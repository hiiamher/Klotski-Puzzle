package view.welcome;

import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.ImageLoader;
import view.login.LoginFrame;
import view.register.RegisterFrame;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static SaveAndRead.SavaAndRead.Save;

public class WelcomeFrame extends JFrame{

    private JButton visitor;
    private JButton rigister;
    private JButton login;
    private GameFrame gameFrame;
    private JFrame registerFrame;
    private JFrame loginFrame;
    private BufferedImage backgroundImage;

    public WelcomeFrame(int width, int height) {
        this.setTitle("Welcome");
        this.setLayout(null);
        this.setLocation(width / 2 - width / 4, height / 2 - height / 4);
        this.setSize(width, height);

        backgroundImage = ImageLoader.loadImage("src/欢迎界面.jpg");
        visitor = FrameUtil.createButton(this, "visitor", new Point(width*3/4, 80), 100, 40);
        rigister = FrameUtil.createButton(this, "rigister", new Point(width*3/4, 160), 100, 40);
        login = FrameUtil.createButton(this, "login", new Point(width*3/4, 240), 100, 40);

        visitor.addActionListener(e -> {

            User user = new User("visitor","666");
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 2, 2, 3},
                    {3, 1, 1, 3},
                    {1, 0, 0, 1}

            });
            GameFrame gameFrame = new GameFrame(600, 660, mapModel, user);
            gameFrame.setWelcomeFrame(this);
            this.gameFrame = gameFrame;

            gameFrame.getloadBtn().setVisible(false);
            gameFrame.getsaveBtn().setVisible(false);

            gameFrame.setVisible(true);
            this.setVisible(false);

            //保存初始地图
            String path0 = String.format("save/%s/path/%d", user.getUsername(),0);
            File dir0 = new File(path0);
            dir0.mkdirs();
            int[][] map0 = mapModel.getMatrix();
            List<String> gameData0 = new ArrayList<>();
            StringBuilder sb0 = new StringBuilder();
            for(int[] line : map0) {
                for(int value:line){
                    sb0.append(value).append(" ");
                }
                gameData0.add(sb0.toString());
                sb0.setLength(0);
            }
            Save (gameData0,path0,"Path_Map");



        });


        rigister.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame(450, 280);
            registerFrame.setWelcomeFrame(this);
            registerFrame.setVisible(true);
            this.setVisible(false);

        });

        login.addActionListener(e -> {
            System.out.println("Login Button clicked");

            LoginFrame loginFrame = new LoginFrame(400, 280);
            loginFrame.setWelcomeFrame(this);
            loginFrame.setVisible(true);
            this.setVisible(false);

        });

        WelcomeFrame.BackgroundPanel backgroundPanel = new WelcomeFrame.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);






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
