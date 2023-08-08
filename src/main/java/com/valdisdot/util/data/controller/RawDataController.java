package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class RawDataController<D> implements DataController {
    private final DataCellGroup<D> dataCellGroup;
    private final Consumer<Map<String, D>> dataConsumer;

    public RawDataController(DataCellGroup<D> dataCellGroup, Consumer<Map<String, D>> dataConsumer) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.dataConsumer = Objects.requireNonNull(dataConsumer);
    }

    @Override
    public void process() {
        dataConsumer.accept(dataCellGroup.getAllData());
    }
}
