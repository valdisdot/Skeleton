package com.valdisdot.skeleton.gui.view.swing.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A class represents a chaining interface to make a customizable JFrame with various configuration options.
 * It implements Runnable and Supplier of the JFrame interfaces.
 */
public class FrameBuilder implements Runnable, Supplier<JFrame> {
    private final JFrame targetFrame;
    private JComponent lastComponent = null;

    /**
     * Constructs a Frame with a new JFrame.
     */
    public FrameBuilder() {
        targetFrame = new JFrame();
    }

    /**
     * Creates a menu slot with the given label.
     *
     * @param label the label for the menu slot.
     * @return a new MenuSlot instance.
     * @throws BuildingException if the label is null.
     */
    public MenuSlot createMenuSlot(String label) {
        if (Objects.isNull(label)) throw new BuildingException("Label for a menu slot is null");
        if (Objects.isNull(targetFrame.getJMenuBar())) targetFrame.setJMenuBar(new JMenuBar());
        return new MenuSlot(label, this);
    }

    /**
     * Sets the title of the frame.
     *
     * @param title the title of the frame.
     * @return the current Frame instance for method chaining.
     * @throws BuildingException If the title is null.
     */
    public FrameBuilder title(String title) {
        if (Objects.isNull(title)) throw new BuildingException("Frame title is null");
        targetFrame.setTitle(title);
        return this;
    }

    /**
     * Sets the icon of the frame.
     *
     * @param image the image to be used as the frame icon.
     * @return the current Frame instance for method chaining.
     * @throws BuildingException if the image is null.
     */
    public FrameBuilder icon(Image image) {
        if (Objects.isNull(image)) throw new BuildingException("Image for frame icon is null");
        targetFrame.setIconImage(image);
        return this;
    }

    /**
     * Sets the initial component to be displayed in the frame.
     *
     * @param component the initial component.
     * @return the current Frame instance for method chaining.
     * @throws BuildingException if the component is null.
     */
    public FrameBuilder setInitialComponent(JComponent component) {
        if (Objects.isNull(component)) throw new BuildingException("Initial component is null");
        updateView(component);
        return this;
    }

