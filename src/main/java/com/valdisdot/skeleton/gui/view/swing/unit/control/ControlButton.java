package com.valdisdot.skeleton.gui.view.swing.unit.control;

import com.valdisdot.skeleton.core.control.ControlUnit;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentableUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The implementation of ControlUnit for clickable button.
 * @author Vladyslav Tymchenko
 * @apiNote Pay attention that each user action will produce a new Thread.
 * @since 1.0
 */
public class ControlButton extends JSinglePresentableUnit implements ControlUnit<JComponent> {
    private final JButton button;

    /**
     * Instantiates a new button.
     *
     * @param id    the id, not null
     * @param title the title, nullable
     */
    public ControlButton(String id, String title) {
        this.button = title == null ? new JButton() : new JButton(title);
        this.button.setFocusPainted(false);
        this.button.setName(id);
        setComponent(this.button);
    }

    /**
     * Instantiates a new control button.
     *
     * @param id the id, not null
     */
    public ControlButton(String id) {
        this(id, null);
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addDataActionFor(DataUnit<Data> element, Consumer<DataUnit<Data>> withMethod) {
        button.addActionListener(l -> new Thread(() -> withMethod.accept(element)).start());
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addDataActionFor(Collection<DataUnit<Data>> elements, Consumer<DataUnit<Data>> withMethod) {
        button.addActionListener(l -> new Thread(() -> elements.forEach(withMethod)).start());
    }

    /**{@inheritDoc}*/
    @Override
    public void addAction(Runnable action) {
        button.addActionListener(l -> new Thread(action).start());
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addAction(Callable<Data> callable, Consumer<Data> resultConsumer, Consumer<Exception> exceptionConsumer) {
        button.addActionListener(
                l -> new Thread(() -> {
                    try {
                        resultConsumer.accept(callable.call());
                    } catch (Exception e) {
                        exceptionConsumer.accept(e);
                    }
                }).start());
    }

    /**{@inheritDoc}*/
    @Override
    public <AView> void addViewActionFor(PresentableUnit<AView> element, Consumer<PresentableUnit<AView>> withMethod) {
        button.addActionListener(l -> new Thread(() -> withMethod.accept(element)).start());
    }

    /**{@inheritDoc}*/
    @Override
    public <AView> void addViewActionFor(Collection<PresentableUnit<AView>> elements, Consumer<PresentableUnit<AView>> withMethod) {
        button.addActionListener(l -> new Thread(() -> elements.forEach(withMethod)).start());
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        button.setText(presentation);
    }
}
