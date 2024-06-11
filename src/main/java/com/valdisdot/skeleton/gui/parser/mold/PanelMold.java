package com.valdisdot.skeleton.gui.parser.mold;

import java.util.*;

/**
 * {@inheritDoc}
 */
public class PanelMold extends Mold {
    private List<ElementMold> elements;
    private Scope scope;

    /**
     * Instantiates a new Panel mold. ID must not be null.
     *
     * @param id    the id
     * @param scope the scope
     */
    public PanelMold(String id, Scope scope) {
        super(Objects.requireNonNull(id, "ID for PanelMold is null"));
        this.elements = new LinkedList<>();
        this.scope = Objects.requireNonNullElse(scope, Scope.SINGLETON);
    }

    /**
     * Instantiates a new Panel mold. ID must not be null.
     *
     * @param id the id
     */
    public PanelMold(String id) {
        this(id, null);
    }

    /**
     * Adds an element mold to this panel mold.
     *
     * @param elementMold the element mold
     */
    public void addElement(ElementMold elementMold) {
        elements.add(elementMold);
    }

    /**
     * Adds a collection of element molds to this panel mold.
     *
     * @param elementMolds the element molds
     */
    public void addElements(Collection<ElementMold> elementMolds) {
        elements.addAll(elementMolds);
    }

    /**
     * @return a collection of element molds from this panel mold.
     */
    public List<ElementMold> getElements() {
        return List.copyOf(elements);
    }

    /**
     * Returns a list of lists of element molds.
     * Each list element can be treated as a row/column.
     *
     * @return the elements ordered
     */
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

    /**
     * @return the scope of this panel mold
     * @see Scope
     */
    public Scope getScope() {
        return scope;
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

    /**
     * The enum Scope. Indicates an instantiation type of this PanelMold.
     * Can be either prototype or singleton (aka Spring DI)
     */
    public enum Scope {
        PROTOTYPE,
        SINGLETON
    }
}
