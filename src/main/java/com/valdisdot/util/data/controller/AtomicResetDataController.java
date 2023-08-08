package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Objects;
import java.util.function.Function;

public class AtomicResetDataController<D> implements DataController {
    private final DataCellGroup<D> dataCellGroup;
    private final Function<String, D> resetFunction;

    public AtomicResetDataController(DataCellGroup<D> dataCellGroup, Function<String, D> resetFunction) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.resetFunction = Objects.requireNonNull(resetFunction);
    }

    @Override
    public void process() {
        dataCellGroup.getDataCellNames().forEach(name -> dataCellGroup.setDataForCell(name, resetFunction.apply(name)));
    }
}
