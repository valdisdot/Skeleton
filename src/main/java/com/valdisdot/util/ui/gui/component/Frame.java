package com.valdisdot.util.ui.gui.component;

import com.valdisdot.util.ui.gui.tool.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

//implementation of JFrame with JMenu
public class Frame extends JFrame {
    //link for a current displaying element
    private JComponent current;

    public Frame(String frameTitle, String menuName, Color menuColor, LinkedHashMap<String, JPanel> menuItems){
        super(frameTitle);
        //lay first panel
        String firstPanelKey = menuItems.keySet().stream().limit(1).findAny().orElseThrow(() -> new IllegalArgumentException("LinkedHashMap of menu items is empty."));
        current = menuItems.get(firstPanelKey);
        add(current);
        //init menu
        JMenu menu = new JMenu(menuName);
        boolean isDark = Colors.isDark(menuColor.getRGB());
        menu.setBackground(menuColor);
        Color foreground = isDark ? Color.WHITE : Color.BLACK;
        //fill menu with call-panel items
        menuItems.forEach((label, panel) -> {
            JMenuItem item = new JMenuItem(label);
            item.setBackground(menuColor);
            item.setForeground(foreground);
            //set on-click action
            item.addActionListener(l -> {
                //change current element by a label
                remove(current);
                current = menuItems.get(label);
                add(current);
                pack();
            });
            menu.add(item);
        });
        //add menu bar for the frame
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(menuColor);
        menuBar.setForeground(foreground);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        //init frame
        pack();
    }
}
