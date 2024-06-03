package com.valdisdot.skeleton.ui.gui.component;

import com.valdisdot.skeleton.ui.gui.tool.Colors;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SidebarPanel extends JPanel {
    private JComponent currentDisplayedComponent;
    private JButton currentButton;

    public SidebarPanel(Color buttonColor, LinkedHashMap<String, JPanel> menuItems, Runnable pack) {
        if (menuItems.isEmpty()) return;
        //init panels
        setLayout(new MigLayout("insets 0,gap 0px 0px"));
        JPanel sidePanel = new JPanel(new MigLayout("wrap,insets 0,gap 0px 0px"));
        //count best colors
        Color panelsBackground = Colors.analogousColors(buttonColor).get(0);
        Color buttonForeground = Colors.isDark(buttonColor.getRGB()) ? Color.WHITE : Color.BLACK;
        setBackground(panelsBackground);
        sidePanel.setBackground(panelsBackground);
        //count border for selected and deselected buttons
        Border buttonSelectedBorder = BorderFactory.createEtchedBorder();
        Border buttonDeselectedBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        //init temporary holder for buttons
        ArrayList<JButton> buttons = new ArrayList<>(menuItems.size());
        //init buttons
        menuItems.keySet().stream()
                .map(panelLabel -> {
                    JButton button = new JButton(panelLabel);
                    JPanel itemPanel = menuItems.get(panelLabel);
                    itemPanel.setBorder(buttonSelectedBorder);
                    button.setFocusable(false);
                    button.addActionListener(l -> {
                        if (!button.equals(currentButton)) {
                            button.setBackground(itemPanel.getBackground());
                            button.setForeground(Colors.isDark(button.getBackground().getRGB()) ? Color.WHITE : Color.BLACK);
                            currentButton.setBackground(buttonColor);
                            currentButton.setForeground(buttonForeground);
                            currentButton.setBorder(buttonDeselectedBorder);
                            currentButton = button;
                            //currentButton.setBorder(BorderFactory.createLineBorder(Colors.isDark(itemPanel.getBackground().getRGB()) ? Color.WHITE : Color.BLACK, 2));
                            currentButton.setBorder(BorderFactory.createEtchedBorder());
                            remove(currentDisplayedComponent);
                            currentDisplayedComponent = itemPanel;
                            add(currentDisplayedComponent);
                            pack.run();
                        }
                    });
                    return button;
                })
                .forEach(buttons::add);
        //setup buttons design
        Dimension preferredSize = new Dimension(
                buttons.stream().mapToInt(button -> button.getPreferredSize().width).max().getAsInt(),
                buttons.stream().mapToInt(button -> button.getPreferredSize().height).max().getAsInt()
        );
        buttons.stream()
                .peek(button -> button.setPreferredSize(preferredSize))
                .peek(button -> button.setBackground(buttonColor))
                .peek(button -> button.setBorder(buttonDeselectedBorder))
                .peek(button -> button.setForeground(Colors.isDark(buttonColor.getRGB()) ? Color.WHITE : Color.BLACK))
                .forEach(sidePanel::add);

        currentButton = buttons.get(0);
        currentDisplayedComponent = menuItems.get(currentButton.getText());
        currentButton.setBackground(currentDisplayedComponent.getBackground());
        currentButton.setForeground(Colors.isDark(currentDisplayedComponent.getBackground().getRGB()) ? Color.WHITE : Color.BLACK);
        currentButton.setBorder(BorderFactory.createEtchedBorder());
        add(sidePanel, "top");
        add(currentDisplayedComponent);
    }
}
