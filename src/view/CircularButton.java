package view;

import view.game.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircularButton extends JButton {
    private int diameter;
    private Image backgroundImage;
    private Color foregroundColor;

    public CircularButton(String label, Image backgroundImage, Color foregroundColor) {
        super(label);
        this.backgroundImage = backgroundImage;
        this.foregroundColor = foregroundColor;
        // 设置按钮的直径为图像的宽度
        this.diameter = backgroundImage.getWidth(null);
        // 根据按钮大小缩放图像
        this.backgroundImage = ImageLoader.scaleImage(backgroundImage, diameter, diameter);
        this.diameter = backgroundImage.getWidth(null);
        setSize(diameter, diameter);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 创建一个椭圆形
        Ellipse2D circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());

        // 绘制背景图像
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // 设置前景颜色
        g2d.setColor(foregroundColor);
        g2d.draw(circle);

        // 绘制文本
        super.paintComponent(g);

        if(getModel().isPressed()){
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(circle);
        }
    }



    @Override
    protected void paintBorder(Graphics g) {
        // 不需要绘制边框，因为已经在 paintComponent 中绘制了
    }

    @Override
    public boolean contains(int x, int y) {
        // 确保点击区域是圆形
        Ellipse2D circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        return circle.contains(x, y);
    }
}
