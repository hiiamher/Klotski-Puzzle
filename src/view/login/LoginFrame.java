package view.login;

import controller.UserController;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;

import static SaveAndRead.SavaAndRead.Read;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    //private GameFrame gameFrame;


    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);

        submitBtn.addActionListener(e -> {

            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            //todo: check login info
            if (UserController.validateUser(username.getText(), password.getText())) {
                User user = new User(username.getText(), password.getText());
                MapModel mapModel = new MapModel(new int[][]{
                        {1, 2, 2, 1},
                        {1, 3, 2, 2},
                        {1, 3, 4, 4},
                        {0, 0, 4, 4}
                });
                GameFrame gameFrame = new GameFrame(600, 450, mapModel, user);
                gameFrame.setVisible(true);
                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(this, "Username or Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
