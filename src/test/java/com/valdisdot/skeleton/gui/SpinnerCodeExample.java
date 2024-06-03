package com.valdisdot.skeleton.gui;

import com.valdisdot.skeleton.data.controller.BulkResetDataController;
import com.valdisdot.skeleton.data.controller.RawDataController;
import com.valdisdot.skeleton.data.element.Element;
import com.valdisdot.skeleton.data.element.ElementGroup;
import com.valdisdot.skeleton.FrameFactory;
import com.valdisdot.skeleton.ui.gui.component.ControlButton;
import com.valdisdot.skeleton.ui.gui.element.Spinner;

import javax.swing.*;
import java.util.List;

public class SpinnerCodeExample {
    public static void main(String[] args) {
        experiment1();
        experiment2();
    }

    static void experiment1() {
        Element<String, JComponent> element = new Spinner("test", Spinner.asNumberRange(0, 200, 10));
        ElementGroup<String> elementGroup = new ElementGroup<>(List.of(element));
        ControlButton clean = new ControlButton("clean", "clean", new BulkResetDataController<>(elementGroup, ""));
        ControlButton print = new ControlButton(
                "print",
                "print",
                new RawDataController<>(
                        elementGroup,
                        (map) -> map.forEach((key, value) -> System.out.println(key + ": " + value))
                )
        );
        FrameFactory.playOnDesk(element.get());
    }

    static void experiment2() {
        Element<String, JComponent> element = new Spinner("test", Spinner.asList(List.of("value1", "value2", "value3")));
        ElementGroup<String> elementGroup = new ElementGroup<>(List.of(element));
        ControlButton clean = new ControlButton("clean","clean", new BulkResetDataController<>(elementGroup, ""));
        ControlButton print = new ControlButton(
                "print",
                "print",
                new RawDataController<>(
                        elementGroup,
                        (map) -> map.forEach((key, value) -> System.out.println(key + ": " + value))
                )
        );
        FrameFactory.playOnDesk(
                List.of(element.get(), clean.get(), print.get()),
                0x4267B2
        );
    }
}
