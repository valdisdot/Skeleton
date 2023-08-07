package com.valdisdot.util.ui.gui.util;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//custom implementation of JPanel, places component one by one, linear
//after each add-calling use internal class DimensionCounter for counting preferred size
//use custom LayoutManager by default
public class ContentPanel extends JPanel {
    //encapsulation of preferred size counting logic
    private final DimensionCounter counter;

    public ContentPanel() {
        this(LayoutManager.Order.VERTICAL, LayoutManager.DEFAULT_INDENT_X, LayoutManager.DEFAULT_INDENT_Y, false);
    }

    //main constructor, if isInternalPanel, will count preferred size without first indents
    public ContentPanel(LayoutManager.Order order, int indentX, int indentY, boolean isInternalPanel) {
        super();
        counter = new DimensionCounter(order, indentX, indentY, isInternalPanel);
        setLayout(new LayoutManager(order, indentX, indentY, isInternalPanel));
    }

    //add component and recount preferred size
    @Override
    public Component add(Component comp) {
        counter.add(comp.getPreferredSize());
        setPreferredSize(counter.getDimension());
        return super.add(comp);
    }

    @Override
    public void remove(Component comp) {
        super.remove(comp);
        counter.remove(comp.getPreferredSize());
        setPreferredSize(counter.getDimension());
    }

    //class with encapsulation of preferred size counting logic
    private class DimensionCounter {
        private final LayoutManager.Order order;
        private final int indentX;
        private final int indentY;
        private final boolean isInternalPanel;
        private int width;
        private int height;

        public DimensionCounter(LayoutManager.Order order, int indentX, int indentY, boolean isInternalPanel) {
            this.order = order;
            this.indentX = indentX;
            this.indentY = indentY;
            this.isInternalPanel = isInternalPanel;
            //if !isInternalPanel start count with indents
            width = isInternalPanel ? 0 : indentX;
            height = isInternalPanel ? 0 : indentY;
        }

        public void add(Dimension dimension) {
            if (order == LayoutManager.Order.VERTICAL) {
                //if !isInternalPanel count with indents left and right indent
                width = Math.max(width, dimension.width + (isInternalPanel ? 0 : indentX * 2));
                height += indentY + dimension.height;
            } else {
                width += indentX + dimension.width;
                //if !isInternalPanel count with indents top and bottom indent
                height = Math.max(height, dimension.height + (isInternalPanel ? 0 : indentY * 2));
            }
        }

        public void remove(Dimension dimension) {
            List<Dimension> parentDimensions = Arrays.stream(ContentPanel.this.getComponents()).map(Component::getPreferredSize).collect(Collectors.toList());
            if (order == LayoutManager.Order.VERTICAL) {
                width = parentDimensions.stream().mapToInt(dim -> dim.width + (isInternalPanel ? 0 : indentX * 2)).max().orElse(0);
                height -= indentY + dimension.height;
            } else {
                width -= indentX + dimension.width;
                height = parentDimensions.stream().mapToInt(dim -> dim.height + (isInternalPanel ? 0 : indentY * 2)).max().orElse(0);
            }
        }

        public Dimension getDimension() {
            return new Dimension(width - (isInternalPanel ? indentX : 0), height - (isInternalPanel ? indentY : 0));
        }
    }
}
