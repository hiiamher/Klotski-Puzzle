import view.welcome.WelcomeFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {


            WelcomeFrame welcomeFrame = new WelcomeFrame(900,600);
            welcomeFrame.setVisible(true);

//           RegisterFrame RegisterFrame = new RegisterFrame(450, 280);
//           RegisterFrame.setVisible(true);

            /*
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            */

           // loginFrame.setGameFrame(gameFrame);
        });
    }
}
