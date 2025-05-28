package view.register;

import view.FrameUtil;
import view.game.GameFrame;
import view.game.ImageLoader;
import view.login.LoginFrame;
import view.welcome.WelcomeFrame;

import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;

import static SaveAndRead.SavaAndRead.*;

//未完成 未在主程序引入

public class RegisterFrame extends JFrame {

    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton gotoLoginBtn;
    private GameFrame gameFrame;
    private String username1;
    private String password1;
    private BufferedImage backgroundImage;

    private JFrame welcomeFrame;
    private JFrame loginFrame;



    public RegisterFrame(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setLocation(width / 2 - width / 4, height / 2 - height / 4);
        this.setSize(width, height);
        backgroundImage = ImageLoader.loadImage("src/登录界面.jpg");
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);
        gotoLoginBtn = FrameUtil.createButton(this, "gotoLogin", new Point(280, 140), 100, 40);

        RegisterFrame.BackgroundPanel backgroundPanel = new RegisterFrame.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);


        submitBtn.addActionListener(e -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            username1 = username.getText();
            password1 = password.getText();
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            String path = String.format("save/%s", username1);
            if (isDirectoryExistsUsingFile(path)) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                Save(password1, path, "passworddata");
                if (true) {
                    JOptionPane.showMessageDialog(this, "Registered successfully ", "congratulation", JOptionPane.INFORMATION_MESSAGE);
                }

                LoginFrame loginFrame = new LoginFrame(400, 280);
                loginFrame.setVisible(true);
                this.setVisible(false);
            }



        });

        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        gotoLoginBtn.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame(400, 280);
            this.setLoginFrame(loginFrame);
            loginFrame.setWelcomeFrame(this.getWelcomeFrame());
            loginFrame.setRegisterFrame(this);
            loginFrame.setVisible(true);
            this.setVisible(false);
        });



        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public JFrame getWelcomeFrame() {
        return welcomeFrame;
    }

    public void setWelcomeFrame(JFrame welcomeFrame) {
        this.welcomeFrame = welcomeFrame;
    }

    public JFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;
    }


    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
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
