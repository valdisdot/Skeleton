package com.valdisdot.skeleton.OLD.ui.gui.mold;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

//frame on the panel (logical division, not a separate JFrame)
public class FrameMold {
    private final List<PanelMold> panelMolds;
    private String name;
    private String title;
    private int rootBackgroundColor;

    public FrameMold(String name, String title) {
        this.name = name;
        this.title = title;
        panelMolds = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "FrameMold{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", panelMolds=" + panelMolds +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRootBackgroundColor() {
        return rootBackgroundColor;
    }

    public void setRootBackgroundColor(int rootBackgroundColor) {
        this.rootBackgroundColor = rootBackgroundColor;
    }

    public List<PanelMold> getPanelMolds() {
        return panelMolds;
    }

    public void setPanelMolds(List<PanelMold> panelMolds) {
        if (Objects.nonNull(panelMolds)) this.panelMolds.addAll(panelMolds);
    }

    public void addPanelMold(PanelMold panelMold) {
        if (Objects.nonNull(panelMold)) panelMolds.add(panelMold);
    }
}
