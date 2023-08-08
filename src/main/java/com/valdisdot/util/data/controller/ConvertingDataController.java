package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConvertingDataController<D, C> implements DataController {
    private final DataCellGroup<D> dataCellGroup;
    private final Function<Map<String, D>, C> convertingFunction;
    private final Consumer<C> dataConsumer;

    public ConvertingDataController(DataCellGroup<D> dataCellGroup, Function<Map<String, D>, C> convertingFunction, Consumer<C> dataConsumer) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.convertingFunction = Objects.requireNonNull(convertingFunction);
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
    }

    @Override
    public void process() {
        dataConsumer.accept(convertingFunction.apply(dataCellGroup.getAllData()));
    }
}
