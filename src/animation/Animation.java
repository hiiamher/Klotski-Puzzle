package animation;

import view.game.BoxComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animation {


    public static void translation(BoxComponent box, int speed, int targetcol, int targetrow) {

        box.getGamePanel().setFocusable(false);

        Timer timer = new Timer(15, new ActionListener() {
            final int gridSize = box.getGamePanel().getGRID_SIZE();
            float alpha = 1.0f;
            final float alphaStep = 0.05f;
            // 用于标记透明度是递增还是递减
            boolean increasing = false;
            int currentSpeed = speed;
            final int speedStep = 2;

            @Override
            public void actionPerformed(ActionEvent e) {
                int targetX = targetcol * gridSize + 2;
                int targetY = targetrow * gridSize + 2;
                int currentX = box.getX();
                int currentY = box.getY();

                // 水平移动
                if (currentX < targetX) {
                    box.setLocation(currentX + Math.min(currentSpeed, targetX - currentX), currentY);
                } else if (currentX > targetX) {
                    box.setLocation(currentX - Math.min(currentSpeed, currentX - targetX), currentY);
                }

                // 垂直移动
                if (currentY < targetY) {
                    box.setLocation(currentX, currentY + Math.min(currentSpeed, targetY - currentY));
                } else if (currentY > targetY) {
                    box.setLocation(currentX, currentY - Math.min(currentSpeed, currentY - targetY));
                }

                // 动态调整透明度
                if (increasing) {
                    alpha += alphaStep;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f;
                        increasing = false;
                    }
                } else {
                    alpha -= alphaStep;
                    if (alpha <= 0.3f) {
                        alpha = 0.3f;
                        increasing = true;
                    }
                }
                box.setAlpha(alpha);

                // 动态调整速度
                if (currentSpeed < speed * 2) {
                    currentSpeed += speedStep;
                } else {
                    currentSpeed = speed;
                }

                // 检查是否到达目标位置
                if (currentX <= targetX + 2 && currentX >= targetX - 2 && currentY <= targetY + 2 && currentY >= targetY - 2) {


                    ((Timer) e.getSource()).stop();
                    box.setLocation(targetX, targetY);
                    box.setAlpha(1.0f);
                    box.getGamePanel().setFocusable(true);
                    box.getGamePanel().requestFocus();
                }
                box.repaint();
            }
        });

        timer.start();
    }


}

