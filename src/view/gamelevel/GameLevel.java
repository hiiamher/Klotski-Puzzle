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
    private JFrame gameFrame;
    private GamePanel gamePanel;
    private MapModel model;
    private User user;
    private GameController controller;



    public GameLevel(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);

        level1 = FrameUtil.createButton(this, "1", new Point(width*3/4, 80), 100, 40);
        level2 = FrameUtil.createButton(this, "2", new Point(width*3/4, 160), 100, 40);
        level3 = FrameUtil.createButton(this, "3", new Point(width*3/4, 240), 100, 40);

        level1.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 2, 2, 3},
                    {3, 1, 1, 3},
                    {1, 0, 0, 1}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map1);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map1);





        });

        level2.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {0, 1, 1, 3},
                    {0, 4, 4, 3},
                    {0, 4, 4, 1},
                    {0, 1, 1, 1},
                    {1, 0, 0, 1}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map1);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map1);


        });

        level3.addActionListener(e -> {

            int[][] map1 = new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 1, 1, 1},
                    {3, 1, 1, 1},
                    {1, 0, 0, 1}
            };

            this.model.setMatrix(map1);
            this.model.setOriginalMatrix(map1);
            this.gamePanel.clearAllBoxFromPanel();
            this.gamePanel.initialGame(map1);


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
