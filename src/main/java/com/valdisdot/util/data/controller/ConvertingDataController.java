package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/*
* implementation that reads raw data from DataCells, convert the data to specific type and consumes the result
* Code example: com.valdisdot.util.data.DataControllerCodeExample
* */
public class ConvertingDataController<D, C> implements Runnable {
    private final DataCellGroup<D> dataCellGroup;
    private final Function<Map<String, D>, C> convertingFunction;
    private final Consumer<C> dataConsumer;

    public ConvertingDataController(DataCellGroup<D> dataCellGroup, Function<Map<String, D>, C> convertingFunction, Consumer<C> dataConsumer) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.convertingFunction = Objects.requireNonNull(convertingFunction);
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
    }

    @Override
    public void run() {
        dataConsumer.accept(convertingFunction.apply(dataCellGroup.getAllData()));
    }
}
