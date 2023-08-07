package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.Naming;

import java.util.Objects;
import java.util.function.Function;

public class AtomicResetDataController<D> implements DataController{
    private Naming<D> dataCellGroup;
    private Function<String, D> resetFunction;

    public AtomicResetDataController(Naming<D> dataCellGroup, Function<String, D> resetFunction){
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.resetFunction = Objects.requireNonNull(resetFunction);
    }

    @Override
    public void process() {
        dataCellGroup.getDataCellNames().forEach(name -> dataCellGroup.setDataForCell(name, resetFunction.apply(name)));
    }
}
