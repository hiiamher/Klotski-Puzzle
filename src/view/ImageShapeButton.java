package view;

import view.game.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.plaf.basic.BasicButtonUI;

public class ImageShapeButton extends JButton {
    private Image defaultImage;
    private Image pressedImage;

    public ImageShapeButton(String defaultImagePath, String pressedImagePath,int width,int height) {
        super();
            defaultImage = ImageLoader.scaleImage(ImageLoader.loadImage(defaultImagePath),width, height);
            pressedImage = ImageLoader.scaleImage(ImageLoader.loadImage(pressedImagePath),width,height);


        // 设置按钮的大小与图片大小一致
        this.setSize(width, height);
        // 移除默认的按钮样式
        this.setUI(new BasicButtonUI());
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        // 添加鼠标事件监听器
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 根据按钮的状态绘制不同的图片
        if (getModel().isPressed()) {
            g2d.drawImage(pressedImage, 0, 0, this);
        } else {
            g2d.drawImage(defaultImage, 0, 0, this);
        }
    }

}
