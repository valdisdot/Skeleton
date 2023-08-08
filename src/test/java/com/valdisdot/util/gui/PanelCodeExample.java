package com.valdisdot.util.gui;

import com.valdisdot.util.data.controller.BulkResetDataController;
import com.valdisdot.util.data.controller.RawDataController;
import com.valdisdot.util.data.element.ElementGroup;
import com.valdisdot.util.tool.Debug;
import com.valdisdot.util.ui.gui.component.ControlButton;
import com.valdisdot.util.ui.gui.component.Panel;
import com.valdisdot.util.ui.gui.element.CheckBox;
import com.valdisdot.util.ui.gui.element.JElement;
import com.valdisdot.util.ui.gui.element.TextInputElement;
import com.valdisdot.util.ui.gui.util.LayoutManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelCodeExample {
    public static void main(String[] args) {
        List<JElement<String>> jElements = new ArrayList<>();
        jElements.add(new TextInputElement("text_area", new JTextArea()));
        jElements.add(new CheckBox("check_box", "checked", false, "true", "false"));

        ElementGroup<String> elementGroup = new ElementGroup<>(jElements);
        ControlButton controlButton = new ControlButton(new JButton("print"), new RawDataController<>(elementGroup, System.out::println));
        ControlButton cleanButton = new ControlButton(new JButton("clean"), new BulkResetDataController<>(elementGroup, ""));
        Panel horizontalPanel = new Panel(LayoutManager.Order.HORIZONTAL, 5, 5, true);
        horizontalPanel.add(controlButton);
        horizontalPanel.add(cleanButton);
        Panel rootPanel = new Panel();
        rootPanel.add(jElements);
        rootPanel.add(horizontalPanel);
        Debug.playOnDesk(rootPanel.get(), new Dimension(1000, 1000), 0x666d43);
    }
}
