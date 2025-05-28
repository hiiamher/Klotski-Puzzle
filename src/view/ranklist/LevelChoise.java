package view.ranklist;

import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class LevelChoise extends JFrame {

    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton level4;
    private JButton level5;
    private JButton level6;

    public LevelChoise(int width, int height) {
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);

        level1 = FrameUtil.createButton(this, "立马横刀", new Point(width/4, 80), 100, 40);
        level2 = FrameUtil.createButton(this, "指挥若定", new Point(width/4, 160), 100, 40);
        level3 = FrameUtil.createButton(this, "将拥曹营", new Point(width/4, 240), 100, 40);
        level4 = FrameUtil.createButton(this, "左右步兵", new Point(width*2/4, 80), 100, 40);
        level5 = FrameUtil.createButton(this, "雨声淅沥", new Point(width*2/4, 160), 100, 40);
        level6 = FrameUtil.createButton(this, "扬帆起航", new Point(width*2/4, 240), 100, 40);


        level1.addActionListener(e -> {


            RankListFrame rankListFrame = new RankListFrame(400, 800,1);
            rankListFrame.setVisible(true);

            this.dispose();





        });

        level2.addActionListener(e -> {

            RankListFrame rankListFrame = new RankListFrame(400, 800,2);
            rankListFrame.setVisible(true);


            this.dispose();
        });

        level3.addActionListener(e -> {
            RankListFrame rankListFrame = new RankListFrame(400, 800,3);

            rankListFrame.setVisible(true);

            this.dispose();
        });

        level4.addActionListener(e -> {

            RankListFrame rankListFrame = new RankListFrame(400, 800,4);

            rankListFrame.setVisible(true);


            this.dispose();
        });

        level5.addActionListener(e -> {


            RankListFrame rankListFrame = new RankListFrame(400, 800,5);
            rankListFrame.setVisible(true);

            this.dispose();
        });

        level6.addActionListener(e -> {


            RankListFrame rankListFrame = new RankListFrame(400, 800,6);

            rankListFrame.setVisible(true);

            this.dispose();
        });






    }
}
