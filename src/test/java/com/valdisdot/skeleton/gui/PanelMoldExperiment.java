package com.valdisdot.skeleton.gui;

import com.valdisdot.skeleton.ui.gui.mold.ApplicationMold;
import com.valdisdot.skeleton.ui.gui.mold.ElementMold;
import com.valdisdot.skeleton.ui.gui.mold.FrameMold;
import com.valdisdot.skeleton.ui.gui.mold.PanelMold;

import java.util.List;

public class PanelMoldExperiment {
    public static void main(String[] args) {
        experiment1();
    }

    static void experiment1() {
        FrameMold frameMold = new FrameMold("frame_id", "My frame!");
        PanelMold panelMold = new PanelMold();
        panelMold.add(new ElementMold("label", "Some label 1", "label1", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        panelMold.addToRow(new ElementMold("label", "Some label 2", "label2", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        panelMold.addToRow(new ElementMold("label", "Some label 3", "label3", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        panelMold.addToRow(new ElementMold("label", "Some label 4", "label4", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        panelMold.add(new ElementMold("label", "Some label 5", "label5", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        panelMold.add(new ElementMold("label", "Some label 6", "label6", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        ApplicationMold applicationMold = new ApplicationMold();
        applicationMold.setApplicationName("My application");
        applicationMold.setBuildingPolicy(ApplicationMold.FramesGrouping.PECULIAR_FRAME);
        applicationMold.addFrameMold(frameMold);
    }
}
