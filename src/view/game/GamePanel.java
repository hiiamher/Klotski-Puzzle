package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import music.Music;
import view.welcome.WelcomeFrame;

import static SaveAndRead.SavaAndRead.Save;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    //存储所有的BoxComponent对象
    private List<BoxComponent> boxes;
    private MapModel model;

    private GameController controller;
    private GameFrame gameFrame;
    //存储显示步数的JLabel对象
    private JLabel stepLabel;
    private int steps;
    //格子大小
    private final int GRID_SIZE = 70;
    //选中的方块
    private BoxComponent selectedBox;
    //时间相关
    //public static int ElapsedTime;
    private Timer timer;

    private BufferedImage backgroundImage;



    private JFrame welcomeFrame;



    //1初始化游戏界面，2创建boxes对象，将游戏界面设置为可获取焦点
    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setController(controller);
        this.backgroundImage = ImageLoader.loadImage("src/格子底.jpg");
        //创建JLabel对象，用于显示步数
        stepLabel = new JLabel("Steps: 0");
        stepLabel.setBounds(5, 20, 100, 20);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;

        //初始化游戏界面
        initialGame(model.getMatrix());
    }

/*
                        {1, 2, 2, 1, 1},
                        {3, 4, 4, 2, 2},
                        {3, 4, 4, 1, 0},
                        {1, 2, 2, 1, 0},
                        {1, 1, 1, 1, 1}
     */


    public void initialGame(int[][] matrix) {
        this.steps = 0;
        //this.ElapsedTime = 0;
        if (stepLabel != null) {
            this.stepLabel.setText(String.format("Step: %d", this.steps));
        }

        //copy a map
        int[][] map = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = matrix[i][j];
            }
        }
        //build Component
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                if (map[i][j] == 1) {
                    BufferedImage image = ImageLoader.loadImage("src/小兵.jpg");
                    box = new BoxComponent(Color.ORANGE, i, j, this,image);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                    box.setType(1);
                } else if (map[i][j] == 2 && j + 1 < map[0].length) { // 检查列边界
                    BufferedImage image = ImageLoader.loadImage("src/关羽.jpg");
                    box = new BoxComponent(Color.PINK, i, j, this,image);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                    box.setType(2);
                } else if (map[i][j] == 3&&i+1<map.length) {//检查行边界
                    BufferedImage image = ImageLoader.loadImage("src/马超.jpg");
                    box = new BoxComponent(Color.BLUE, i, j, this,image);
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    box.setType(3);
                } else if (map[i][j] == 4&&i+1<map.length&&j+1<map[0].length) {
                    BufferedImage image = ImageLoader.loadImage("src/曹操.jpg");
                    box = new BoxComponent(Color.RED, i, j, this,image);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                    box.setType(4);
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.add(box);
                }
            }
        }
        this.repaint();
    }

    //调用Graphics类的fillRect方法绘制背景
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        if(backgroundImage != null) {
            for (int i = 0; i < model.getHeight(); i++) {
                for (int j = 0; j < model.getWidth(); j++) {
                    g2d.drawImage(backgroundImage,j*GRID_SIZE+2,i*GRID_SIZE+2,GRID_SIZE,GRID_SIZE,this);
                }
            }
        }
    }



    @Override

    //点击后变为选中状态，再次点击取消选中状态
    public void doMouseClick(Point point) {
        timer.restart();
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
        this.requestFocusInWindow();
    }

    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                afterMove();
            }
        }
    }

    //测试
    public void ChangeStepsLabel(int steps) {
        this.stepLabel.setText(String.format("Step: %d", steps));
    }

    public JLabel getStepLabel() {
        return stepLabel;
    }

    public void afterMove() {
        Music music = new Music("击中木块.wav");
        //Music music1 = new Music("扬帆！起航！.wav");
        music.play();
       /* if(selectedBox.getType()==4){
            music1.play();
        }*/
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        this.repaint();
        controller.getUser().setSteps(this.steps);
        controller.save_path();
        isVictory();
    }

    public JFrame getWelcomeFrame() {
        return welcomeFrame;
    }

    public void setWelcomeFrame(JFrame welcomeFrame) {
        this.welcomeFrame = welcomeFrame;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void clearAllBoxFromPanel() {
        for (BoxComponent box : boxes) {
            removeBoxFromPanel(box);
        }
        this.boxes.clear();
        this.repaint();
    }

    public BoxComponent removeBoxFromPanel(BoxComponent box) {
        this.remove(box);
        this.revalidate();
        return box;
    }


    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public void setSelectedBox(BoxComponent selectedBox) {
        this.selectedBox = selectedBox;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public List<BoxComponent> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxComponent> boxes) {
        this.boxes = boxes;
    }

    public void withDraw() {
        if (this.steps > 0) {
            this.steps--;
            this.controller.WithDraw(this.steps);
            this.stepLabel.setText(String.format("Step: %d", this.steps));
            this.repaint();
            controller.getUser().setSteps(this.steps);
        } else {
            JOptionPane.showMessageDialog(this, "You have no steps to withdraw.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void isVictory() {
        for (BoxComponent box : boxes) {
            if (box.getWidth() == 2 * getGRID_SIZE() && box.getHeight() == 2 * getGRID_SIZE()) {
                if (box.getRow() == 3 && box.getCol() == 1) {

                    //存储胜利信息

                    Save(String.format("%d",this.gameFrame.getTimeElapsed()),String.format("save/%s/%d/win", gameFrame.getUser().getUsername(), gameFrame.getUser().getLevel()),"time");
                    Save(String.format("%d",this.gameFrame.getUser().getSteps()),String.format("save/%s/%d/win", gameFrame.getUser().getUsername(), gameFrame.getUser().getLevel()),"step");

                    Music music = new Music("胜利音效.wav");
                    //Music music1 = new Music("加勒比海盗主题曲.wav");
                    //music1.play();

                    music.play();
                    timer.stop();
                    String steps = String.format("You have completed the game in %d steps.", this.steps);
                    JOptionPane.showMessageDialog(this, "Congratulations! " + "You used" + steps+ "in"+ this.gameFrame.getTimeElapsed() +"seconds!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);


                    // 弹出对话框，询问用户是否继续游戏
                    int option = JOptionPane.showConfirmDialog(this, "Do you want to continue playing?", "Continue", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        // 退出游戏
                        // 获取当前 GamePanel 所在的 JFrame
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        if (frame != null) {
                            // 关闭当前 JFrame
                            frame.dispose();
                        }
                        WelcomeFrame welcomeFrame = new WelcomeFrame(900,600);
                        welcomeFrame.setVisible(true);
                    } else {
                       System.exit(0);
                    }


                }
            }

        }
    }



    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
/*
    public int getElapsedTime() {
        return ElapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        ElapsedTime = elapsedTime;
    }*/
}


