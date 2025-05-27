package view.gamelevel;

import controller.GameController;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.GamePanel;
import view.login.LoginFrame;
import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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



    public GameLevel(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);

        level1 = FrameUtil.createButton(this, "立马横刀", new Point(width/4, 80), 100, 40);
        level2 = FrameUtil.createButton(this, "指挥若定", new Point(width/4, 160), 100, 40);
        level3 = FrameUtil.createButton(this, "将拥曹营", new Point(width/4, 240), 100, 40);
        level4 = FrameUtil.createButton(this, "左右步兵", new Point(width*2/4, 80), 100, 40);
        level5 = FrameUtil.createButton(this, "雨声淅沥", new Point(width*2/4, 160), 100, 40);
        level6 = FrameUtil.createButton(this, "扬帆起航", new Point(width*2/4, 240), 100, 40);


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

}
