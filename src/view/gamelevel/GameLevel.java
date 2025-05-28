package view.gamelevel;

import controller.GameController;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.GamePanel;
import view.game.ImageLoader;
import view.login.LoginFrame;
import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

import static SaveAndRead.SavaAndRead.Save;

public class GameLevel extends JFrame{

    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton level4;
    private JButton level5;
    private JButton level6;
    private JFrame gameFrame;
    private GamePanel gamePanel;
    private MapModel model;
    private User user;
    private GameController controller;
    private BufferedImage backgroundImage;




    public GameLevel(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);



        level1 = FrameUtil.createButton(this, "src/立.png","src/开始.png", new Point(width/5, 40), 130, 70);
        level2 = FrameUtil.createButton(this, "src/指.png", "src/开始.png",new Point(width/5, 140), 130, 70);
        level3 = FrameUtil.createButton(this, "src/将.png", "src/开始.png",new Point(width/5, 240), 130, 70);
        level4 = FrameUtil.createButton(this, "src/左右.png", "src/开始.png", new Point(width*2/5+100, 40), 130, 70);
        level5 = FrameUtil.createButton(this, "src/雨.png", "src/开始.png",new Point(width*2/5+100, 140), 130, 70);
        level6 = FrameUtil.createButton(this, "src/扬.png", "src/开始.png",new Point(width*2/5+100, 240), 130, 70);

        backgroundImage = ImageLoader.loadImage("src/欢迎界面.jpg");
        GameLevel.BackgroundPanel backgroundPanel = new GameLevel.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);

        level1.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 2, 2, 3},
                    {3, 1, 1, 3},
                    {1, 0, 0, 1}
            };
            int[][] map2 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 2, 2, 3},
                    {3, 1, 1, 3},
                    {1, 0, 0, 1}
            };


            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);
            this.dispose();





        });

        level2.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {1, 2, 2, 1},
                    {3, 1, 1, 3},
                    {3, 0, 0, 3}
            };
            int[][] map2 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {1, 2, 2, 1},
                    {3, 1, 1, 3},
                    {3, 0, 0, 3}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);

            this.dispose();
        });

        level3.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {0, 4, 4, 0},
                    {3, 4, 4, 3},
                    {3, 3, 3, 3},
                    {1, 3, 3, 1},
                    {2, 2, 1, 1}
            };
            int[][] map2 = new int[][]{
                    {0, 4, 4, 0},
                    {3, 4, 4, 3},
                    {3, 3, 3, 3},
                    {1, 3, 3, 1},
                    {2, 2, 1, 1}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);

            this.dispose();
        });

        level4.addActionListener(e -> {
            int[][] map1 = new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {3, 3, 3, 3},
                    {3, 3, 3, 3},
                    {0, 2, 2, 0}
            };
            int[][] map2 = new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {3, 3, 3, 3},
                    {3, 3, 3, 3},
                    {0, 2, 2, 0}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);

            this.dispose();
        });

        level5.addActionListener(e -> {
            int[][] map1 = new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {3, 2, 2, 3},
                    {3, 3, 0, 3},
                    {1, 3, 0, 1}
            };
            int[][] map2 = new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {3, 2, 2, 3},
                    {3, 3, 0, 3},
                    {1, 3, 0, 1}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);

            this.dispose();
        });

        level6.addActionListener(e -> {
            int[][] map1 = new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {1, 0, 0, 1},
                    {3, 0, 0, 3},
                    {3, 0, 0, 3}
            };
            int[][] map2 = new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {1, 0, 0, 1},
                    {3, 0, 0, 3},
                    {3, 0, 0, 3}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map2);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map2);


            this.dispose();
        });







    }

    public JFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(JFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
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
