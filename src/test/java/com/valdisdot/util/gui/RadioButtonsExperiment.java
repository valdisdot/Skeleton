package com.valdisdot.util.gui;

import com.valdisdot.util.ui.gui.tool.FrameFactory;
import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.element.JElement;
import com.valdisdot.util.ui.gui.element.RadioButtons;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RadioButtonsExperiment {
    public static void main(String[] args) {
        experiment1();
    }

    static void experiment1() {
        JElement<String> radio = new RadioButtons(
                "fruit",
                new LinkedHashMap<>(Map.of(
                        new JRadioButton("Apple"), "apple",
                        new JRadioButton("Orange"), "orange"
                )));
        FrameFactory.playOnDesk(radio, "", map -> System.out.println(ValuesParser.toKeySemicolonValueString(map)));
    }
}
