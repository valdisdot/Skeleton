package com.valdisdot.util.tool;

import javax.swing.*;
import java.awt.*;

public class Debug {
    public static void runGetOnTheDesk(JComponent component, int startWithRGBHex){
        new Thread(getOnTheDesk(component, startWithRGBHex)).start();
    }

    public static Runnable getOnTheDesk(JComponent component, int startWithRGBHex) {
        return () -> {
            JPanel panel = new JPanel();
            Dimension size = component.getPreferredSize();
            size = new Dimension(
                    Math.max(640, size.width + 20),
                    Math.max(480, size.height + 20)
            );
            panel.setPreferredSize(size);

            panel.setBackground(new Color(startWithRGBHex));
            int red = (startWithRGBHex >> 16) & 0xFF;
            int green = (startWithRGBHex >> 8) & 0xFF;
            int blue = startWithRGBHex & 0xFF;
            int maxColorValue = 255;

            int reversedRed = maxColorValue - red;
            int reversedGreen = maxColorValue - green;
            int reversedBlue = maxColorValue - blue;
            int reversedColor = (reversedRed << 16) | (reversedGreen << 8) | reversedBlue;
            component.setBackground(new Color(reversedColor));

            panel.add(component);
            JFrame frame = new JFrame("DESK | com.valdisdot.util");
            frame.add(panel);
            frame.repaint();
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        };
    }
}
