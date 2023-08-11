package com.valdisdot.util.ui.gui.parser;

import com.valdisdot.util.data.element.DataCellGroup;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

//contact for parsers
public interface ParsedView<P extends JPanel> extends Supplier<P> {
    DataCellGroup<String> getDataCellGroups();

    Map<String, Consumer<ActionListener>> getButtonsActionListenerConsumers();
}
