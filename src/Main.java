import view.welcome.WelcomeFrame;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



import static ai.KlotskiSolver.solve;

public class Main {
    public static void main(String[] args) {


//测试
        int[][] a = {
                {3, 4, 4, 3},
                {3, 4, 4, 3},
                {3, 2, 2, 3},
                {3, 1, 1, 3},
                {1, 0, 0, 1}

        };

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
