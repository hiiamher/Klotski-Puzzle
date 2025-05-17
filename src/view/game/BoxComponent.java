package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoxComponent extends JComponent {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;
    private float alpha = 1.0f;


    private GamePanel gamePanel;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }




    public BoxComponent(Color color, int row, int col, GamePanel gamePanel) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.gamePanel = gamePanel;
        isSelected = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2d);
        g2d.setColor(color);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        Border border;
        if (isSelected) {
            border = BorderFactory.createLineBorder(Color.red, 3);
        } else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
        g2d.dispose();
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }




}
