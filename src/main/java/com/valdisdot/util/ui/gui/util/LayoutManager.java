package com.valdisdot.util.ui.gui.util;

import java.awt.*;

public class LayoutManager implements java.awt.LayoutManager {
    public static final int DEFAULT_INDENT_X = 4;
    public static final int DEFAULT_INDENT_Y = 5;
    private final int indentX;
    private final int indentY;
    private final boolean startWithZeroPosition;
    private final Order order;

    public LayoutManager() {
        indentX = DEFAULT_INDENT_X;
        indentY = DEFAULT_INDENT_Y;
        startWithZeroPosition = false;
        order = Order.VERTICAL;
    }

    public LayoutManager(Order order, int indentX, int indentY, boolean startWithZeroPosition) {
        this.indentX = indentX >= 0 ? indentX : DEFAULT_INDENT_X;
        this.indentY = indentY >= 0 ? indentY : DEFAULT_INDENT_Y;
        this.startWithZeroPosition = startWithZeroPosition;
        this.order = order != null ? order : Order.VERTICAL;

    }

    @Override
    public void layoutContainer(Container parent) {
        //only if parent contains any elements
        if (parent.getComponentCount() != 0) {
            if (order == Order.VERTICAL) {
                int currentY = startWithZeroPosition ? 0 : indentY;
                //counter for free space for placing elements
                int remainingSpace = parent.getPreferredSize().height;
                Component currentComponent;
                //special calculation if placing should start with y = 0
                if (startWithZeroPosition) {
                    currentComponent = parent.getComponent(0);
                    remainingSpace -= currentComponent.getPreferredSize().height;
                    if (remainingSpace >= 0) {
                        //set preferred size of the current component to fit it with width
                        currentComponent.setPreferredSize(
                                new Dimension(
                                        Math.min(parent.getPreferredSize().width - 2 * indentX, currentComponent.getPreferredSize().width),
                                        currentComponent.getPreferredSize().height));
                        currentComponent.setBounds(
                                indentX,
                                currentY,
                                currentComponent.getPreferredSize().width,
                                currentComponent.getPreferredSize().height);
                        currentY += indentY + currentComponent.getPreferredSize().height;
                    } else {
                        //write to logger warning message and quit
                        System.getLogger(this.getClass().getName())
                                .log(
                                        System.Logger.Level.WARNING,
                                        String.format(
                                                "Component at %d is not added, reason: no free space. Component: %s, Parent: %s",
                                                0,
                                                currentComponent.getClass(), parent.getClass()));
                        return;
                    }
                }
                //code repeating -> try to figure out how to collapse it
                for (int i = startWithZeroPosition ? 1 : 0; i < parent.getComponentCount() && remainingSpace > 0; ++i) {
                    currentComponent = parent.getComponent(i);
                    remainingSpace = remainingSpace - currentComponent.getPreferredSize().height - indentY;
                    if (remainingSpace >= 0) {
                        //set preferred size of the current component to fit it with width
                        currentComponent.setPreferredSize(
                                new Dimension(
                                        Math.min(parent.getPreferredSize().width - 2 * indentX, currentComponent.getPreferredSize().width),
                                        currentComponent.getPreferredSize().height));
                        currentComponent.setBounds(indentX, currentY, currentComponent.getPreferredSize().width, currentComponent.getPreferredSize().height);
                        currentY += indentY + currentComponent.getPreferredSize().height;
                    } else {
                        //write to logger warning message and quit
                        System.getLogger(this.getClass().getName())
                                .log(
                                        System.Logger.Level.WARNING,
                                        String.format(
                                                "Component at %d is not added, reason: no free space. Component: %s, Parent: %s",
                                                i,
                                                currentComponent.getClass(), parent.getClass()));
                        return;
                    }
                }
            } else if (order == Order.HORIZONTAL) {
                int currentX = startWithZeroPosition ? 0 : indentX;
                //counter for free space for placing elements
                int remainingSpace = parent.getPreferredSize().width;
                Component currentComponent;
                //special calculation if placing should start with x = 0
                if (startWithZeroPosition) {
                    currentComponent = parent.getComponent(0);
                    remainingSpace -= currentComponent.getPreferredSize().width;
                    if (remainingSpace >= 0) {
                        //set preferred size of the current component to fit it with height
                        currentComponent.setPreferredSize(
                                new Dimension(
                                        currentComponent.getPreferredSize().width,
                                        Math.min(currentComponent.getPreferredSize().height, parent.getPreferredSize().height - 2 * indentY)));
                        currentComponent.setBounds(currentX, indentY, currentComponent.getPreferredSize().width, currentComponent.getPreferredSize().height);
                        currentX += indentX + currentComponent.getPreferredSize().width;
                    } else {
                        //write to logger warning message and quit
                        System.getLogger(this.getClass().getName())
                                .log(
                                        System.Logger.Level.WARNING,
                                        String.format(
                                                "Component at %d is not added, reason: no free space. Component: %s, Parent: %s",
                                                0,
                                                currentComponent.getClass(), parent.getClass()));
                        return;
                    }
                }
                for (int i = startWithZeroPosition ? 1 : 0; i < parent.getComponentCount() && remainingSpace > 0; ++i) {
                    currentComponent = parent.getComponent(i);
                    remainingSpace = remainingSpace - currentComponent.getPreferredSize().width - indentX;
                    if (remainingSpace >= 0) {
                        //set preferred size of the current component to fit it with height
                        currentComponent.setPreferredSize(
                                new Dimension(
                                        currentComponent.getPreferredSize().width,
                                        Math.min(currentComponent.getPreferredSize().height, parent.getPreferredSize().height - 2 * indentY)));
                        currentComponent.setBounds(currentX, indentY, currentComponent.getPreferredSize().width, currentComponent.getPreferredSize().height);
                        currentX += indentX + currentComponent.getPreferredSize().width;
                    } else {
                        //write to logger warning message and quit
                        System.getLogger(this.getClass().getName())
                                .log(
                                        System.Logger.Level.WARNING,
                                        String.format(
                                                "Component at %d is not added, reason: no free space. Component: %s, Parent: %s",
                                                i,
                                                currentComponent.getClass(), parent.getClass()));
                        return;
                    }
                }
            }
        } else {
            //write warning about lack of elements
            System.getLogger(this.getClass().getName())
                    .log(
                            System.Logger.Level.WARNING,
                            String.format(
                                    "No Components in %s",
                                    parent.getClass()));
        }
    }

    //adapters
    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    public enum Order {VERTICAL, HORIZONTAL}
}
