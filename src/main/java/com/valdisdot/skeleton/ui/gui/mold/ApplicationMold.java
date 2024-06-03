package com.valdisdot.skeleton.ui.gui.mold;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ApplicationMold {
    private String applicationName;
    private String rootItemMenuName;
    private int controlBackground;
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

    public int getControlBackground() {
        return controlBackground;
    }

    public void setControlBackground(int controlBackground) {
        this.controlBackground = controlBackground;
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
                ", controlBackground=" + controlBackground +
                ", framesGrouping=" + framesGrouping +
                ", frameMolds=" + frameMolds +
                '}';
    }

    public enum FramesGrouping {
        MENU("menu"),
        SIDE_BAR("sideBar"),
        PECULIAR_FRAME("peculiar"),
        JOINT_FRAME("joint"),
        NONE(null); //default

        private final static Map<String, FramesGrouping> map;

        static {
            map = new HashMap<>();
            map.put(MENU.value, MENU);
            map.put(SIDE_BAR.value, SIDE_BAR);
            map.put(JOINT_FRAME.value, JOINT_FRAME);
            map.put(PECULIAR_FRAME.value, PECULIAR_FRAME);
        }

        private final String value;

        FramesGrouping(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static FramesGrouping getFromValue(String value){
            return map.getOrDefault(value, NONE);
        }
    }
}
