package view.ranklist;

import view.game.ImageLoader;
import view.gamelevel.GameLevel;
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
    private JScrollPane scrollPane; // 新增滚动面板成员变量
    private BufferedImage backgroundImage;
    private JLayeredPane layeredPane;

    public RankListFrame(int width, int height, int level) {
        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
        this.setTitle("RankList");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.level = level;

        timeOrderBtn = FrameUtil.createButton(this, "timeOrder", new Point(width*1/4, 10), 100, 40);
        stepOrderBtn = FrameUtil.createButton(this, "stepOrder", new Point(width*2/4, 10), 100, 40);
        backgroundImage = ImageLoader.loadImage("src/排行.jpg");
        RankListFrame.BackgroundPanel backgroundPanel = new RankListFrame.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        layeredPane.add(backgroundPanel, BorderLayout.CENTER,JLayeredPane.DEFAULT_LAYER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(timeOrderBtn);
        buttonPanel.add(stepOrderBtn);
        this.add(buttonPanel, BorderLayout.NORTH);


        ArrayList<Object> rankObjects = GetWinObject(level);
        ArrayList<Object> stepOrderObjects = sortByStep(rankObjects);
        ArrayList<Object> timeOrderObjects = sortByTime(rankObjects);
        ArrayList<String> stepOrderStrings = convertToStrings(stepOrderObjects);
        ArrayList<String> timeOrderStrings = convertToStrings(timeOrderObjects);

        rankJList = new JList<>(timeOrderStrings.toArray(new String[0]));
        // 设置字体为楷体，样式为普通，大小为 18，可按需调整
        rankJList.setFont(new Font("楷体", Font.PLAIN, 18));
        scrollPane = new JScrollPane(rankJList);
        scrollPane.setPreferredSize(new Dimension(width, height));
        layeredPane.add(scrollPane,JLayeredPane.POPUP_LAYER);
        this.add(layeredPane, BorderLayout.CENTER);

        timeOrderBtn.addActionListener(e -> {
            rankJList.setListData(timeOrderStrings.toArray(new String[0])); // 更新列表数据
        });

        stepOrderBtn.addActionListener(e -> {
            rankJList.setListData(stepOrderStrings.toArray(new String[0])); // 更新列表数据
        });

        this.setVisible(true);
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
            return new ArrayList<>(); // 返回空列表
        }
        // 存储用户名和对应的 win 文件夹最后修改时间
        ArrayList<Object> rankObjects = new ArrayList<>();
        File[] userDirs = baseDir.listFiles(File::isDirectory);
        if (userDirs != null) {
            for (File userDir : userDirs) {
                String username = userDir.getName();
                File levelDir = new File(userDir, String.format("%d", level));
                // 检查关卡目录是否存在
                if (!levelDir.exists() || !levelDir.isDirectory()) {
                    continue;
                }
                File winDir = new File(levelDir, "win");
                // 检查 win 目录是否存在
                if (!winDir.exists() || !winDir.isDirectory()) {
                    continue;
                }
                File timeFile = new File(winDir, "time.txt");
                File stepFile = new File(winDir, "step.txt");
                // 检查时间文件和步数文件是否存在
                if (timeFile.exists() && stepFile.exists()) {
                    String timeContent = readFileContent(timeFile);
                    String stepContent = readFileContent(stepFile);
                    if (timeContent != null && stepContent != null) {
                        RankObject rankObject = new RankObject(username, timeContent, stepContent);
                        rankObjects.add(rankObject);
                    }
                }
            }
        }
        return rankObjects;
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

    private class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;
        public BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0,this.getWidth(),this.getHeight(),this);
            }
        }
    }







}
