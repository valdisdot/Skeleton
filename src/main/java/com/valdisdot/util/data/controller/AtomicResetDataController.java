package com.valdisdot.util.data.controller;

import com.valdisdot.util.data.element.DataCellGroup;

import java.util.Objects;
import java.util.function.Function;

/**
 * implementation for atomic reset of DataCells
 * for example, we have some DataCell with name "data_cell"
 * 1) we want to set data on this cell to "default" -> the Function<String, D> resetFunction has to accept DataCell name "data_cell" * and return "default"
 * 2)we have Iterable ["val1", "val2"] and want to set data on the cell to iterable.next -> *~ and return iterable.next
 */
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
