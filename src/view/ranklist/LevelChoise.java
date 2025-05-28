package view.ranklist;

import view.FrameUtil;
import view.game.ImageLoader;
import view.gamelevel.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelChoise extends JFrame {

    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton level4;
    private JButton level5;
    private JButton level6;
    private BufferedImage backgroundImage;

    public LevelChoise(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);

        level1 = FrameUtil.createButton(this, "src/立.png","src/开始.png", new Point(width/5, 40), 130, 70);
        level2 = FrameUtil.createButton(this, "src/指.png", "src/开始.png",new Point(width/5, 140), 130, 70);
        level3 = FrameUtil.createButton(this, "src/将.png", "src/开始.png",new Point(width/5, 240), 130, 70);
        level4 = FrameUtil.createButton(this, "src/左右.png", "src/开始.png", new Point(width*2/5+100, 40), 130, 70);
        level5 = FrameUtil.createButton(this, "src/雨.png", "src/开始.png",new Point(width*2/5+100, 140), 130, 70);
        level6 = FrameUtil.createButton(this, "src/扬.png", "src/开始.png",new Point(width*2/5+100, 240), 130, 70);
        backgroundImage = ImageLoader.loadImage("src/欢迎界面.jpg");
        LevelChoise.BackgroundPanel backgroundPanel = new LevelChoise.BackgroundPanel(backgroundImage);
        backgroundPanel.setSize(width, height);
        backgroundPanel.setLocation(0, 0);
        this.add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);


        level1.addActionListener(e -> {
            RankListFrame rankListFrame = new RankListFrame(400, 800);

            rankListFrame.setVisible(true);


            this.dispose();





        });

        level2.addActionListener(e -> {

            RankListFrame rankListFrame = new RankListFrame(400, 800);
            rankListFrame.setVisible(true);


            this.dispose();
        });

        level3.addActionListener(e -> {
            RankListFrame rankListFrame = new RankListFrame(400, 800);

            rankListFrame.setVisible(true);

            this.dispose();
        });

        level4.addActionListener(e -> {

            RankListFrame rankListFrame = new RankListFrame(400, 800);

            rankListFrame.setVisible(true);


            this.dispose();
        });

        level5.addActionListener(e -> {


            RankListFrame rankListFrame = new RankListFrame(400, 800);
            rankListFrame.setVisible(true);

            this.dispose();
        });

        level6.addActionListener(e -> {


            RankListFrame rankListFrame = new RankListFrame(400, 800);

            rankListFrame.setVisible(true);

            this.dispose();
        });






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
