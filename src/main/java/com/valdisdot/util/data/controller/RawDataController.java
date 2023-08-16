package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/*
* implementation that reads and consumes the Map of raw data from DataCells
* Code example: com.valdisdot.util.data.DataControllerCodeExample
* */
public class RawDataController<D> implements Runnable {
    private final DataCellGroup<D> dataCellGroup;
    private final Consumer<Map<String, D>> dataConsumer;

    public RawDataController(DataCellGroup<D> dataCellGroup, Consumer<Map<String, D>> dataConsumer) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
    }

    @Override
    public void run() {
        dataConsumer.accept(dataCellGroup.getAllData());
    }
}
