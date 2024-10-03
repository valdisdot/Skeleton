package com.valdisdot.skeleton.gui.parser.mold;

import java.util.*;

/**{@inheritDoc}
 * @since 1.0
 * @author Vladyslav Tymchenko
 * */
public class ElementMold extends Mold {
    private String type;
    private boolean doesFollow = false;
    private boolean isControl = false;
    private Map<String, String> values;

    /**
     * Instantiates a new Element mold. ID must not be null.
     *
     * @param id     the id
     * @param type   the type
     * @param values the values
     */
    public ElementMold(String id, String type, Map<String, String> values) {
        super(Objects.requireNonNull(id, "ID for ElementMold is null"));
        this.type = Objects.requireNonNullElse(type, "");
        this.values = new LinkedHashMap<>(Objects.requireNonNullElse(values, Map.of()));
    }

    /**
     * Instantiates a new Element mold. ID must not be null.
     *
     * @param id   the id
     * @param type the type
     */
    public ElementMold(String id, String type){
        this(id, type, null);
    }

    /**
     * Instantiates a new Element mold.
     *
     * @param id the id
     */
    public ElementMold(String id){
        this(id, null, null);
    }

    /**
     * Return a text representation of type of this element mold.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets a text representation of type of this element mold.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = Objects.requireNonNullElse(type, this.type);
    }

    /**
     * @return the flag indicates the requirement of leading previous element after the previous.
     */
    public boolean doesFollow() {
        return doesFollow;
    }

    /**
     * Sets the flag about requirement of leading previous element after the previous.
     *
     * @param value the value
     */
    public void doFollow(boolean value) {
        this.doesFollow = value;
    }

    /**
     * @return the flag indicates the requirement to treat the current element as a control element.
     */
    public boolean isControl() {
        return isControl;
    }

    /**
     * Sets the flag indicates the requirement to treat the current element as a control element.
     *
     * @param isControl the value
     */
    public void setControl(boolean isControl) {
        this.isControl = isControl;
    }

    /**
     * Returns the values of this element.
     * @return the values
     */
    public Map<String, String> getValues() {
        return values;
    }

    /**
     * Sets the values of this element.
     * @param values the values
     */
    public void setValues(Map<String, String> values) {
        if (Objects.nonNull(values)) this.values.putAll(values);
    }

    /**
     * Clones this element mold, creating a new {@code ElementMold} with a specified ID, but different properties.
     *
     * @return the cloned element mold
     */
    public ElementMold clone(String id) {
        ElementMold elementMold = new ElementMold(id, this.type, this.values);
        elementMold.doesFollow = this.doesFollow;
        elementMold.isControl = this.isControl;
        elementMold.title = this.title;
        elementMold.properties = new LinkedHashMap<>(this.properties);
        elementMold.styles = new ArrayList<>(styles);
        return elementMold;
    }

    /**
     * Clones this element mold, creating a new {@code ElementMold} with the same ID and properties..
     *
     * @return the cloned element mold
     */
    public ElementMold clone(){
        return clone(this.id);
    }

    @Override
    public String toString() {
        return "ElementMold{" +
                "type=" + type +
                ", doesFollow=" + doesFollow +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", args=" + properties +
                ", styles=" + styles +
                '}';
    }
}
