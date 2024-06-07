package com.valdisdot.skeleton.OLD.ui.gui.mold;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
    class builds an ordered map with element mold and its constraint for MigLayout (for JPanel.add)
    contains hex color of panel background and the flag where the panel should be placed on a frame
*/
public class PanelMold {
    //map of element raw properties (Element mold) and constraint for MigLayout
    private final LinkedHashMap<ElementMold, String> ordered;
    private List<ElementMold> row;
    private int backgroundColor;
    private boolean fromTheTopOfFrame = true;

    public PanelMold() {
        ordered = new LinkedHashMap<>();
        row = new ArrayList<>();
    }

    public PanelMold(boolean fromTheTopOfFrame) {
        this();
        this.fromTheTopOfFrame = fromTheTopOfFrame;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void addToRow(ElementMold elementMold) {
        row.add(elementMold);
    }

    public void add(ElementMold elementMold) {
        writeRow();
        ordered.put(elementMold, "");
    }

    public void addToNewRow(ElementMold elementMold) {
        writeRow();
    }

    protected void writeRow() {
        if (!row.isEmpty()) {
            ordered.put(row.get(0), "split " + row.size());
            for (int i = 1; i < row.size(); ++i) ordered.put(row.get(i), "");
            row = new ArrayList<>();
        }
    }

    public Map<ElementMold, String> getMoldConstraintMap() {
        writeRow();
        return ordered;
    }

    public boolean isFromTheTopOfFrame() {
        return fromTheTopOfFrame;
    }

    public void setFromTheTopOfFrame(boolean fromTheTopOfFrame) {
        this.fromTheTopOfFrame = fromTheTopOfFrame;
    }

    @Override
    public String toString() {
        return "PanelMold{" +
                "ordered=" + ordered +
                ", row=" + row +
                ", backgroundColor=" + backgroundColor +
                ", fromTheTopOfFrame=" + fromTheTopOfFrame +
                '}';
    }
}
