package com.valdisdot.util.tool;

import javax.swing.*;
import java.awt.*;

public class Debug {
    public static Runnable getOnTheDesk(JComponent component, int startWithRGBHex) {
        return () -> {
            JPanel panel = new JPanel();
            Dimension size = component.getPreferredSize();
            size = new Dimension(
                    Math.max(640, size.width + 20),
                    Math.max(480, size.height + 20)
            );
            panel.setPreferredSize(size);

            //gpt
            int color = startWithRGBHex; //hex
            panel.setBackground(new Color(color));
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            int maxColorValue = 255;

            int reversedRed = maxColorValue - red;
            int reversedGreen = maxColorValue - green;
            int reversedBlue = maxColorValue - blue;

            // Reconstruct the new reversed RGB color
            int reversedColor = (reversedRed << 16) | (reversedGreen << 8) | reversedBlue;
            component.setBackground(new Color(reversedColor));

            panel.add(component);
            JFrame frame = new JFrame("DECK | com.valdisdot.util");
            frame.add(panel);
            frame.repaint();
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        new JFrame(){
//            {
//                t
//            }
//        };
            frame.setVisible(true);
        };
    }
}
