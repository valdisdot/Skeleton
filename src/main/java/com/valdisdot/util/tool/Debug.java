package com.valdisdot.util.tool;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;

public class Debug {
    public static void playOnDesk(Collection<JComponent> components, Dimension panelSize, int startWithRGBHex){
        new Thread(getOnTheDeskRunnable(components, panelSize, startWithRGBHex)).start();
    }

    public static void playOnDesk(JComponent component, Dimension panelSize, int startWithRGBHex){
        playOnDesk(Collections.singletonList(component), panelSize, startWithRGBHex);
    }

    public static Runnable getOnTheDeskRunnable(Collection<JComponent> components, Dimension panelSize, int startWithRGBHex) {
        return () -> {
            JPanel panel = new JPanel();
            panel.setPreferredSize(panelSize);

            panel.setBackground(new Color(startWithRGBHex));
            int red = (startWithRGBHex >> 16) & 0xFF;
            int green = (startWithRGBHex >> 8) & 0xFF;
            int blue = startWithRGBHex & 0xFF;
            int maxColorValue = 255;

            int reversedRed = maxColorValue - red;
            int reversedGreen = maxColorValue - green;
            int reversedBlue = maxColorValue - blue;
            int reversedColor = (reversedRed << 16) | (reversedGreen << 8) | reversedBlue;

            components.forEach(component -> {
                component.setBackground(new Color(reversedColor));
                panel.add(component);
            });

            JFrame frame = new JFrame("DESK | com.valdisdot.util");
            frame.add(panel);
            frame.repaint();
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        };
    }
}
