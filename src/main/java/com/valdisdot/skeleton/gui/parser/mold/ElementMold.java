package com.valdisdot.skeleton.gui.parser.mold;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ElementMold extends Mold {
    protected String type;
    protected boolean doesFollow = false;
    protected boolean isControl = false;
    protected Map<String, String> values;

    public ElementMold(String id, String type, Map<String, String> values) {
        super(id);
        this.type = Objects.requireNonNullElse(type, "");
        this.values = new LinkedHashMap<>(Objects.requireNonNullElse(values, Map.of()));
    }

    //open for inheritance
    protected ElementMold() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = Objects.requireNonNullElse(type, this.type);
    }

    public boolean doesFollow() {
        return doesFollow;
    }

    public void doFollow(boolean value) {
        this.doesFollow = value;
    }

    public boolean isControl() {
        return isControl;
    }

    public void setControl(boolean isControl) {
        this.isControl = isControl;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        if (Objects.nonNull(values)) this.values.putAll(values);
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
