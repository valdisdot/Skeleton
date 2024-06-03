package com.valdisdot.skeleton.ui.gui.component;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class FrameWithSidebar extends JFrame {

    public FrameWithSidebar(String frameTitle, Color buttonColor, LinkedHashMap<String, JPanel> menuItems) {
        super(frameTitle);
        add(new SidebarPanel(buttonColor, menuItems, this::pack));
        pack();
    }
}
