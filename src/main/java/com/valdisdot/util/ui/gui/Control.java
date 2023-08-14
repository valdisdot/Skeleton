package com.valdisdot.util.ui.gui;

import com.valdisdot.util.data.element.DataCellGroup;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Consumer;

//data class for holding DataCellGroup of String and Map of String, Consumer<ActionListener> for control buttons
//also, simplify some operation with DataCellGroup and binding with ActionListener for control buttons
public class Control {
    private final DataCellGroup<String> dataCellGroup;
    private final Map<String, Consumer<ActionListener>> controlButtonsActionListenersConsumers;

    Control(DataCellGroup<String> dataCellGroup, Map<String, Consumer<ActionListener>> controlButtonsActionListenersConsumers) {
        this.dataCellGroup = dataCellGroup;
        this.controlButtonsActionListenersConsumers = controlButtonsActionListenersConsumers;
    }

    public DataCellGroup<String> getDataCellGroup() {
        return dataCellGroup;
    }

    public Map<String, Consumer<ActionListener>> getControlButtonsActionListenersConsumers() {
        return controlButtonsActionListenersConsumers;
    }

    //simplifying control.getDataCellGroup().someAction(dataCellName, data)
    public void setDataForCell(String dataCellName, String data) {
        dataCellGroup.setDataForCell(dataCellName, data);
    }

    public String getDataForCell(String dataCellName) {
        return dataCellGroup.getDataForCell(dataCellName);
    }

    //simplifying ActionListener binding
    public void addActionForButton(String controlButtonName, ActionListener actionListener) {
        controlButtonsActionListenersConsumers.get(controlButtonName).accept(actionListener);
    }
}
