package com.valdisdot.util.gui;

import com.valdisdot.util.ui.gui.component.FrameWithSidebar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FrameWithSideMenueTest {
    public static void main(String[] args) {
        Dimension elementSize = new Dimension(50, 50);
        Map<String, JPanel> map = java.util.List.of("Panel one", "Panel two", "Panel three", "Panel four", "Panel five", "Panel six", "Panel seven", "Panel eight", "Panel nine", "Panel ten", "Panel eleven")
                .stream().collect(Collectors.toMap(
                        s -> s,
                        s -> {
                            int rowSize = (int) (Math.random() * 2 + 3);
                            JPanel panel = new JPanel(new MigLayout("wrap " + rowSize));
                            Stream.generate(() -> {
                                        JPanel elem = new JPanel();
                                        Color color = new Color((int) (Math.random() * Integer.MAX_VALUE));
                                        elem.setPreferredSize(elementSize);
                                        elem.setBackground(color);
                                        JLabel label = new JLabel(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
                                        label.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                System.out.println(label.getText());
                                            }
                                        });
                                        label.setBackground(Color.WHITE);
                                        label.setOpaque(true);
                                        elem.add(label);
                                        return elem;
                                    })
                                    .limit((int) (Math.random() * 50 + 1))
                                    .forEach(panel::add);
                            return panel;
                        }
                ));
        JFrame frame = new FrameWithSidebar("test", Color.RED, new LinkedHashMap<>(map));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
