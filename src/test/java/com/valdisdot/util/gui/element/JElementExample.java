package com.valdisdot.util.gui.element;

import com.valdisdot.util.ui.gui.element.*;
import com.valdisdot.util.ui.gui.tool.FrameFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

public class JElementExample {
    public static void main(String[] args) {
        JPanel panel = new JPanel(new MigLayout("wrap"));
        checkBox(panel);
        comboList(panel);
        contentButton(panel);
        modifiableLabel(panel);
        multiList(panel);
        radioButtons(panel);
        textInputElement(panel);
        scrollableTextArea(panel);
        slider(panel);
        spinner(panel);
        scrollableTextArea(panel);
        FrameFactory.playOnDesk(panel);
    }
    static void checkBox(JPanel panel){
        CheckBox checkBox = new CheckBox("is_checked", "Is checked?", false, "sure", "nope");
        JCheckBox jCheckBox = new JCheckBox("Is checked?");
        jCheckBox.setSelected(false);

        checkBox = new CheckBox("is_checked", jCheckBox, "sure", "nope");
        panel.add(checkBox.get());
    }

    static void comboList(JPanel panel){
        ComboList comboList = new ComboList(
                "favourite_color",
                List.of("white", "red", "blue", "green", "other"),
                (currentValue, values) -> {
                    String lastValue = values.get(values.size() - 1);
                    return currentValue.equals(lastValue) ? 0 : values.size() - 1;
                });

        JComboBox<String> comboBox = new JComboBox<>(new Vector<>(List.of("white", "red", "blue", "green", "other")));
        comboList = new ComboList(
                "favourite_color",
                comboBox,
                null
        );
        panel.add(comboList.get());
    }

    static void contentButton(JPanel panel){
        JButton button = new JButton("Accept");
        ContentButton contentButton = new ContentButton("accepted", button, "true", "false");
        panel.add(contentButton.get());
    }

    static void modifiableLabel(JPanel panel){
        JLabel label = new JLabel("0");
        ModifiableLabel modifiableLabel = new ModifiableLabel("counter", label);
        panel.add(modifiableLabel.get());
    }


    static void multiList(JPanel panel){
        JList<String> jList = new JList<>(new String[]{"rates", "deposit", "registration", "loans"});
        MultiList<String> multiList = new MultiList<>("request_type", jList);
        panel.add(multiList.get());
    }
    static void radioButtons(JPanel panel){
        LinkedHashMap<JRadioButton, String> orderedMap = new LinkedHashMap<>();
        orderedMap.put(new JRadioButton("Happy", true), "3");
        orderedMap.put(new JRadioButton("Neutral", false), "2");
        orderedMap.put(new JRadioButton("Sad", false), "1");
        RadioButtons radioButtons = new RadioButtons("customer_happiness", orderedMap);
        panel.add(radioButtons.get());
    }
    static void textInputElement(JPanel panel){
        TextInputElement textInputElement = new TextInputElement("customer_phone");

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(160, 25));
        textField.setFont(new Font("Arial", Font.BOLD, 13));
        textInputElement = new TextInputElement("customer_phone", textField);
        panel.add(textInputElement.get());

    }
    static void scrollableTextArea(JPanel panel){
        JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        ScrollableTextArea scrollableTextArea = new ScrollableTextArea("comment", textArea);
        panel.add(scrollableTextArea.get());
    }
    static void slider(JPanel panel){
        LinkedHashMap<String, String> orderedMap = new LinkedHashMap<>();
        orderedMap.put("Happy", "3");
        orderedMap.put("Neutral", "2");
        orderedMap.put("Sad", "1");
        Slider<String> slider = new Slider<>("customer_happiness", orderedMap);
        panel.add(slider.get());
    }
    static void spinner(JPanel panel){
        Spinner spinner = new Spinner("rate", Spinner.asNumberRange(1, 5, 1));
        panel.add(spinner.get());
        JSpinner jSpinner = new JSpinner();
        jSpinner.setPreferredSize(new Dimension(150, 30));
        spinner = new Spinner("rate", jSpinner, Spinner.asList(List.of("good", "neutral", "bad")));
        panel.add(spinner.get());
    }
    static void wrappedDataElement(JPanel panel){
        Spinner spinner = new Spinner("rate", Spinner.asNumberRange(1, 5, 1));
        WrappedDataElement<String, Integer> wrappedElement = new WrappedDataElement<>(spinner, Integer::parseInt, String::valueOf);
        int data = wrappedElement.getDataCell().getData();
        panel.add(wrappedElement.get());
    }
}