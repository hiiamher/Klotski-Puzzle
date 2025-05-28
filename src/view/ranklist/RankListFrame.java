package view.ranklist;

import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RankListFrame extends JFrame {

    private ArrayList<String> rankList;
    private JList<String> rankJList;

    public RankListFrame(int width, int height) {
        this.setLayout(null);
        this.setTitle("RankList");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(rankJList);
        scrollPane.setBounds(10, 10, width - 20, height - 20);
        this.add(scrollPane);









    }


    public ArrayList<String> getRankList() {
        return rankList;
    }

    public void setRankList(ArrayList<String> rankList) {
        this.rankList = rankList;
    }

    public ArrayList<String> getTimeList(int level) {
        // 定义存储用户时间信息的列表
        ArrayList<String> timeList = new ArrayList<>();
        // 定义基础目录
        File baseDir = new File("save");
        // 检查基础目录是否存在且为目录
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return timeList;
        }

        // 存储用户名和对应的 win 文件夹最后修改时间
       /* Map<String, Long> userLevelWinTimeMap = new HashMap<>();
        File[] userDirs = baseDir.listFiles(File::isDirectory);
        if (userDirs != null) {
            for (File userDir : userDirs) {
                String username = userDir.getName();
                File levelDirs = new File(userDir, "level" + level);
                if (levelDirs != null) {
                    for (File levelDir : levelDirs) {
                        String levelName = levelDir.getName();
                        File winDir = new File(levelDir, "win");
                        if (winDir.exists() && winDir.isDirectory()) {
                            long lastModified = winDir.lastModified();
                            String key = username + " - " + levelName;
                            userLevelWinTimeMap.put(key, lastModified);
                        }
                    }
                }
            }
        }

        // 将用户 - 关卡信息按 win 文件夹最后修改时间排序
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(userLevelWinTimeMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : sortedEntries) {
            result.add(entry.getKey() + " - " + entry.getValue());
        }
        return result;*/
        return timeList;
    }







}
