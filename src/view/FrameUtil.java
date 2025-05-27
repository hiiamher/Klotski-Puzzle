package view;

import javax.swing.*;
import java.awt.*;

/**
 * This class is to create basic JComponent.
 */
public class FrameUtil {
    //创建一个JLabel组件并将其添加到指定的JFrame中，text为显示的文本
    public static JLabel createJLabel(JFrame frame, Point location, int width, int height, String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setSize(width, height);
        jLabel.setLocation(location);
        frame.add(jLabel);
        return jLabel;
    }

    //创建一个带有字体的JLabel组件并将其添加到指定的JFrame中，name为显示的文本，font为字体
    public static JLabel createJLabel(JFrame frame, String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        frame.add(label);
        return label;
    }
//创建一个JTextField组件并将其添加到指定的JFrame中,JTextField组件可以输入文本
    public static JTextField createJTextField(JFrame frame, Point location, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(width, height);
        jTextField.setLocation(location);
        frame.add(jTextField);
        return jTextField;
    }
//·创建一个JButton组件并将其添加到指定的JFrame中
    public static JButton createButton(JFrame frame, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        frame.add(button);
        return button;
    }
//·创建一个JButton组件并将其添加到指定的JDialog中
    public static JButton createButton(JDialog dialog, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        dialog.add(button);
        return button;
    }

    //创建一个自定义的圆形JButton组件并将其添加到指定的JFrame中
    public static JButton createButton(JFrame frame, String text, Point location, int diameter,Image image,Color foreground) {
        CircularButton button = new CircularButton(text,image,foreground);
        button.setLocation(location);
        button.setPreferredSize(new Dimension(diameter, diameter));
        frame.add(button);
        return button;
    }



}
