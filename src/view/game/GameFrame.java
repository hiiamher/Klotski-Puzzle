package view.game;

import SaveAndRead.SavaAndRead;
import controller.GameController;
import model.MapModel;
import user.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private User user;
    private JLabel userLabel;

    public GameFrame(int width, int height, MapModel mapModel,User user) {
        this.user = user;
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.userLabel = FrameUtil.createJLabel(this, user.getUsername(), new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 15), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.loadBtn.addActionListener(e -> {

            String mappath = String.format("save/%s/mapdata.txt", user.getUsername());
            String searchpath = String.format("save/%s", user.getUsername());

            //待改进
            //检验是否有存档
            if (true) {
                controller.loadGame(mappath);
                gamePanel.requestFocusInWindow();
            }else{
                JOptionPane.showMessageDialog(this, "No save file!");
            }
            gamePanel.requestFocusInWindow();
            //enable key listener
        });

        this.saveBtn.addActionListener(e -> {

            controller.saveGame(user);
            gamePanel.requestFocusInWindow();
        });

        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
