package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import javax.swing.*;

/**
 * The {@code Badge} class represents a label component with additional padding and border configuration,
 * commonly used for displaying badges in a Swing-based GUI.
 * <p>
 * The badge is essentially a specialized version of a label, with built-in styling for padding and background opacity.
 * Multiple constructors are provided to create badges with various configurations, including optional titles and customizable padding.
 * </p>
 *
 * @author Vladyslav Tymchenko
 * @since 1.0
 * @see JLabel
 */
public class Badge extends Label {

    /**
     * Creates a new {@code Badge} with the specified ID.
     * <p>
     * The badge is created with default vertical and horizontal padding of 5 pixels.
     * </p>
     *
     * @param id the unique ID of the badge, not null
     */
    public Badge(String id) {
        super(id);
        prepareAsBadge(5, 5);
    }

    /**
     * Creates a new {@code Badge} with the specified ID and title.
     * <p>
     * The badge is created with default vertical and horizontal padding of 5 pixels.
     * </p>
     *
     * @param id the unique ID of the badge, not null
     * @param title the text to be displayed in the badge, nullable
     */
    public Badge(String id, String title) {
        super(id, title);
        prepareAsBadge(5, 5);
    }

    /**
     * Creates a new {@code Badge} with the specified ID and custom padding.
     *
     * @param id the unique ID of the badge, not null
     * @param verticalPadding the padding for the top and bottom of the badge
     * @param horizontalPadding the padding for the left and right of the badge
     */
    public Badge(String id, int verticalPadding, int horizontalPadding) {
        super(id);
        prepareAsBadge(verticalPadding, horizontalPadding);
    }

    /**
     * Creates a new {@code Badge} with the specified ID, title, and custom padding.
     *
     * @param id the unique ID of the badge, not null
     * @param title the text to be displayed in the badge, nullable
     * @param verticalPadding the padding for the top and bottom of the badge
     * @param horizontalPadding the padding for the left and right of the badge
     */
    public Badge(String id, String title, int verticalPadding, int horizontalPadding) {
        super(id, title);
        prepareAsBadge(verticalPadding, horizontalPadding);
    }

    /**
     * Configures the badge's appearance by applying padding and making the background opaque.
     * <p>
     * This method is internally used by the constructors to set up the badge's styling.
     * </p>
     *
     * @param verticalPadding the padding for the top and bottom of the badge
     * @param horizontalPadding the padding for the left and right of the badge
     */
    private void prepareAsBadge(int verticalPadding, int horizontalPadding) {
        apply(internal -> {
            internal.setOpaque(true);
            internal.setBorder(BorderFactory.createEmptyBorder(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding));
        });
    }
}
