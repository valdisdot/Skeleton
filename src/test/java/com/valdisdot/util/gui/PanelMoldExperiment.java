package com.valdisdot.util.gui;

import com.valdisdot.util.ui.gui.mold.ElementMold;
import com.valdisdot.util.ui.gui.mold.PanelMold;

import java.util.List;

public class PanelMoldExperiment {
    public static void main(String[] args) {
        experiment1();
    }

    static void experiment1() {
        PanelMold mold = new PanelMold()
                .add(new ElementMold("label", "Some label 1", "label1", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 2", "label2", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 3", "label3", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .addToRow(new ElementMold("label", "Some label 4", "label4", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .add(new ElementMold("label", "Some label 5", "label5", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12))
                .add(new ElementMold("label", "Some label 6", "label6", List.of(), 40, 20, 0xFFFFFF, 0x0, "Arial", "plain", 12));
        mold.getMoldConstraintMap().forEach((elem, constr) -> System.out.println("constr: " + (constr.isBlank() ? "blank" : constr) + ", elem: " + elem));
    }
}
