package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
public class MouseTrailPanel extends JPanel implements MouseMotionListener {
        // 存储鼠标轨迹坐标点
        private final List<Point> trailPoints = new ArrayList<>();
        // 轨迹颜色（示例为红色，可自定义）
        private Color trailColor = Color.RED;
        // 轨迹点大小
        private int pointSize = 3;
        //轨迹长度
        private int trailSize = 10;

        public MouseTrailPanel() {
            // 设置面板透明
            setOpaque(false);
            //设置不可聚焦
            setFocusable(false);
            setRequestFocusEnabled(false);
            // 注册鼠标移动监听器
            addMouseMotionListener(this);
            setDoubleBuffered(true);
        }

        // 绘制轨迹
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // 抗锯齿设置
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置轨迹颜色
            g2d.setColor(trailColor);

            // 绘制所有轨迹点
            for (Point point : trailPoints) {
                g2d.fillOval(point.x - pointSize/2,
                        point.y - pointSize/2,
                        pointSize, pointSize);
            }

            g2d.dispose();
        }

        // 鼠标移动时添加轨迹点
        @Override
        public void mouseDragged(MouseEvent e) {
            trailPoints.add(0,e.getPoint());
            if(trailPoints.size() >= trailSize ) {
               trailPoints.remove(trailSize-1);
            }
            // 刷新面板显示新轨迹
            repaint();
        }

        // 鼠标移动（非拖拽）时不处理（可根据需求调整）
        @Override
        public void mouseMoved(MouseEvent e) {
            trailPoints.add(0,e.getPoint());
            if(trailPoints.size() >= trailSize ) {
                trailPoints.remove(trailSize-1);
            }
            // 刷新面板显示新轨迹
            repaint();
        }

        public void Mouseupdtae(MouseEvent e) {
            trailPoints.add(0,e.getPoint());
            if(trailPoints.size() >= trailSize ) {
                trailPoints.remove(trailSize-1);
            }
            // 刷新面板显示新轨迹
            repaint();
        }

        // 清空轨迹（可选方法）
        public void clearTrail() {
            trailPoints.clear();
            repaint();
        }

        // 自定义轨迹颜色
        public void setTrailColor(Color color) {
            this.trailColor = color;
        }

        // 自定义轨迹点大小
        public void setPointSize(int size) {
            this.pointSize = size;
        }

        public boolean mouseEvent(MouseEvent e) {
            return false;
        }

        public boolean contains(int x, int y) {
            return false;
        }


    }


