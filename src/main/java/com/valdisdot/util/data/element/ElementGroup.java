package com.valdisdot.util.data.element;

import com.valdisdot.util.data.DataCell;

import java.util.Collection;
import java.util.stream.Collectors;


//implementation with boilerplate for Element. Code example: in com.valdisdot.util.gui.DataCellCodeExample
public class ElementGroup<D> extends Naming<D> {
    public ElementGroup(Collection<Element<D, ?>> elements) {
        super(elements.stream().collect(Collectors.toMap(Element::getName, Element::getDataCell)));
    }

    public void addDataCell(Element<D, ?> element) {
        put(element.getName(), element.getDataCell());
    }

    @Override
    public void put(String dataCellName, DataCell<D> dataCell) {
        throw new UnsupportedOperationException("Adding a data cell is not supported in ElementGroup");
    }
}
