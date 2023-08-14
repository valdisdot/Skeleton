package com.valdisdot.util.gui;

import com.valdisdot.util.data.controller.BulkResetDataController;
import com.valdisdot.util.data.controller.ConvertingDataController;
import com.valdisdot.util.data.controller.RawDataController;
import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.Control;
import com.valdisdot.util.ui.gui.GUI;
import com.valdisdot.util.ui.gui.parser.json.JsonGUIParser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class GUIExample {
    public static void main(String[] args) throws IOException {
        //parse gui.json
        GUI gui = new GUI(new JsonGUIParser(new File("D:\\users\\main\\documents\\Projects\\Utilities\\sketch\\gui.json")));
        //get control
        Map<String, Control> controlMap = gui.getControlMap();
        Control customerRequestWindowsControl = controlMap.get("customer_request_window");
        Control monitorWindowsControl = controlMap.get("monitor");
        //fill with some business logic
        //get data consumer from 'monitor' - 'monitor_area'
        Consumer<String> dataConsumer = new Consumer<String>() {
            StringBuilder builder = new StringBuilder();
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell(
                        "monitor_area",
                        builder.append(s).append("\n\n").toString());
            }
        };
        //get data consumer from 'monitor' - 'counter'
        Consumer<String> counter = new Consumer<String>() {
            int counter = 0;
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell("counter", String.valueOf(++counter));
            }
        };
        //define get data controller
        ConvertingDataController<String, String> sendToMonitorController = new ConvertingDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ValuesParser::toKeySemicolonValueString,
                dataConsumer.andThen( //send data to monitor
                        counter //and then increase counter (which ignores data)
                )
        );
        //define print data controller
        RawDataController<String> printController = new RawDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                System.out::println
        );
        //define clean data controller
        BulkResetDataController<String> resetController = new BulkResetDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ""
        );
        //bind action listeners for buttons
        customerRequestWindowsControl.addActionForButton("send", l -> sendToMonitorController.process());
        customerRequestWindowsControl.addActionForButton("print", l -> printController.process());
        customerRequestWindowsControl.addActionForButton("clean", l -> resetController.process());
        //show view
        gui.getFrames().stream().forEach(frame -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });
    }
}
