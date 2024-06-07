package com.valdisdot.skeleton.OLD.ui.gui.element;

import com.valdisdot.skeleton.OLD.data.DataCell;

import java.util.function.Function;

//for cases, where converting is needed
//example, we have a JSlider, which returns Integer, but the application demands String
public class WrappedDataElement<D, C> extends JElement<C> {
    public WrappedDataElement(JElement<D> internal, Function<D, C> supplierFunction, Function<C, D> consumerFunction) {
        DataCell<D> dataCell = internal.getDataCell();
        component = internal.get();
        this.dataCell = new DataCell<C>(
                () -> supplierFunction.apply(dataCell.getData()),
                (c) -> dataCell.setData(consumerFunction.apply(c))
        );
    }

}
