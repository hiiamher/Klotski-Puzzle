package animation;

import view.game.BoxComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animation{


    public static void translation(BoxComponent box, int speed, int targetcol, int targetrow) {


        Timer timer = new Timer(5, new ActionListener() {
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
                if (currentX == targetX && currentY == targetY) {
                    ((Timer) e.getSource()).stop();
                    // 恢复透明度
                    box.setAlpha(1.0f);
                }
                box.repaint();
            }
        });

        timer.start();
    }



//普通平移
  /*  public static void translation(BoxComponent box,int speed,int targetcol ,int targetrow ) {

        final int gridSize = box.getGamePanel().getGRID_SIZE(); // 修复字段可能为 final 的问题
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int targetX = targetcol * gridSize + 2;
                int targetY = targetrow * gridSize + 2;
                int currentX = box.getX();
                int currentY = box.getY();

                // 水平移动
                if (currentX < targetX) {
                    box.setLocation(currentX + Math.min(speed, targetX - currentX), currentY);
                } else if (currentX > targetX) {
                    box.setLocation(currentX - Math.min(speed, currentX - targetX), currentY);
                }

                // 垂直移动
                if (currentY < targetY) {
                    box.setLocation(currentX, currentY + Math.min(speed, targetY - currentY));
                } else if (currentY > targetY) {
                    box.setLocation(currentX, currentY - Math.min(speed, currentY - targetY));
                }

                // 检查是否到达目标位置
                if (currentX == targetX && currentY == targetY) {
                    ((Timer) e.getSource()).stop();
                }
                box.repaint();
            }
        });

        timer.start();
    }*/

}

