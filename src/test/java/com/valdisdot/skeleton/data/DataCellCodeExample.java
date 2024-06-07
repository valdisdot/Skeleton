package com.valdisdot.skeleton.data;

import com.valdisdot.skeleton.OLD.data.DataCell;
import com.valdisdot.skeleton.OLD.data.element.DataCellGroup;
import com.valdisdot.skeleton.OLD.data.element.ElementGroup;
import com.valdisdot.skeleton.OLD.ui.gui.element.CheckBox;
import com.valdisdot.skeleton.OLD.ui.gui.element.ComboList;
import com.valdisdot.skeleton.OLD.ui.gui.element.TextInputElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DataCellCodeExample {
    public static void main(String[] args) {
        //experiment1();
        //experiment2();
        //experiment3();
    }

    static void experiment1() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));

        TextInputElement textInputElement = new TextInputElement("some name");
        JComponent component = textInputElement.get();
        component.setPreferredSize(new Dimension(150, 50));
        panel.add(component);

        JButton clean = new JButton("clean");
        clean.addActionListener(l -> textInputElement.getDataCell().setData(""));
        panel.add(clean);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static void experiment2() {
        /*
        Output:
            term_display: 63 K
            checked_by_operator: false
            term_display: 91 K
            checked_by_operator: true
        */
        //data section definition section
        String termDisplayName = "term_display";
        String checkboxName = "checked_by_operator";
        String checkResultName = "check_result";
        //data controller section
        TextInputElement textInputElement = new TextInputElement(termDisplayName);
        CheckBox checkBox = new CheckBox(checkboxName, checkboxName, false, "true", "false");
        ComboList comboList = new ComboList(
                checkResultName,
                List.of("everything is ok", "temperature is too high", "replacing is needed"),
                null
        );
        ElementGroup<String> dataCellGroup = new ElementGroup<>(
                List.of(
                        textInputElement,
                        checkBox
                )
        );
        dataCellGroup.addElement(comboList);
        //generator
        Thread thread = new Thread(() -> {
            boolean run = true;
            while (run) {
                dataCellGroup.setDataForCell(termDisplayName, ((int) (Math.random() * 200)) + " K");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupting...");
                    run = false;
                }
            }
        });
        thread.start();
        //gui controller section
        JButton printButton = new JButton("Print");
        printButton.addActionListener(l -> dataCellGroup.getAllData().forEach((name, value) -> System.out.println(name + ": " + value)));
        JButton interruptGeneratorButton = new JButton("Interrupt");
        interruptGeneratorButton.addActionListener(l -> thread.interrupt());
        //gui view section
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(370, 150));
        JComponent component;
        component = textInputElement.get();
        component.setPreferredSize(new Dimension(350, 30));
        panel.add(component);
        component = new JLabel("Checked: ");
        panel.add(component);
        component = checkBox.get();
        panel.add(component);
        component = comboList.get();
        panel.add(component);
        panel.add(printButton);
        panel.add(interruptGeneratorButton);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static void experiment3() {
        /*
        Output:
        {
            "cell1": "6860f9d4-f6ca-4d10-a881-88305f921dd2";
        }
        {
            "cell2": "5a6ccc97-63a1-443d-9138-127adc7c6815";
            "cell3": "414ae549-2fa4-4274-95bf-90b806650ac0";
            "cell1": "6860f9d4-f6ca-4d10-a881-88305f921dd2";
        }
        */
        DataCellGroup<String> dataCellGroup = new DataCellGroup<>();
        dataCellGroup.put("cell1", new DataCell<>(UUID.randomUUID()::toString, (s -> {
        })));
        dataCellGroup.put("cell2", new DataCell<>(UUID.randomUUID()::toString, (s -> {
        })));
        dataCellGroup.put("cell3", new DataCell<>(UUID.randomUUID()::toString, (s -> {
        })));

        Function<Map<String, String>, String> allCellsConverter = (cellMap) -> {
            StringBuilder builder = new StringBuilder("{\n");
            cellMap.forEach((key, value) -> builder.append("\t\"").append(key).append("\": \"").append(value).append("\";\n"));
            return builder.append("}").toString();
        };
        BiFunction<String, String, String> singleCellConverter = (key, value) -> {
            return "{\n\t\"" + key + "\": \"" + value + "\";\n}";
        };

        System.out.println(dataCellGroup.getDataForCell("cell1", singleCellConverter));
        System.out.println(dataCellGroup.getAllData(allCellsConverter));
    }
}
