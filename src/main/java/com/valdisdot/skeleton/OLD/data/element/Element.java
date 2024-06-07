package com.valdisdot.skeleton.OLD.data.element;

import com.valdisdot.skeleton.OLD.data.DataCell;

import java.util.function.Supplier;

//interface of the view-data bridge
public interface Element<D, C> extends Supplier<C> {
    String getName();

    DataCell<D> getDataCell();
}
