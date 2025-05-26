package view.game;

import SaveAndRead.SavaAndRead;
import controller.GameController;
import model.Direction;
import model.MapModel;
import music.Music;
import user.User;
import view.FrameUtil;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static SaveAndRead.SavaAndRead.isExist;

import ai.KlotskiSolver;
import view.gamelevel.GameLevel;
import view.showsolution.ShowSolution;


import java.util.List;


public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private User user;
    private JLabel userLabel;
    private JButton leftButton, rightButton, upButton, downButton;
    private JPanel buttonPanel;
    private JButton BackButn;
    private JFrame welcomeFrame;
    private JButton backtowelcomeBtn;
    private JButton aiSolveButton;
    private JButton MusicBtn;
    private JButton levelBtn;
    public Music backgroundMusic;
    private MouseTrailPanel mouseTrailPanel;
    private Timer timer;
    private JLabel timeLabel;
    protected int timeElapsed = 0;

    public JFrame getWelcomeFrame() {
        return welcomeFrame;
    }

    public void setWelcomeFrame(JFrame welcomeFrame) {
        this.welcomeFrame = welcomeFrame;
    }

    //创建JPanel对象，用于放置方向按钮
    private JPanel createbuttonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.setBounds(400, 200, 150, 100);
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



    public GameFrame(int width, int height, MapModel mapModel, User user) {
        this.user = user;
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        mouseTrailPanel = new MouseTrailPanel();
        mouseTrailPanel.setTrailColor(Color.blue);
        mouseTrailPanel.setPointSize(5);
        mouseTrailPanel.setLocation(0,0);
        mouseTrailPanel.setSize(width,height);
        this.add(mouseTrailPanel,JLayeredPane.PALETTE_LAYER);
        //重写鼠标移动
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseTrailPanel.Mouseupdtae(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {}
        });

        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);

        this.controller = new GameController(gamePanel, mapModel, user);
        this.controller.setGameframe( this);
        this.backgroundMusic = new Music("背景音乐.wav");

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 100), 80, 30);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 180), 80, 30);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 260), 80, 30);
        this.BackButn = FrameUtil.createButton(this, "Back", new Point(gamePanel.getWidth() + 80, 340), 80, 30);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 40), 180, 50);
        this.levelBtn = FrameUtil.createButton(this, "Level", new Point(gamePanel.getWidth() + 80, 420), 80, 30);
        this.aiSolveButton = FrameUtil.createButton(this, "AI Solve", new Point(40, 500), 100, 40);
        this.MusicBtn = FrameUtil.createButton(this, "Music", new Point(160, 500), 100, 40);
        backtowelcomeBtn = FrameUtil.createButton(this, "Exist", new Point(gamePanel.getWidth() + 70, 480), 100, 30);


        this.userLabel = FrameUtil.createJLabel(this, user.getUsername(), new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 15), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        this.add(createbuttonPanel());
        //计时器
        timeLabel = FrameUtil.createJLabel(this,"Time :0",new Font("seilf",Font.ITALIC,22), new Point(gamePanel.getWidth() + 200, 15), 180, 50);
        this.add(timeLabel);

        timer = new Timer(1000,new TimerListener());
        timer.start();

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            this.user.setSteps(this.gamePanel.getSteps());
            gamePanel.requestFocusInWindow();//enable key listener
            timeElapsed = 0;
            timer.stop();
        });

        this.loadBtn.addActionListener(e -> {

            String mappath = String.format("save/%s/mapdata.txt", user.getUsername());
            String searchpath = String.format("save/%s", user.getUsername());
            //检验是否有存档
            if (isExist(mappath)) {
                controller.loadGame(mappath);
                gamePanel.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(this, "No save file!");
            }
            gamePanel.requestFocusInWindow();

            //enable key listener
        });

        backtowelcomeBtn.addActionListener(e -> {
            welcomeFrame.setVisible(true);
            this.dispose();
            timer.stop();
            backgroundMusic.stop();
        });

        this.saveBtn.addActionListener(e -> {

            controller.saveGame(user);
            gamePanel.requestFocusInWindow();
        });

        //添加鼠标版面


        //ai 算法
        //给aiSolveButton添加事件监听器，当点击按钮时，调用KlotskiSolver.solve()方法求解游戏 并显示解决方案步骤
        aiSolveButton.addActionListener(e -> {

            this.aiSolveButton.setEnabled(false);


            int[][] currentState = copyMatrix(mapModel.getMatrix());
            List<int[][]> solution = KlotskiSolver.solve(currentState);

            if (solution != null) {

                ShowSolution showSolution = new ShowSolution(this, solution);
                showSolution.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "No solution found", "Error", JOptionPane.ERROR_MESSAGE);
            }

            this.aiSolveButton.setEnabled(true);
        });

        //音乐按钮
        this.MusicBtn.addActionListener(e -> {
                if (backgroundMusic.isPlaying()) {
                    backgroundMusic.stop();
                    MusicBtn.setText("Music");
                } else {
                    backgroundMusic.loop();
                    MusicBtn.setText("Stop Music");
                }
        });

//退出时保存游戏
        this.setLocationRelativeTo(null);
        // 设置默认关闭操作，点击关闭按钮时不直接退出
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // 添加窗口关闭事件监听
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // 调用保存方法
                controller.saveGame(user);
                // 关闭窗口
                GameFrame.this.dispose();
                backgroundMusic.stop();
                timer.stop();
            }
        });

        this.levelBtn.addActionListener(e -> {
            GameLevel gameLevel = new GameLevel(600, 400);
            gameLevel.setVisible(true);
            gameLevel.setGamePanel(gamePanel);
            gameLevel.setController(controller);
            gameLevel.setGameFrame(this);
            gameLevel.setModel( mapModel);
            gameLevel.setUser(user);



        });


        this.BackButn.addActionListener(e -> {
            gamePanel.withDraw();
        });

        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);




    }


    public JButton getsaveBtn() {
        return saveBtn;
    }

    public JButton getloadBtn() {
        return loadBtn;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }
    //计时器事件监听类
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeElapsed++;
            timeLabel.setText(String.format("Time:%d",timeElapsed));
        }
    }

    public void restartTimer(){
        timeElapsed = 0;
        timeLabel.setText("Time:0");
        timer.restart();
    }

    public void stopTimer(){
        timer.stop();
    }


}


