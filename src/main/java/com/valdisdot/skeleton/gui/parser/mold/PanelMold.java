package com.valdisdot.skeleton.gui.parser.mold;

import java.util.*;

/**
 * Represents a panel mold that can contain a collection of {@link ElementMold} elements and
 * has a defined {@link Scope} for managing its lifecycle.
 * <p>
 * A panel mold serves as a container for other element molds, and the elements can be
 * added, removed, or ordered within the panel.
 * </p>
 * <p>
 * The {@code PanelMold} has an {@code id} that must be provided during instantiation and
 * an optional {@link Scope} which defines its instantiation type (singleton or prototype).
 * </p>
 *
 * @since 1.0
 * @see ElementMold
 * @see Scope
 * @author Vladyslav Tymchenko
 */
public class PanelMold extends Mold {
    private LinkedList<ElementMold> elements;
    private Scope scope;

    /**
     * Instantiates a new Panel mold. The {@code id} must not be {@code null}.
     *
     * @param id    the unique identifier for this panel mold
     * @param scope the scope defining the lifecycle of the panel mold (optional, defaults to {@link Scope#SINGLETON})
     */
    public PanelMold(String id, Scope scope) {
        super(Objects.requireNonNull(id, "ID for PanelMold is null"));
        this.elements = new LinkedList<>();
        this.scope = Objects.requireNonNullElse(scope, Scope.SINGLETON);
    }

    /**
     * Instantiates a new Panel mold with the default {@link Scope#SINGLETON}.
     * The {@code id} must not be {@code null}.
     *
     * @param id the unique identifier for this panel mold
     */
    public PanelMold(String id) {
        this(id, null);
    }

    /**
     * Adds an {@link ElementMold} to this panel mold.
     *
     * @param elementMold the element mold to add
     */
    public void addElement(ElementMold elementMold) {
        elements.add(elementMold);
    }

    /**
     * Adds a collection of {@link ElementMold}s to this panel mold.
     *
     * @param elementMolds the element molds to add
     */
    public void addElements(Collection<ElementMold> elementMolds) {
        elements.addAll(elementMolds);
    }

    /**
     * Inserts an {@link ElementMold} at the specified index.
     * If the index is out of bounds, the element will be added at the end.
     *
     * @param elementMold the element mold to insert
     * @param atIndex     the index at which to insert the element
     */
    public void insertElement(ElementMold elementMold, int atIndex) {
        try {
            elements.add(atIndex, elementMold);
        } catch (IndexOutOfBoundsException e) {
            elements.add(elementMold);
        }
    }

    /**
     * Inserts an {@link ElementMold} after the element mold with the specified ID.
     *
     * @param elementMold        the element mold to insert
     * @param afterElementMoldId the ID of the element mold after which to insert
     */
    public void insertElement(ElementMold elementMold, String afterElementMoldId) {
        insertElement(elementMold, findIndex(afterElementMoldId, true, 1));
    }

    /**
     * Inserts a collection of {@link ElementMold}s at the specified index.
     * If the index is out of bounds, the elements will be added at the end.
     *
     * @param elementMolds the collection of element molds to insert
     * @param atIndex      the index at which to insert the elements
     */
    public void insertElements(Collection<ElementMold> elementMolds, int atIndex) {
        try {
            elements.addAll(atIndex, elementMolds);
        } catch (IndexOutOfBoundsException e) {
            elements.addAll(elementMolds);
        }
    }

    /**
     * Inserts a collection of {@link ElementMold}s after the element mold with the specified ID.
     *
     * @param elementMolds       the collection of element molds to insert
     * @param afterElementMoldId the ID of the element mold after which to insert the collection
     */
    public void insertElements(Collection<ElementMold> elementMolds, String afterElementMoldId) {
        insertElements(elementMolds, findIndex(afterElementMoldId, true, 1));
    }

    /**
     * Replaces the element at the specified index with a new {@link ElementMold}.
     * If the index is out of bounds, the new element is added.
     *
     * @param newElement the new element mold to replace
     * @param atIndex    the index at which to replace the element
     */
    public void replaceElement(ElementMold newElement, int atIndex) {
        if (atIndex >= 0 && atIndex < elements.size()) elements.set(atIndex, newElement);
        else elements.add(newElement);
    }

    /**
     * Replaces the element with the specified ID with a new {@link ElementMold}.
     *
     * @param newElement          the new element mold to replace
     * @param targetElementMoldId the ID of the element mold to be replaced
     */
    public void replaceElement(ElementMold newElement, String targetElementMoldId) {
        replaceElement(newElement, findIndex(targetElementMoldId, false, 0));
    }

    /**
     * Removes the element at the specified index.
     *
     * @param atIndex the index of the element to remove
     */
    public void removeElement(int atIndex) {
        if (atIndex >= 0 && atIndex < elements.size()) elements.remove(atIndex);
    }

    /**
     * Removes the element with the specified ID.
     *
     * @param targetElementMoldId the ID of the element mold to remove
     */
    public void removeElement(String targetElementMoldId) {
        removeElement(findIndex(targetElementMoldId, false, 0));
    }

    /**
     * Returns a list of all element molds in this panel mold.
     *
     * @return an unmodifiable list of element molds
     */
    public List<ElementMold> getElements() {
        return List.copyOf(elements);
    }

    /**
     * Returns a list of lists of element molds, where each sublist can be treated as a row or column.
     *
     * @return a list of ordered elements
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
     * Returns the {@link Scope} of this panel mold.
     *
     * @return the scope of this panel mold
     * @see Scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Clones this panel mold, creating a new {@code PanelMold} with the same ID, scope, and elements.
     *
     * @return the cloned panel mold
     */
    public PanelMold clone() {
        PanelMold panelMold = new PanelMold(this.id, this.scope);
        panelMold.elements.addAll(this.elements);
        return panelMold;
    }

    /**
     * Finds the index of an element mold by its ID.
     *
     * @param elementMoldId  the ID of the element mold
     * @param excludeLast    whether to exclude the last element from the search
     * @param shift          an offset applied to the found index
     * @return the index of the element mold, or -1 if not found
     */
    private int findIndex(String elementMoldId, boolean excludeLast, int shift) {
        Iterator<ElementMold> iterator = elements.iterator();
        for (int i = 0; i < (elements.size() - (excludeLast ? 1 : 0)); ++i) {
            if (elementMoldId.equals(iterator.next().getId())) {
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
     * The {@code Scope} enum defines the instantiation type of this {@code PanelMold}.
     * <p>
     * Can either be {@code PROTOTYPE}, indicating a new instance is created each time, or
     * {@code SINGLETON}, indicating the same instance is reused.
     * </p>
     */
    public enum Scope {
        PROTOTYPE,
        SINGLETON
    }
}
