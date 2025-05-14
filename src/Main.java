import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;
import view.register.RegisterFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {


            RegisterFrame RegisterFrame = new RegisterFrame(450, 280);
            RegisterFrame.setVisible(true);

            /*
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            */

           // loginFrame.setGameFrame(gameFrame);
        });
    }
}
