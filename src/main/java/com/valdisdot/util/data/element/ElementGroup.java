package com.valdisdot.util.data.element;

import com.valdisdot.util.data.DataCell;

import java.util.Collection;
import java.util.stream.Collectors;


//implementation with boilerplate for Element. Code example: in com.valdisdot.util.gui.DataCellCodeExample
public class ElementGroup<D> extends Naming<D> {
    public ElementGroup(Collection<? extends Element<D, ?>> elements) {
        super(elements.stream().collect(Collectors.toMap(Element::getName, Element::getDataCell)));
    }

    public void addElement(Element<D, ?> element) {
        put(element.getName(), element.getDataCell());
    }


}
