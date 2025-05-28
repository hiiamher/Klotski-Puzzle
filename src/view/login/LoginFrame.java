package view.login;

import controller.UserController;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.ImageLoader;
import view.register.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

import static SaveAndRead.SavaAndRead.Save;
import static SaveAndRead.SavaAndRead.isDirectoryExistsUsingFile;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton backtowelcomeBtn;

    private JFrame welcomeFrame;


    private JFrame registerFrame;
    private BufferedImage backgroundImage;


//private GameFrame gameFrame;


    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);

        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this,"src/confirm0.png", "src/confirm1.png", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "src/reset0.png", "src/reset1.png",new Point(160, 140), 100, 40);
        backtowelcomeBtn = FrameUtil.createButton(this, "src/back0.png","src/back1.png", new Point(280, 140), 100, 40);

        backgroundImage = ImageLoader.loadImage("src/登录界面.jpg");
        LoginFrame.BackgroundPanel backgroundPanel = new LoginFrame.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);

        submitBtn.addActionListener(e -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            if (isDirectoryExistsUsingFile(String.format("save/%s", username.getText()))) {
                //todo: check login info
                if (UserController.validateUser(username.getText(), password.getText())) {
                    User user = new User(username.getText(), password.getText());
                    MapModel mapModel = new MapModel(new int[][]{
                                    {3, 4, 4, 3},
                                    {3, 4, 4, 3},
                                    {3, 2, 2, 3},
                                    {3, 1, 1, 3},
                                    {1, 0, 0, 1}
                                    /*    {0, 1, 1, 1},
                                        {0, 1, 1, 1},
                                        {0, 4, 4, 1},
                                        {0, 4, 4, 1},
                                        {1, 0, 0, 1}*/
                            });
                    //保存初始地图
                    String path0 = String.format("save/%s/path/%d", user.getUsername(), 0);
                    File dir0 = new File(path0);
                    dir0.mkdirs();
                    int[][] map0 = mapModel.getMatrix();
                    List<String> gameData0 = new ArrayList<>();
                    StringBuilder sb0 = new StringBuilder();
                    for (int[] line : map0) {
                        for (int value : line) {
                            sb0.append(value).append(" ");
                        }
                        gameData0.add(sb0.toString());
                        sb0.setLength(0);
                    }
                    Save(gameData0, path0, "Path_Map");
                    //
                    GameFrame gameFrame = new GameFrame(600, 660, mapModel, user);
                    gameFrame.setWelcomeFrame(this.getWelcomeFrame());
                    gameFrame.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Username or Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Username does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        this.backtowelcomeBtn.addActionListener(e -> {
            welcomeFrame.setVisible(true);
            this.dispose();
            if (this.registerFrame != null) {
                this.registerFrame.dispose();
            }

        });


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public JFrame getWelcomeFrame() {
        return this.welcomeFrame;
    }

    public void setWelcomeFrame(JFrame welcomeFrame) {
        this.welcomeFrame = welcomeFrame;
    }

    public JFrame getRegisterFrame(RegisterFrame registerFrame) {
        return this.registerFrame;
    }

    public void setRegisterFrame(JFrame registerFrame) {
        this.registerFrame = registerFrame;
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
