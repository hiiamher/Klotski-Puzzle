package view.ranklist;

import view.FrameUtil;
import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RankListFrame extends JFrame {

    private ArrayList<String> rankList;
    private JList<String> rankJList;
    private int level;
    private JButton timeOrderBtn;
    private JButton stepOrderBtn;

    public RankListFrame(int width, int height, int level) {
        this.setLayout(null);
        this.setTitle("RankList");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.level = level;

        timeOrderBtn = FrameUtil.createButton(this, "timeOrder", new Point(width*1/4, 10), 100, 40);
        stepOrderBtn = FrameUtil.createButton(this, "stepOrder", new Point(width*2/4, 10), 100, 40);


        rankList = new ArrayList<>(); // 初始化 rankList
        rankJList = new JList<>(); // 初始化 rankJList
        // 创建 JScrollPane 并将 rankJList 放入其中
        JScrollPane scrollPane = new JScrollPane(rankJList);
        scrollPane.setBounds(10, 60, width - 20, height - 20);
        this.add(scrollPane);

        ArrayList<Object> rankObjects = GetWinObject(level);
        ArrayList<Object> stepOrderObjects = sortByStep(rankObjects);
        ArrayList<Object> timeOrderObjects = sortByTime(rankObjects);
        ArrayList<String> stepOrderStrings = convertToStrings(stepOrderObjects);
        ArrayList<String> timeOrderStrings = convertToStrings(timeOrderObjects);






        timeOrderBtn.addActionListener(e -> {

        });



        stepOrderBtn.addActionListener(e -> {

        });















    }


    public ArrayList<String> getRankList() {
        return rankList;
    }

    public void setRankList(ArrayList<String> rankList) {
        this.rankList = rankList;
    }



    public ArrayList<Object> GetWinObject(int level) {

        // 定义基础目录
        File baseDir = new File("save");
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return null;
        }
        // 存储用户名和对应的 win 文件夹最后修改时间
        ArrayList<Object> rankObjects = new ArrayList<>();
        File[] userDirs = baseDir.listFiles(File::isDirectory);
        if (userDirs != null) {
            for (File userDir : userDirs) {
                String username = userDir.getName();
                File levelDir = new File(userDir, String.format("%d", level));
                File winDir = new File(levelDir, String.format("win"));
                File timeFile = new File(winDir, "time.txt");
                File stepFile = new File(winDir, "step.txt");
                String timeContent = readFileContent(timeFile);
                String stepContent = readFileContent(stepFile);
                if (timeContent != null&& stepContent != null) {
                    RankObject rankObject = new RankObject(username, timeContent,stepContent);
                    rankObjects.add(rankObject);
                }

            }
        }

        return  rankObjects;
    }


    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            // 去除最后一个换行符
            if (content.length() > 0) {
                content.setLength(content.length() - 1);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    //按步数对 RankObject 对象列表进行排序
    public ArrayList<Object> sortByStep(ArrayList<Object> list) {
        ArrayList<RankObject> rankObjectList = new ArrayList<>();
        // 筛选出 RankObject 对象
        for (Object obj : list) {
            if (obj instanceof RankObject) {
                rankObjectList.add((RankObject) obj);
            }
        }
        // 按步数排序
        rankObjectList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getStep())));
        return new ArrayList<>(rankObjectList);
    }

    //按时间对 RankObject 对象列表进行排序
    public ArrayList<Object> sortByTime(ArrayList<Object> list) {
        ArrayList<RankObject> rankObjectList = new ArrayList<>();
        // 筛选出 RankObject 对象
        for (Object obj : list) {
            if (obj instanceof RankObject) {
                rankObjectList.add((RankObject) obj);
            }
        }
        // 按时间排序
        rankObjectList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getTime())));
        return new ArrayList<>(rankObjectList);
    }

    private ArrayList<String> convertToStrings(ArrayList<Object> objects) {
        ArrayList<String> strings = new ArrayList<>();
        for (Object obj : objects) {
            strings.add(obj.toString());
        }
        return strings;
    }





}
