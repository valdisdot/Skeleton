package com.valdisdot.skeleton.gui.parser.mold;

import java.util.*;

public class PanelMold extends Mold {
    protected List<ElementMold> elements;
    private Scope scope;

    public PanelMold(String id, Scope scope) {
        super(id);
        this.elements = new LinkedList<>();
        this.scope = Objects.requireNonNullElse(scope, Scope.SINGLETON);
    }

    public PanelMold(String id) {
        this(id, null);
    }

    protected PanelMold() {
        super();
    }

    public void addElement(ElementMold elementMold) {
        elements.add(elementMold);
    }

    public void addElements(Collection<ElementMold> elementMolds) {
        elements.addAll(elementMolds);
    }

    public List<ElementMold> getElements() {
        return List.copyOf(elements);
    }

    public List<List<ElementMold>> getElementsOrdered() {
        List<List<ElementMold>> res = new LinkedList<>();
        if (!elements.isEmpty()) {
            ElementMold current;
            List<ElementMold> lastRow;

            current = elements.get(0);
            lastRow = new ArrayList<>();
            lastRow.add(current);
            res.add(lastRow);

            for (int i = 1; i < elements.size(); ++i) {
                current = elements.get(i);
                if (!current.doesFollow()) {
                    lastRow = new ArrayList<>();
                    res.add(lastRow);
                }
                lastRow.add(current);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "PanelMold{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", args=" + properties +
                ", styles=" + styles +
                ", elements=" + elements +
                '}';
    }

    public Scope getScope() {
        return scope;
    }

    public enum Scope {
        PROTOTYPE, SINGLETON
    }
}
