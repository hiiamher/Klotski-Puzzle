package view.welcome;

import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.ImageLoader;
import view.gamelevel.GameLevel;
import view.login.LoginFrame;
import view.ranklist.LevelChoise;
import view.ranklist.RankListFrame;
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
    private JButton rankList;
    private GameFrame gameFrame;
    private JFrame registerFrame;
    private JFrame loginFrame;
    private BufferedImage backgroundImage;
    private ArrayList<String> rankList1;

    public WelcomeFrame(int width, int height) {
        this.setTitle("Welcome");
        this.setLayout(null);
        this.setLocation(width / 2 - width / 4, height / 2 - height / 4);
        this.setSize(width, height);

        backgroundImage = ImageLoader.loadImage("src/欢迎界面.jpg");
        visitor = FrameUtil.createButton(this, "src/visit0.png","src/visit1.png", new Point(width*2/3, 40), 170, 70);
        rigister = FrameUtil.createButton(this, "src/register0.png","src/rigister1.png" , new Point(width*2/3, 160), 170, 70);
        login = FrameUtil.createButton(this, "src/login0.png", "src/login1.png" ,new Point(width*2/3, 280), 170, 70);
        rankList = FrameUtil.createButton(this, "src/rank0.png","src/rank1.png", new Point(width*2/3, 400), 170, 70);


        visitor.addActionListener(e -> {

            User user = new User("visitor","666");
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 2, 2, 3},
                    {3, 1, 1, 3},
                    {1, 0, 0, 1}

            });
            GameFrame gameFrame = new GameFrame(1000, 618, mapModel, user);
            gameFrame.setWelcomeFrame(this);
            this.gameFrame = gameFrame;

            gameFrame.getloadBtn().setVisible(false);
            gameFrame.getsaveBtn().setVisible(false);

            gameFrame.setVisible(true);
            this.setVisible(false);
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

        rankList.addActionListener(e -> {


            LevelChoise levelChoise = new LevelChoise(600, 400);
            levelChoise.setVisible(true);



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
