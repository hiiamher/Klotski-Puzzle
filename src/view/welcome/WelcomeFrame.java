package view.welcome;

import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.login.LoginFrame;
import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame{

    private JButton visitor;
    private JButton rigister;
    private JButton login;
    private GameFrame gameFrame;
    private JFrame registerFrame;
    private JFrame loginFrame;

    public WelcomeFrame(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);

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
            GameFrame gameFrame = new GameFrame(600, 600, mapModel, user);
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







    }



}
