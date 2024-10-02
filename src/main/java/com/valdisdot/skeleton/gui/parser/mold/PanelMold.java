package com.valdisdot.skeleton.gui.parser.mold;

import java.util.*;

/**
 * {@inheritDoc}
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class PanelMold extends Mold {
    private LinkedList<ElementMold> elements;
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

    public void insertElement(ElementMold elementMold, int atIndex) {
        try{
            elements.add(atIndex, elementMold);
        } catch (IndexOutOfBoundsException e) {
            elements.add(elementMold);
        }
    }

    public void insertElement(ElementMold elementMold, String afterElementMoldId) {
        insertElement(elementMold, findIndex(afterElementMoldId, true, 1));
    }

    public void insertElements(Collection<ElementMold> elementMolds, int atIndex) {
        try {
            elements.addAll(atIndex, elementMolds);
        }  catch (IndexOutOfBoundsException e) {
            elements.addAll(elementMolds);
        }
    }

    public void insertElements(Collection<ElementMold> elementMolds, String afterElementMoldId) {
        insertElements(elementMolds, findIndex(afterElementMoldId, true, 1));
    }

    public void replaceElement(ElementMold newElement, int atIndex) {
        if(atIndex >= 0 && atIndex < elements.size()) elements.set(atIndex, newElement);
        else elements.add(newElement);
    }

    public void replaceElement(ElementMold newElement, String targetElementMoldId) {
        replaceElement(newElement, findIndex(targetElementMoldId, false, 0));
    }

    public void removeElement(int atIndex) {
        if(atIndex >= 0 && atIndex < elements.size()) elements.remove(atIndex);
    }

    public void removeElement(String targetElementMoldId) {
        removeElement(findIndex(targetElementMoldId, false, 0));
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

    private int findIndex(String elementMoldId, boolean excludeLast, int shift) {
        Iterator<ElementMold> iterator = elements.iterator();
        for(int i = 0; i < (elements.size() - (excludeLast ? 1 : 0)); ++i){
            if(elementMoldId.equals(iterator.next().getId())) {
                return i + shift;
            }
        }
        return -1;
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
