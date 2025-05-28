package view.game;

import SaveAndRead.SavaAndRead;
import controller.GameController;
import model.Direction;
import model.MapModel;
import music.Music;
import user.User;
import view.CircularButton;
import view.FrameUtil;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

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
    private JButton aiSolveButton1;
    private JButton MusicBtn;
    private JButton levelBtn;
    public Music backgroundMusic;
    private MouseTrailPanel mouseTrailPanel;
    private Timer timer;
    private JLabel timeLabel;
    public static int timeElapsed = 0;
    private BufferedImage backgroundImage;
    private int currentlevel;

    public int getCurrentlevel() {
        return currentlevel;
    }

    public void setCurrentlevel(int currentlevel) {
        this.currentlevel = currentlevel;
    }

    public JFrame getWelcomeFrame() {
        return welcomeFrame;
    }

    public void setWelcomeFrame(JFrame welcomeFrame) {
        this.welcomeFrame = welcomeFrame;
    }


    public GameFrame(int width, int height, MapModel mapModel, User user) {
        backgroundImage = ImageLoader.loadImage("src/背景.jpg");
        this.user = user;
        this.setTitle("2025 CS109 华容道");
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
                checkMouseOverButtons(e.getPoint());
            }
            @Override
            public void mouseDragged(MouseEvent e) {}
        });

        gamePanel = new GamePanel(mapModel);
        gamePanel.setGameFrame(this);
        gamePanel.setWelcomeFrame(this.getWelcomeFrame());
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2-50);
        this.add(gamePanel);

        this.controller = new GameController(gamePanel, mapModel, user);
        this.controller.setGameframe( this);
        this.backgroundMusic = new Music("背景音乐.wav");
        //设置方向按钮
        BufferedImage imageup0 = ImageLoader.loadImage("src/向上.png");
        Image imageup = ImageLoader.scaleImage(imageup0,50,50);
        BufferedImage imagedown0 =ImageLoader.loadImage("src/向下.png");
        Image imagedown = ImageLoader.scaleImage(imagedown0,50,50);
        BufferedImage imageleft0 = ImageLoader.loadImage("src/向左.png");
        Image imageleft = ImageLoader.scaleImage(imageleft0,50,50);
        BufferedImage imageright0 =ImageLoader.loadImage("src/向右.png");
        Image imageright = ImageLoader.scaleImage(imageright0,50,50);

        int buttonDiameter = 50;
        int StartX = gamePanel.getWidth()+80;


        this.upButton = FrameUtil.createButton(this,"向上",new Point(StartX+160,100),buttonDiameter,imageup,Color.black);
        this.downButton = FrameUtil.createButton(this,"向下",new Point(StartX+160,200),buttonDiameter,imagedown,Color.black);
        this.leftButton = FrameUtil.createButton(this,"向左",new Point(StartX+100,150),buttonDiameter,imageleft,Color.black);
        this.rightButton = FrameUtil.createButton(this,"向右",new Point(StartX+220,150),buttonDiameter,imageright,Color.black);
        this.upButton.addActionListener(e -> {gamePanel.doMoveUp();});
        this.downButton.addActionListener(e -> {gamePanel.doMoveDown();});
        this.leftButton.addActionListener(e -> {gamePanel.doMoveLeft();});
        this.rightButton.addActionListener(e -> {gamePanel.doMoveRight();});

        this.restartBtn = FrameUtil.createButton(this, "src/restart0.png", "src/restar1.png",new Point(gamePanel.getWidth() + 80, 100), 80, 30);
        this.loadBtn = FrameUtil.createButton(this, "src/loadButh0.png","src/load1.png", new Point(gamePanel.getWidth() + 80, 180), 80, 30);
        this.saveBtn = FrameUtil.createButton(this, "src/saveButh0.png","src/save1.png", new Point(gamePanel.getWidth() + 80, 260), 80, 30);
        this.BackButn = FrameUtil.createButton(this, "src/withdraw0.png","src/withdraw1.png", new Point(gamePanel.getWidth() + 80, 340), 80, 30);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 30), new Point(gamePanel.getWidth() + 80, 40), 180, 50);
        this.levelBtn = FrameUtil.createButton(this, "src/level0.png","src/level1.png", new Point(gamePanel.getWidth() + 80, 420), 80, 30);
        this.aiSolveButton1 = FrameUtil.createButton(this,"启发式",new Point(40,550),100,40);
        this.aiSolveButton = FrameUtil.createButton(this, "广度式", new Point(40, 500), 100, 40);
        this.MusicBtn = FrameUtil.createButton(this, "src/MusicBth0.png","src/Music.png", new Point(160, 500), 100, 40);
        backtowelcomeBtn = FrameUtil.createButton(this, "src/Exist0.png", "src/exisit1.png",new Point(gamePanel.getWidth() + 70, 480), 100, 30);


        this.userLabel = FrameUtil.createJLabel(this, user.getUsername(), new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 15), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        //this.add(createbuttonPanel());
        //计时器
        timeLabel = FrameUtil.createJLabel(this,"Time :0",new Font("serif",Font.ITALIC,22), new Point(gamePanel.getWidth() + 200, 15), 180, 50);
        this.add(timeLabel);

        timer = new Timer(1000,new TimerListener());
        this.getGamePanel().setTimer(timer);




        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            this.user.setSteps(this.gamePanel.getSteps());
            gamePanel.requestFocusInWindow();//enable key listener
            restartTimer();
        });

        this.loadBtn.addActionListener(e -> {

            String mappath = String.format("save/%s/%d/mapdata.txt", user.getUsername(), user.getLevel());
            //String searchpath = String.format("save/%s/%d", user.getUsername(), user.getLevel());
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

        aiSolveButton1.addActionListener(e -> {
            this.aiSolveButton.setEnabled(false);


            int[][] currentState = copyMatrix(mapModel.getMatrix());
            List<int[][]> solution = KlotskiSolver.solve2(currentState);

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
            timer.stop();
            timeElapsed = 0;
            timeLabel.setText("Time:0");


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

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);

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
            user.setUsedtime(timeElapsed);
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

    //自定义BackgroundPanel类来绘制背景图像
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

    private void checkMouseOverButtons(Point mousePoint) {
        if (upButton.getBounds().contains(mousePoint)) {
            highlightButton(upButton);
            mouseTrailPanel.clearTrail();
        } else {
            resetButton(upButton);
        }

        if (downButton.getBounds().contains(mousePoint)) {
            highlightButton(downButton);
            mouseTrailPanel.clearTrail();
        } else {
            resetButton(downButton);
        }
        if (leftButton.getBounds().contains(mousePoint)) {
            highlightButton(leftButton);
            mouseTrailPanel.clearTrail();
        }else {
            resetButton(leftButton);
        }

        if (rightButton.getBounds().contains(mousePoint)) {
            highlightButton(rightButton);
            mouseTrailPanel.clearTrail();
        }else {
            resetButton(rightButton);
        }
        if (restartBtn.getBounds().contains(mousePoint)) {
            highlightButton(restartBtn);
            mouseTrailPanel.clearTrail();
        } else {
            resetButton(restartBtn);
        }

        if (saveBtn.getBounds().contains(mousePoint)) {
            highlightButton(saveBtn);
            mouseTrailPanel.clearTrail();
        } else {
            resetButton(saveBtn);
        }
        if (levelBtn.getBounds().contains(mousePoint)) {
            highlightButton(levelBtn);
            mouseTrailPanel.clearTrail();
        } else {
            resetButton(levelBtn);
        }

        if(loadBtn.getBounds().contains(mousePoint)){
            highlightButton(loadBtn);
            mouseTrailPanel.clearTrail();
        }else {
            resetButton(loadBtn);
        }

        if(BackButn.getBounds().contains(mousePoint)){
            highlightButton(BackButn);
            mouseTrailPanel.clearTrail();
        }else {
            resetButton(BackButn);
        }

        if(aiSolveButton.getBounds().contains(mousePoint)){
            highlightButton(aiSolveButton);
            mouseTrailPanel.clearTrail();
        }else {
            resetButton(aiSolveButton);
        }

        if(MusicBtn.getBounds().contains(mousePoint)){
            highlightButton(MusicBtn);
            mouseTrailPanel.clearTrail();
        }else{
            resetButton(MusicBtn);
        }

        if(backtowelcomeBtn.getBounds().contains(mousePoint)){
            highlightButton(backtowelcomeBtn);
            mouseTrailPanel.clearTrail();
        }else{
            resetButton(backtowelcomeBtn);
        }

        if (gamePanel.getBounds().contains(mousePoint)) {
            mouseTrailPanel.clearTrail();
        }







    }


    private void highlightButton(JButton button) {
        button.setBackground(Color.CYAN);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    private void resetButton(JButton button) {
        button.setBackground(null);
        button.setCursor(Cursor.getDefaultCursor());
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int gettimeElapse(){
        return timeElapsed;
    }
    public void settimeElapse(int timeElapse){
        this.timeElapsed = timeElapse;
    }

}


