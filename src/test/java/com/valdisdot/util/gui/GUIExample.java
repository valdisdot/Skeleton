package com.valdisdot.util.gui;

import com.valdisdot.util.data.controller.BulkResetDataController;
import com.valdisdot.util.data.controller.ConvertingDataController;
import com.valdisdot.util.data.controller.RawDataController;
import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.Control;
import com.valdisdot.util.ui.gui.GUI;
import com.valdisdot.util.ui.gui.parser.json.JsonApplicationMoldParser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class GUIExample {
    public static void main(String[] args) throws IOException {
        //parse gui.json
        GUI gui = new GUI(new JsonApplicationMoldParser(new File("D:\\users\\main\\documents\\Projects\\Utilities\\sketch\\gui.json")).getApplicationMold());

        //get control
        Map<String, Control> controlMap = gui.getControlMap();
        Control customerRequestWindowsControl = controlMap.get("customer_request_window");
        Control monitorWindowsControl = controlMap.get("monitor");

        //fill with some business logic
        //1) get data consumer from 'monitor' - 'monitor_area'
        Consumer<String> dataConsumer = new Consumer<String>() {
            StringBuilder builder = new StringBuilder();
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell(
                        "monitor_area",
                        builder.append(s).append("\n\n").toString());
            }
        };

        //2) get data consumer from 'monitor' - 'counter'
        Consumer<String> counter = new Consumer<String>() {
            int counter = 0;
            @Override
            public void accept(String s) {
                monitorWindowsControl.setDataForCell("counter", String.valueOf(++counter));
            }
        };

        //3) define get data controller
        Runnable sendToMonitorController = new ConvertingDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ValuesParser::toJSON,
                dataConsumer.andThen( //send data to monitor
                        counter //and then increase counter (which ignores data)
                )
        );

        //4) define print data controller
        Runnable printController = new RawDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                System.out::println
        );

        //5) define clean data controller
        Runnable resetController = new BulkResetDataController<>(
                customerRequestWindowsControl.getDataCellGroup(),
                ""
        );

        //bind action listeners with buttons
        customerRequestWindowsControl.bindActionForButton("send", sendToMonitorController);
        customerRequestWindowsControl.bindActionForButton("print", printController);
        customerRequestWindowsControl.bindActionForButton("clean", resetController);

        //show view
        gui.getFrames().stream().forEach(frame -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });
    }
}
