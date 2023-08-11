package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import java.util.function.Function;

//for cases, where converting is needed
//example, we have a JSlider, which returns Integer, but the application demands String
public class WrappedDataElement<D, C> extends JElement<C> {
    public WrappedDataElement(JElement<D> internal, Function<D, C> supplierFunction, Function<C, D> consumerFunction) {
        DataCell<D> dataCell = internal.getDataCell();
        completeInitialization(
                internal.get(),
                new DataCell<C>(
                        () -> supplierFunction.apply(dataCell.getData()),
                        (c) -> dataCell.setData(consumerFunction.apply(c))
                )
        );
    }

    @Override
    protected boolean pleaseAcceptThatYouHaveDone() {
        return true;
    }
}
