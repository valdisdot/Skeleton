package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
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
     * @see PresentablePair
     */
    public RadioBox(String id, Collection<PresentablePair> view) {
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
    public DataBean<String> getBean() {
        Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
        AbstractButton button;
        while (enumeration.hasMoreElements()) {
            button = enumeration.nextElement();
            if (button.isSelected()) return new DataBean<>(getId(), button.getName());
        }
        return new DataBean<>(getId(), (String) null);
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) {
            String val = data.fetchFirst();
            Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
            AbstractButton button;
            while (enumeration.hasMoreElements()) {
                button = enumeration.nextElement();
                if (button.getName().equals(val)) {
                    button.setSelected(true);
                    return;
                }
            }
        }

    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        buttonGroup.clearSelection();
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        container.removeAll();
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            buttonGroup = new ButtonGroup();
            for (PresentablePair pair : view) {
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
