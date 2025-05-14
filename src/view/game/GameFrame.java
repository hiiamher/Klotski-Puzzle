package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton leftButton,rightButton,upButton,downButton;
    private JPanel buttonPanel;

    //创建JPanel对象，用于放置方向按钮
    private JPanel createbuttonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.setBounds(280, 300, 150, 100);
        upButton = new JButton("↑");
        upButton.addActionListener(e -> {
            gamePanel.doMoveUp();
        });
        buttonPanel.add(new JButton());
        buttonPanel.add(upButton);
        buttonPanel.add(new JButton());
        leftButton = new JButton("←");
        leftButton.addActionListener(e -> {
            gamePanel.doMoveLeft();
        });
        buttonPanel.add(leftButton);
        downButton = new JButton("↓");
        downButton.addActionListener(e -> {
            gamePanel.doMoveDown();
        });
        buttonPanel.add(downButton);
        rightButton = new JButton("→");
        rightButton.addActionListener(e -> {
            gamePanel.doMoveRight();
        });
        buttonPanel.add(rightButton);
        return buttonPanel;
    }

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        this.add(createbuttonPanel());
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);}



    }


