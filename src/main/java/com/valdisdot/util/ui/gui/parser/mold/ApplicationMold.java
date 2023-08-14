package com.valdisdot.util.ui.gui.parser.mold;

import java.util.LinkedList;
import java.util.List;

public class ApplicationMold {
    private String applicationName;
    private String rootItemMenuName;
    private int menuBackground;
    private FramesGrouping framesGrouping;
    private final List<FrameMold> frameMolds = new LinkedList<>();

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getRootItemMenuName() {
        return rootItemMenuName;
    }

    public void setRootItemMenuName(String rootItemMenuName) {
        this.rootItemMenuName = rootItemMenuName;
    }

    public int getMenuBackground() {
        return menuBackground;
    }

    public void setMenuBackground(int menuBackground) {
        this.menuBackground = menuBackground;
    }

    public FramesGrouping getBuildingPolicy() {
        return framesGrouping;
    }

    public void setBuildingPolicy(FramesGrouping framesGrouping) {
        this.framesGrouping = framesGrouping;
    }

    public List<FrameMold> getFrameMolds() {
        return frameMolds;
    }

    public void addFrameMolds(List<FrameMold> frameMolds) {
        this.frameMolds.addAll(frameMolds);
    }

    public void addFrameMold(FrameMold frameMold) {
        frameMolds.add(frameMold);
    }

    @Override
    public String toString() {
        return "ApplicationMold{" +
                "applicationName='" + applicationName + '\'' +
                ", rootItemMenuName='" + rootItemMenuName + '\'' +
                ", menuBackground=" + menuBackground +
                ", framesGrouping=" + framesGrouping +
                ", frameMolds=" + frameMolds +
                '}';
    }

    public enum FramesGrouping {
        MENU("menu"),
        PECULIAR_FRAME("peculiar"), //default
        JOINT_FRAME("joint");

        private final String value;

        FramesGrouping(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
