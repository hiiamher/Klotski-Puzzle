package view.showsolution;

import model.MapModel;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

public class ShowSolution extends JDialog {

    private JButton nextstepBtn;
    private JButton laststepBtn;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private List<int[][]> solution;
    private MapModel model;
    private int index = 0;


    public ShowSolution(GameFrame owner, List<int[][]> solution) {

        super(owner);

        System.out.println("Solution列表内容：");
        for (int[][] state : solution) {
            System.out.println(Arrays.deepToString(state));
        }


        setTitle("Show AISolution");
        this.setLayout(null);
        this.setSize(600, 600);
        this.setLocationRelativeTo(owner);

        this.solution = solution;
        //this.solution.add(0, owner.getGamePanel().getModel().getMatrix());

        this.setGameFrame(owner);
        this.model = new MapModel(owner.getGamePanel().getModel().getMatrix());

        GamePanel gamePanel = new GamePanel(model);
        this.setGamePanel(gamePanel);
        this.add(gamePanel);
        this.getGamePanel().setVisible(true);
        this.getGamePanel().setEnabled(false);
        this.getGamePanel().setFocusable(false);

        nextstepBtn = FrameUtil.createButton(this, "next", new Point(this.getWidth() - 220, 180), 100, 40);
        laststepBtn = FrameUtil.createButton(this, "last", new Point(this.getWidth() - 220, 260), 100, 40);

        this.add(nextstepBtn);
        this.add(laststepBtn);

        nextstepBtn.addActionListener(e -> {
            // 打印Solution列表内容
            System.out.println("Solution列表内容：");
            for (int[][] state : solution) {
                System.out.println(Arrays.deepToString(state));
            }

            if (index+1 <= solution.size()-1&&index+1>=0) {
                index++;
                int[][] nowState = solution.get(index);
                gamePanel.getModel().setMatrix(nowState);
                gamePanel.clearAllBoxFromPanel();
                gamePanel.initialGame(nowState);

            }else{
                JOptionPane.showMessageDialog(this, "已经是最后一步了", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        laststepBtn.addActionListener(e -> {
            if (index-1 >= 0&&index-1<=solution.size()-1) {
                index--;
                int[][] nowState = solution.get(index);
                gamePanel.getModel().setMatrix(nowState);
                gamePanel.clearAllBoxFromPanel();
                gamePanel.initialGame(nowState);

            }else{
                JOptionPane.showMessageDialog(this, "已经是第一步了", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        //测试
        //System.out.println(Arrays.deepToString(this.getGameFrame().getGamePanel().getModel().getMatrix()));


        //待改进
        // 创建定时器逐步展示解决方案
       /* Timer timer = new Timer(1000, e -> {  // 调整为1秒间隔
            if (!solution.isEmpty()) {
                // 在EDT中更新界面组件
                SwingUtilities.invokeLater(() -> {
                    int[][] nextState = solution.remove(0);
                    gamePanel.getModel().setMatrix(nextState);  // 更新模型
                    gamePanel.revalidate();  // 重新验证布局
                    gamePanel.repaint();     // 刷新界面
                });
            } else {
                ((Timer) e.getSource()).stop();  // 停止定时器
                JOptionPane.showMessageDialog(this, "解决方案展示完成", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        timer.start();
*/


        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (owner != null) {
                    // 请求焦点
                    owner.requestFocusInWindow();
                    // 恢复游戏面板状态
                    owner.getGamePanel().setEnabled(true);
                    owner.getGamePanel().setFocusable(true);
                }
            }
        });
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


}