    /**
     * Configures the frame to execute a specified action upon exit.
     *
     * @param exitType the type of exit behavior.
     * @param action   the action to execute upon exit.
     * @return the current Frame instance for method chaining.
     * @throws BuildingException if either parameter is null.
     */
    public FrameBuilder onExit(ExitType exitType, Runnable action) {
        if (Objects.isNull(exitType)) throw new BuildingException("Frame exit type is null");
        if (Objects.isNull(action)) throw new BuildingException("On exit action runnable is null");

        targetFrame.setDefaultCloseOperation(
                exitType == ExitType.APPLICATION_EXIT ? WindowConstants.EXIT_ON_CLOSE :
                        exitType == ExitType.FRAME_HIDE ? WindowConstants.HIDE_ON_CLOSE :
                                exitType == ExitType.FRAME_DISPOSE ? WindowConstants.DISPOSE_ON_CLOSE :
                                        WindowConstants.DO_NOTHING_ON_CLOSE
        );

        targetFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                action.run();
                super.windowClosing(e);
            }
        });
        return this;
    }

    /**
     * Sets whether the frame is resizable.
     *
     * @param isResizable true if the frame should be resizable, false otherwise.
     * @return the current Frame instance for method chaining.
     */
    public FrameBuilder resizable(boolean isResizable) {
        targetFrame.setResizable(isResizable);
        return this;
    }

    /**
     * Sets whether the frame should always be on top of other windows.
     *
     * @param isAlwaysOnTop true if the frame should always be on top, false otherwise.
     * @return the current Frame instance for method chaining.
     */
    public FrameBuilder alwaysOnTop(boolean isAlwaysOnTop) {
        targetFrame.setAlwaysOnTop(isAlwaysOnTop);
        return this;
    }

    /**
     * Allows peeking into the JMenuBar of the frame and applying a specified function.
     *
     * @param function the function to apply to the JMenuBar.
     * @return the current Frame instance for method chaining.
     * @throws BuildingException if the function is null.
     */
    public FrameBuilder peekMenuBar(Consumer<JMenuBar> function) {
        if (Objects.isNull(function)) throw new BuildingException("Menu bar peek function is null");
        if (Objects.isNull(targetFrame.getJMenuBar())) targetFrame.setJMenuBar(new JMenuBar());
        function.accept(this.targetFrame.getJMenuBar());
        return this;
    }

    /**
     * Gets the JFrame managed by this class.
     *
     * @return the JFrame instance.
     */
    @Override
    public JFrame get() {
        targetFrame.pack();
        return targetFrame;
    }

    /**
     * Runs the frame, making it visible.
     */
    @Override
    public void run() {
        get().setVisible(true);
    }

    private void updateView(JComponent newView) {
        if (Objects.nonNull(lastComponent)) targetFrame.remove(lastComponent);
        lastComponent = newView;
        targetFrame.add(newView);
        targetFrame.revalidate();
        targetFrame.repaint();
        targetFrame.pack();
    }

    /**
     * Enum representing different exit behaviors for the frame.
     */
    public enum ExitType {APPLICATION_EXIT, FRAME_DISPOSE, FRAME_HIDE, DO_NOTHING}

    /**
     * Class representing a menu slot in the frame.
     */
    public static class MenuSlot {
        private final FrameBuilder frameBuilder;
        private final JMenu targetSlot;
        private MenuSlot parent;

        private MenuSlot(String label, FrameBuilder frameBuilder) {
            this.frameBuilder = frameBuilder;
            this.targetSlot = new JMenu(label);
            this.targetSlot.setOpaque(true);
        }

        private MenuSlot(String label, FrameBuilder frameBuilder, MenuSlot parent) {
            this(label, frameBuilder);
            this.parent = parent;
        }

        /**
         * Creates a menu option with the specified label.
         *
         * @param label the label for the menu option.
         * @return a new MenuOption instance.
         * @throws BuildingException if the label is null.
         */
        public MenuOption createMenuOption(String label) {
            if (Objects.isNull(label)) throw new BuildingException("Label for a menu option is null");
            return new MenuOption(label, this);
        }

        /**
         * Creates a checkbox menu option with the specified label and preselection state.
         *
         * @param label       the label for the checkbox menu option.
         * @param preselected the initial selection state.
         * @return a new SelectableMenuOption instance.
         * @throws BuildingException if the label is null.
         */
        public SelectableMenuOption createCheckBoxMenuOption(String label, boolean preselected) {
            if (Objects.isNull(label)) throw new BuildingException("Label for a check box menu option is null");
            return new SelectableMenuOption(label, this, preselected, false);
        }

        /**
         * Creates a radio box menu option with the specified label and preselection state.
         *
         * @param label       the label for the radio box menu option.
         * @param preselected the initial selection state.
         * @return a new SelectableMenuOption instance.
         * @throws BuildingException if the label is null.
         */
        public SelectableMenuOption createRadioBoxMenuOption(String label, boolean preselected) {
            if (Objects.isNull(label)) throw new BuildingException("Label for a radio box menu option is null");
            return new SelectableMenuOption(label, this, preselected, true);
        }

        /**
         * Creates a sub-slot with the specified label.
         *
         * @param label the label for the sub-slot.
         * @return a new MenuSlot instance.
         * @throws BuildingException if the label is null.
         */
        public MenuSlot createSubSlot(String label) {
            if (Objects.isNull(label)) throw new BuildingException("Label for a sub slot is null");
            return new MenuSlot(label, frameBuilder, this);
        }

        /**
         * Adds an action to be executed when the menu slot is selected.
         *
         * @param action the action to execute.
         * @return the current MenuSlot instance for method chaining.
         * @throws BuildingException if the action is null.
         */
        public MenuSlot addAction(Runnable action) {
            if (Objects.isNull(action)) throw new BuildingException("Action for the menu slot is null");
            targetSlot.addActionListener(l -> action.run());
            return this;
        }

        /**
         * Adds a component action to be executed when the menu slot is selected.
         *
         * @param action the component action to execute.
         * @return the current MenuSlot instance for method chaining.
         * @throws BuildingException if the action is null.
         */
        public MenuSlot addSlotComponentAction(Consumer<JMenu> action) {
            if (Objects.isNull(action)) throw new BuildingException("Component action for the menu slot is null");
            targetSlot.addActionListener(l -> action.accept(targetSlot));
            return this;
        }

        /**
         * Allows peeking into the JMenu of the slot and applying a specified function.
         *
         * @param function the function to apply to the JMenu.
         * @return the current MenuSlot instance for method chaining.
         * @throws BuildingException if the function is null.
         */
        public MenuSlot peek(Consumer<JMenu> function) {
            if (Objects.isNull(function)) throw new BuildingException("Peek function for the menu slot is null");
            function.accept(targetSlot);
            return this;
        }

        /**
         * Sets whether the menu slot is transparent.
         *
         * @param isTransparent true if the menu slot should be transparent, false otherwise.
         * @return the current MenuSlot instance for method chaining.
         */
        public MenuSlot transparent(boolean isTransparent) {
            targetSlot.setOpaque(!isTransparent);
            return this;
        }

        /**
         * Adds a separator to the menu slot.
         *
         * @return the current MenuSlot instance for method chaining.
         */
        public MenuSlot separator() {
            targetSlot.addSeparator();
            return this;
        }

        /**
         * Returns to the parent menu slot.
         *
         * @return the parent MenuSlot instance.
         * @throws BuildingException if the parent slot is null.
         */
        public MenuSlot backToParentSlot() {
            if (Objects.isNull(parent)) throw new BuildingException("Parent slot is null");
            parent.targetSlot.add(this.targetSlot);
            return parent;
        }

        /**
         * Returns to the frame and adds this menu slot to the frame's JMenuBar.
         *
         * @return the Frame instance.
         */
        public FrameBuilder backToFrame() {
            frameBuilder.targetFrame.getJMenuBar().add(targetSlot);
            return frameBuilder;
        }
    }

    /**
     * Class representing a menu option in the frame.
     */
    public static class MenuOption {
        private final MenuSlot root;
        protected JMenuItem targetItem;
        private JComponent component;

        private MenuOption(String label, MenuSlot root) {
            this.targetItem = new JMenuItem(label);
            this.root = root;
        }

        /**
         * Sets the component to be displayed when the menu option is selected.
         *
         * @param component the component to display.
         * @return the current MenuOption instance for method chaining.
         * @throws BuildingException if the component is null.
         */
        public MenuOption setComponent(JComponent component) {
            if (Objects.isNull(component)) throw new BuildingException("The component for an menu option is null");
            this.component = component;
            return this;
        }

        /**
         * Adds an action to be executed when the menu option is selected.
         *
         * @param action the action to execute.
         * @return the current MenuOption instance for method chaining.
         * @throws BuildingException if the action is null.
         */
        public MenuOption addAction(Runnable action) {
            if (Objects.isNull(action)) throw new BuildingException("Action for the menu option is null");
            targetItem.addActionListener(l -> action.run());
            return this;
        }

        /**
         * Adds a component action to be executed when the menu option is selected.
         *
         * @param action the component action to execute.
         * @return the current MenuOption instance for method chaining.
         * @throws BuildingException if the action is null.
         */
        public MenuOption addItemComponentAction(Consumer<JMenuItem> action) {
            if (Objects.isNull(action)) throw new BuildingException("Component action for the menu slot is null");
            targetItem.addActionListener(l -> action.accept(targetItem));
            return this;
        }

        /**
         * Allows peeking into the JMenuItem of the menu option and applying a specified function.
         *
         * @param function the function to apply to the JMenuItem.
         * @return the current MenuOption instance for method chaining.
         * @throws BuildingException if the function is null.
         */
        public MenuOption peek(Consumer<JMenuItem> function) {
            if (Objects.isNull(function)) throw new BuildingException("Peek function for the menu slot is null");
            function.accept(targetItem);
            return this;
        }

        /**
         * Returns to the menu slot and adds this menu option to the menu slot.
         *
         * @return the MenuSlot instance.
         */
        public MenuSlot backToMenuSlot() {
            if (Objects.nonNull(component)) {
                targetItem.addActionListener(l -> {
                    root.frameBuilder.updateView(component);
                });
            }
            root.targetSlot.add(this.targetItem);
            return root;
        }
    }

    /**
     * Class representing a selectable menu option in the frame.
     */
    public static class SelectableMenuOption {
        private final JMenuItem targetItem;
        private final MenuSlot root;
        private JComponent component;

        private SelectableMenuOption(String label, MenuSlot root, boolean preselected, boolean isRadioButton) {
            this.root = root;
            this.targetItem = isRadioButton ? new JRadioButtonMenuItem(label) : new JCheckBoxMenuItem(label);
            this.targetItem.setSelected(preselected);
        }

        /**
         * Adds state-dependent actions to be executed based on the selection state.
         *
         * @param ifSelectedAction   the action to execute if selected.
         * @param ifDeselectedAction the action to execute if deselected.
         * @param ignoreIfNull       true to ignore null actions, false otherwise.
         * @return the current SelectableMenuOption instance for method chaining.
         * @throws BuildingException if the actions are null and ignoreIfNull is false.
         */
        public SelectableMenuOption addStateDependentAction(Runnable ifSelectedAction, Runnable ifDeselectedAction, boolean ignoreIfNull) {
            if (!ignoreIfNull) {
                if (Objects.isNull(ifSelectedAction))
                    throw new BuildingException("If selected action for a selectable menu option is null");
                if (Objects.isNull(ifDeselectedAction))
                    throw new BuildingException("If deselected action for a selectable menu option is null");
            }
            if (Objects.nonNull(ifSelectedAction)) targetItem.addActionListener(l -> {
                if (targetItem.isSelected()) ifSelectedAction.run();
            });
            if (Objects.nonNull(ifDeselectedAction)) targetItem.addActionListener(l -> {
                if (!targetItem.isSelected()) ifDeselectedAction.run();
            });
            return this;
        }

        /**
         * Adds state-dependent component actions to be executed based on the selection state.
         *
         * @param ifSelectedAction   the component action to execute if selected.
         * @param ifDeselectedAction the component action to execute if deselected.
         * @param ignoreIfNull       true to ignore null actions, false otherwise.
         * @return the current SelectableMenuOption instance for method chaining.
         * @throws BuildingException if the actions are null and ignoreIfNull is false.
         */
        public SelectableMenuOption addStateDependedComponentAction(Consumer<JMenuItem> ifSelectedAction, Consumer<JMenuItem> ifDeselectedAction, boolean ignoreIfNull) {
            if (!ignoreIfNull) {
                if (Objects.isNull(ifSelectedAction))
                    throw new BuildingException("If selected component action for a selectable menu option is null");
                if (Objects.isNull(ifDeselectedAction))
                    throw new BuildingException("If deselected component action for a selectable menu option is null");
            }
            if (Objects.nonNull(ifSelectedAction)) targetItem.addActionListener(l -> {
                if (targetItem.isSelected()) ifSelectedAction.accept(targetItem);
            });
            if (Objects.nonNull(ifDeselectedAction)) targetItem.addActionListener(l -> {
                if (!targetItem.isSelected()) ifDeselectedAction.accept(targetItem);
            });
            return this;
        }

        /**
         * Adds a state listener that reacts to the selection state of the menu option.
         *
         * @param stateConsumer the consumer to execute when the selection state changes. It receives a Boolean indicating whether the menu option is selected.
         * @return the current SelectableMenuOption instance for method chaining.
         * @throws BuildingException tf the stateConsumer is null.
         */
        public SelectableMenuOption addStateListener(Consumer<Boolean> stateConsumer) {
            if (Objects.isNull(stateConsumer)) throw new BuildingException("State listener function is null");
            targetItem.addActionListener(l -> {
                stateConsumer.accept(targetItem.isSelected());
            });
            return this;
        }


        /**
         * Sets the components to be displayed based on the selection state.
         *
         * @param ifSelectedComponent   the component to display if selected.
         * @param ifDeselectedComponent the component to display if deselected.
         * @param ignoreIfNull          true to ignore null components, false otherwise.
         * @return the current SelectableMenuOption instance for method chaining.
         * @throws BuildingException if the components are null and ignoreIfNull is false.
         */
        public SelectableMenuOption setComponent(JComponent ifSelectedComponent, JComponent ifDeselectedComponent, boolean ignoreIfNull) {
            if (!ignoreIfNull) {
                if (Objects.isNull(ifSelectedComponent))
                    throw new BuildingException("If selected component for a selectable menu option is null");
                if (Objects.isNull(ifDeselectedComponent))
                    throw new BuildingException("If deselected component for a selectable menu option is null");
            }
            if (Objects.nonNull(ifSelectedComponent)) targetItem.addActionListener(l -> {
                if (targetItem.isSelected()) root.frameBuilder.updateView(ifSelectedComponent);
            });
            if (Objects.nonNull(ifDeselectedComponent)) targetItem.addActionListener(l -> {
                if (!targetItem.isSelected()) root.frameBuilder.updateView(ifDeselectedComponent);
            });
            return this;
        }

        /**
         * Allows peeking into the JMenuItem of the selectable menu option and applying a specified function.
         *
         * @param function the function to apply to the JMenuItem.
         * @return the current SelectableMenuOption instance for method chaining.
         */
        public SelectableMenuOption peek(Consumer<JMenuItem> function) {
            if (Objects.nonNull(function)) function.accept(targetItem);
            return this;
        }

        /**
         * Returns to the menu slot and adds this selectable menu option to the menu slot.
         *
         * @return The MenuSlot instance.
         */
        public MenuSlot backToMenuSlot() {
            if (Objects.nonNull(component)) {
                targetItem.addActionListener(l -> {
                    root.frameBuilder.updateView(component);
                });
            }
            root.targetSlot.add(this.targetItem);
            return root;
        }
    }

    /**
     * Exception class for building-related errors.
     */
    public static class BuildingException extends RuntimeException {
        private BuildingException(String message) {
            super(message);
        }
    }
}
