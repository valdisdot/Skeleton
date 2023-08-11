package com.valdisdot.util.tool;

import com.valdisdot.util.data.controller.BulkResetDataController;
import com.valdisdot.util.data.controller.DataController;
import com.valdisdot.util.data.controller.RawDataController;
import com.valdisdot.util.data.element.DataCellGroup;
import com.valdisdot.util.ui.gui.component.ControlButton;
import com.valdisdot.util.ui.gui.element.JElement;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class FrameFactory {
    public static void playOnDesk(Collection<JComponent> components, int startWithRGBHex) {
        new Thread(getOnTheDeskRunnable(components, startWithRGBHex)).start();
    }

    public static void playOnDesk(JComponent component, int startWithRGBHex) {
        playOnDesk(Collections.singletonList(component), startWithRGBHex);
    }

    public static Runnable getOnTheDeskRunnable(Collection<JComponent> components, int startWithRGBHex) {
        return () -> {
            JFrame frame = new JFrame("DESK | com.valdisdot.util");
            JRootPane rootPane = frame.getRootPane();
            rootPane.setLayout(new MigLayout("wrap"));
            rootPane.setBackground(new Color(startWithRGBHex));
            components.forEach(component -> {
                component.setBackground(new Color(Colors.reverse(startWithRGBHex)));
                rootPane.add(component);
            });
            frame.repaint();
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        };
    }

    public static <T> void playOnDesk(JElement<T> element, T resetValue, Consumer<Map<String, T>> dataConsumer) {
        DataCellGroup<T> dataCellGroup = new DataCellGroup<>(
                Map.of(element.getName(), element.getDataCell())
        );
        DataController reset = new BulkResetDataController<>(dataCellGroup, resetValue);
        DataController read = new RawDataController<>(dataCellGroup, dataConsumer);
        JPanel panel = new JPanel(new MigLayout("wrap"));
        panel.add(element.get());
        panel.add(new ControlButton("reset", "reset", reset).get(), "split 2");
        panel.add(new ControlButton("read", "read", read).get());
        playOnDesk(panel, ((int) (Integer.MAX_VALUE * Math.random())));
    }

    public static void playOnDesk(JComponent component){
        playOnDesk(component, ((int) (Integer.MAX_VALUE * Math.random())));
    }
}
