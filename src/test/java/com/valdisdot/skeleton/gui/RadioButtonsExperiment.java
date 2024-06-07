package com.valdisdot.skeleton.gui;

import com.valdisdot.skeleton.FrameFactory;
import com.valdisdot.skeleton.OLD.tool.ValuesParser;
import com.valdisdot.skeleton.OLD.ui.gui.element.JElement;
import com.valdisdot.skeleton.OLD.ui.gui.element.RadioButtons;

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
        FrameFactory.playOnDesk(radio, "", map -> System.out.println(ValuesParser.toJSON(map)));
    }
}
