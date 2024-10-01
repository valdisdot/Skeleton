package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.core.Pair;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * The implementation of a radio box element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class RadioBox extends JMultiPresentableUnit implements DataUnit<String> {
    private final JPanel container;
    private ButtonGroup buttonGroup;

    /**
     * Instantiates a new radio box.
     *
     * @param id   an id, not null
     * @param view a view, nullable
     * @see Pair
     */
    public RadioBox(String id, Collection<Pair> view) {
        JPanel container = new JPanel(new MigLayout("wrap,insets 0,gap 0")) {
            @Override
            public void setFont(Font font) {
                super.setFont(font);
                for (Component c : getComponents()) c.setFont(font);
            }

            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                for (Component c : getComponents()) c.setForeground(fg);
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(bg);
                for (Component c : getComponents()) c.setBackground(bg);
            }
        };
        container.setName(id);
        container.setOpaque(true);
        this.container = setComponent(container);
        replacePresentations(view);
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
        AbstractButton button;
        while (enumeration.hasMoreElements()) {
            button = enumeration.nextElement();
            if (button.isSelected()) return button.getName();
        }
        return "";
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
        AbstractButton button;
        while (enumeration.hasMoreElements()) {
            button = enumeration.nextElement();
            if (button.getName().equals(data)) {
                button.setSelected(true);
                return;
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void resetData() {
        buttonGroup.clearSelection();
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        container.removeAll();
        List<Pair> view = getCurrentView();
        if (!view.isEmpty()) {
            buttonGroup = new ButtonGroup();
            for (Pair pair : view) {
                JRadioButton jButton = new JRadioButton(pair.toString());
                jButton.setFont(container.getFont());
                jButton.setBackground(container.getBackground());
                jButton.setForeground(container.getForeground());
                jButton.setName(pair.getId());
                container.add(jButton);
                buttonGroup.add(jButton);
                addPart(jButton);
            }
            container.revalidate();
        }
    }
}
