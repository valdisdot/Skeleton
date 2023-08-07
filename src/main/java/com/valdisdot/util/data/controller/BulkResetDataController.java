package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.Naming;

import java.util.Objects;

public class BulkResetDataController<D> implements DataController {
    private final Naming<D> dataCellGroup;
    private final D resetValue;

    public BulkResetDataController(Naming<D> dataCellGroup, D resetValue) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.resetValue = Objects.requireNonNull(resetValue);
    }

    @Override
    public void process() {
        dataCellGroup.setDataForAllCells(resetValue);
    }
}
