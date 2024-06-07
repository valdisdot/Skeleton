package com.valdisdot.skeleton.OLD.ui.gui.component;

import java.util.HashMap;
import java.util.Map;

public enum ComponentType {
    CHECK_BOX("checkBox"),
    CONTENT_BUTTON("contentButton"),
    CONTROL_BUTTON("button"),
    LABEL("label"),
    LIST("list"),
    MULTI_LIST("multiList"),
    MODIFIABLE_LABEL("modifiableLabel"),
    RADIO_BUTTONS("radioButtons"),
    SLIDER("slider"),
    SPINNER("spinner"),
    TEXT_AREA("textArea"),
    TEXT_FIELD("textField"),
    NULL(null);

    private static final Map<String, ComponentType> map;

    static {
        map = new HashMap<>();
        map.put(CHECK_BOX.value, CHECK_BOX);
        map.put(CONTENT_BUTTON.value, CONTENT_BUTTON);
        map.put(CONTROL_BUTTON.value, CONTROL_BUTTON);
        map.put(LABEL.value, LABEL);
        map.put(LIST.value, LIST);
        map.put(MULTI_LIST.value, MULTI_LIST);
        map.put(MODIFIABLE_LABEL.value, MODIFIABLE_LABEL);
        map.put(RADIO_BUTTONS.value, RADIO_BUTTONS);
        map.put(SLIDER.value, SLIDER);
        map.put(SPINNER.value, SPINNER);
        map.put(TEXT_AREA.value, TEXT_AREA);
        map.put(TEXT_FIELD.value, TEXT_FIELD);
    }

    private final String value;

    ComponentType(String value) {
        this.value = value;
    }

    public static ComponentType fromValue(String enumValue) {
        return map.getOrDefault(enumValue, NULL);
    }

    public String getValue() {
        return value;
    }
}
