package com.valdisdot.skeleton.gui.view.swing.unit.control;

import com.valdisdot.skeleton.core.ActionRegistration;
import com.valdisdot.skeleton.core.ControlUnit;
import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.core.PresentableUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * The implementation of ControlUnit for clickable button.
 *
 * @author Vladyslav Tymchenko
 * @apiNote Pay attention that each user action will produce a new Thread if no ExecutorService was provided.
 * @since 1.0
 */
public class ControlButton extends JSinglePresentableUnit implements ControlUnit<JComponent> {
    private final JButton button;
    private ExecutorService executorService = null;

    /**
     * Instantiates a new button.
     *
     * @param id    the id, not null
     * @param title the title, nullable
     */
    public ControlButton(String id, String title) {
        this.button = title == null ? new JButton() : new JButton(title);
        this.button.setFocusPainted(false);
        this.button.setMargin(new Insets(5, 5, 5, 5));
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

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private ActionRegistration bindAction(Runnable runnable) {
        ActionListener l = executorService == null ? e -> new Thread(runnable).start() : e -> executorService.execute(runnable);
        button.addActionListener(l);
        return () -> button.removeActionListener(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <Data> ActionRegistration addDataActionFor(DataUnit<Data> element, Consumer<DataUnit<Data>> withMethod) {
        return bindAction(() -> withMethod.accept(element));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <Data> ActionRegistration addDataActionFor(Collection<DataUnit<Data>> elements, Consumer<DataUnit<Data>> withMethod) {
        return bindAction(() -> elements.forEach(withMethod));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionRegistration addAction(Runnable action) {
        return bindAction(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <Data> ActionRegistration addAction(Callable<Data> callable, Consumer<Data> resultConsumer, Consumer<Exception> exceptionConsumer) {
        return bindAction(() -> {
            try {
                resultConsumer.accept(callable.call());
            } catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <AView> ActionRegistration addViewActionFor(PresentableUnit<AView> element, Consumer<PresentableUnit<AView>> withMethod) {
        return bindAction(() -> withMethod.accept(element));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <AView> ActionRegistration addViewActionFor(Collection<PresentableUnit<AView>> elements, Consumer<PresentableUnit<AView>> withMethod) {
        return bindAction(() -> elements.forEach(withMethod));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPresentation(String presentation) {
        if (presentation != null) button.setText(presentation);
    }
}
