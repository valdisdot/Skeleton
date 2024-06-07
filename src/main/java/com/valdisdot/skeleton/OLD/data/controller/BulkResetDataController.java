package com.valdisdot.skeleton.OLD.data.controller;

import com.valdisdot.skeleton.OLD.data.element.DataCellGroup;

import java.util.Objects;
/*
* implementation for bulk reset of all DataCells
* for example, we need set all data in DataCells to "" or is the set function of the DataCell works like a switcher and ignores the resetValue
* */
public class BulkResetDataController<D> implements Runnable {
    private final DataCellGroup<D> dataCellGroup;
    private final D resetValue;

    public BulkResetDataController(DataCellGroup<D> dataCellGroup, D resetValue) {
        this.dataCellGroup = Objects.requireNonNull(dataCellGroup);
        this.resetValue = Objects.requireNonNull(resetValue);
    }

    @Override
    public void run() {
        dataCellGroup.setDataForAllCells(resetValue);
    }
}
