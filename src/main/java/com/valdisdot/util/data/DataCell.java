package com.valdisdot.util.data;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
The class is a container for setter/getters of GUI elements.
For example, for JTextArea it will be DataCell<String> dc = new DataCell<>(textArea::getText, textArea::setText)

*/

public class DataCell<D> {
    private final Supplier<D> supplierFunction;
    private final Consumer<D> consumerFunction;

    public DataCell(Supplier<D> supplierFunction, Consumer<D> consumerFunction) {
        this.supplierFunction = Objects.requireNonNull(supplierFunction);
        this.consumerFunction = Objects.requireNonNull(consumerFunction);
    }

    //get-set states
    public D getData(){
        return supplierFunction.get();
    }
    public void setData(D data){
        consumerFunction.accept(data);
    }
}
