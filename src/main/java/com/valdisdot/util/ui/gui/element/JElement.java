package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;
import com.valdisdot.util.data.element.Element;

import javax.swing.*;
import java.util.Objects;

//common Element implementation for javax.swing
public abstract class JElement<T> implements Element<T, JComponent> {
    private DataCell<T> dataCell;
    private JComponent component;

    protected abstract boolean pleaseAcceptThatYouHaveDone();

    //cuz Constructor.super must be the first line
    protected void completeInitialization(JComponent component, DataCell<T> dataCell) {
        this.component = Objects.requireNonNull(component, "JComponent is not initialised");
        Objects.requireNonNull(component.getName(), "Component name is null: " + component);
        this.dataCell = dataCell;
        pleaseAcceptThatYouHaveDone();
    }


    @Override
    public String getName() {
        return component.getName();
    }

    @Override
    public JComponent get() {
        return Objects.requireNonNull(component, "JComponent is not initialised");
    }

    @Override
    public DataCell<T> getDataCell() {
        return Objects.requireNonNull(dataCell, "DataCell is not initialised");
    }
}
