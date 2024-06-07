package com.valdisdot.skeleton.OLD.ui.gui.element;

import com.valdisdot.skeleton.OLD.data.DataCell;
import com.valdisdot.skeleton.OLD.data.element.Element;

import javax.swing.*;
import java.util.Objects;

//common Element implementation for javax.swing
public abstract class JElement<D> implements Element<D, JComponent> {
    protected DataCell<D> dataCell;
    protected JComponent component;

    @Override
    public String getName() {
        return Objects.requireNonNull(Objects.requireNonNull(component, "JComponent is not initialised").getName(), "Component name is null: " + component);
    }

    @Override
    public JComponent get() {
        return Objects.requireNonNull(component, "JComponent is not initialised");
    }

    @Override
    public DataCell<D> getDataCell() {
        return Objects.requireNonNull(dataCell, "DataCell is not initialised. " + component);
    }
}
