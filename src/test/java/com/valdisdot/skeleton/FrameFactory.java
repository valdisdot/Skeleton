package com.valdisdot.skeleton;

import com.valdisdot.skeleton.OLD.data.controller.BulkResetDataController;
import com.valdisdot.skeleton.OLD.data.controller.RawDataController;
import com.valdisdot.skeleton.OLD.data.element.DataCellGroup;
import com.valdisdot.skeleton.OLD.ui.gui.component.ControlButton;
import com.valdisdot.skeleton.OLD.ui.gui.element.JElement;
import com.valdisdot.skeleton.OLD.ui.gui.tool.Colors;
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
                component.setBackground(Colors.reverse(new Color(startWithRGBHex)));
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
        Runnable reset = new BulkResetDataController<>(dataCellGroup, resetValue);
        Runnable read = new RawDataController<>(dataCellGroup, dataConsumer);
        JPanel panel = new JPanel(new MigLayout("wrap"));
        panel.add(element.get());
        panel.add(new ControlButton("reset", "reset", reset).get(), "split 2");
        panel.add(new ControlButton("read", "read", read).get());
        playOnDesk(panel, ((int) (Integer.MAX_VALUE * Math.random())));
    }

    public static void playOnDesk(JComponent component){
        new Thread(() -> {
            JFrame frame = new JFrame("DESK | com.valdisdot.util");
            frame.add(component);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        }).start();
    }
}
