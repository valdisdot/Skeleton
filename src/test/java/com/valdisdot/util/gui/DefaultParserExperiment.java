package com.valdisdot.util.gui;

import com.valdisdot.util.data.controller.BulkResetDataController;
import com.valdisdot.util.data.controller.ConvertingDataController;
import com.valdisdot.util.data.element.DataCellGroup;
import com.valdisdot.util.tool.ValuesParser;
import com.valdisdot.util.ui.gui.component.ComponentType;
import com.valdisdot.util.ui.gui.parser.DefaultParsedView;
import com.valdisdot.util.ui.gui.parser.ParsedView;
import com.valdisdot.util.ui.gui.mold.ElementMold;
import com.valdisdot.util.ui.gui.mold.PanelMold;
import com.valdisdot.util.FrameFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DefaultParserExperiment {
    public static void main(String[] args) {
        experiment1();
        experiment2();
    }

    static void experiment1() {
        PanelMold mold = new PanelMold()
                .add(new ElementMold("label", "Some label 1", "label1", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 2", "label2", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 3", "label3", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 4", "label4", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .add(new ElementMold("label", "Some label 5", "label5", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .add(new ElementMold("label", "Some label 6", "label6", List.of(), 40, 20, 0xFFFFFF, 0x000000, "Arial", "plain", 12))
                .add(new ElementMold(ComponentType.CONTROL_BUTTON.getValue(), "control_1", "control_1_name", List.of(), 60, 30, 0x4267B2, 0x0, "Arial", "plain", 12));
        mold.getMoldConstraintMap().forEach((elem, constr) -> System.out.println("constr: " + (constr.isBlank() ? "blank" : constr) + ", elem: " + elem));
        ParsedView<JPanel> parsedView = new DefaultParsedView("test", List.of(mold), ValuesParser::toJSON);
        FrameFactory.playOnDesk(parsedView.get());
        DataCellGroup<String> dataCellGroups = parsedView.getDataCellGroups();
        Map<String, Consumer<ActionListener>> buttonsActionListenerConsumers = parsedView.getButtonsActionListenerConsumers();
        System.out.println("data cell groups: " + dataCellGroups.getDataCellNames().size());
        System.out.println("control buttons: " + buttonsActionListenerConsumers.size());
        System.out.println();
    }

    static void experiment2() {
        /*
        1) create a simple panel, which contains next elements:
        name label and its text field
        id label and its text field
        phone label and its text field
        list with customer requests themes
        check box for very happy customer
        radio buttons for customer happiness level
        content button where we accept the request
        control button for send the customer request to the server
        control button for clean all fields
        Assume, that we have parsed next PanelMold from some xml or json:
         */
        PanelMold panelMold = new PanelMold();
        panelMold.setBackgroundColor(0xD3D3D3);
        panelMold
                .addToRow(new ElementMold(ComponentType.LABEL.getValue(), "Name:", null, null, 50, 25, -1, -1, null, null, -1))
                .addToRow(new ElementMold(ComponentType.TEXT_FIELD.getValue(), null, "name", List.of(""), 170, 25, -1, -1, null, null, -1))
                .addToNewRow(new ElementMold(ComponentType.LABEL.getValue(), "ID:", null, null, 50, 25, -1, -1, null, null, -1))
                .addToRow(new ElementMold(ComponentType.TEXT_FIELD.getValue(), null, "id", List.of(""), 170, 25, -1, -1, null, null, -1))
                .addToNewRow(new ElementMold(ComponentType.LABEL.getValue(), "Phone:", null, null, 50, 25, -1, -1, null, null, -1))
                .addToRow(new ElementMold(ComponentType.TEXT_FIELD.getValue(), null, "phone", List.of(""), 170, 25, -1, -1, null, null, -1))
                .addToNewRow(new ElementMold(ComponentType.LABEL.getValue(), "Customer request:", null, null, 50, 25, -1, -1, null, null, -1))
                .addToRow(new ElementMold(ComponentType.MULTI_LIST.getValue(), null, "request_type", List.of("registration", "rates", "bonuses"), 100, 25, -1, -1, null, null, -1))
                .add(new ElementMold(ComponentType.CHECK_BOX.getValue(), "Is customer satisfied?", "customer_satisfied", List.of("true", "false"), 120, 25, 0xD3D3D3, -1, null, null, -1))
                .add(new ElementMold(ComponentType.LABEL.getValue(), "Satisfaction level:", null, null, 50, 25, -1, -1, null, null, -1))
                .add(new ElementMold(ComponentType.RADIO_BUTTONS.getValue(), null, "satisfaction_level", List.of("good", "3", "medium", "2", "bad", "1"), 80, 25, -1, -1, null, null, -1))
                .add(new ElementMold(ComponentType.CONTENT_BUTTON.getValue(), "Accept", "employee_accepted", List.of("yes", "no"), 90, 35, 0x4267B2, 0xffffff, null, null, -1))
                .addToRow(new ElementMold(ComponentType.CONTROL_BUTTON.getValue(), "Send", "send_button", null, 70, 30, 0x4F7942, -1, null, null, -1))
                .addToRow(new ElementMold(ComponentType.CONTROL_BUTTON.getValue(), "Clean", "clean_button", null, 70, 30, 0xFF0000, 0xffffff, null, null, -1));
        //parse the PanelMold into ParsedView and get control
        ParsedView<JPanel> view = new DefaultParsedView("test", List.of(panelMold), ValuesParser::toJSON);
        DataCellGroup<String> dataCellGroups = view.getDataCellGroups();
        Map<String, Consumer<ActionListener>> buttonsActionListenerConsumers = view.getButtonsActionListenerConsumers();
        //define bulk data reset function
        BulkResetDataController<String> reset = new BulkResetDataController<>(dataCellGroups, "");
        //define fetch-and-send data function, which send out data to the server (to the console)
        ConvertingDataController<String, String> fetchAndSend = new ConvertingDataController<>(dataCellGroups, ValuesParser::toJSON, System.out::println);
        //next, lets define the bulk reset function for control button with name 'clean_button'
        buttonsActionListenerConsumers.get("clean_button").accept(l -> reset.process());
        //define the fetch-and-send data function for control button with name 'send_button'
        buttonsActionListenerConsumers.get("send_button").accept(l -> fetchAndSend.process());
        //finally, play the view on the desk
        FrameFactory.playOnDesk(view.get());
    }
}
