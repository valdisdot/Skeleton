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
 * The {@code ControlButton} class is an implementation of {@link ControlUnit} for a clickable Swing button.
 * <p>
 * This class provides functionality to bind various actions to a button, allowing the execution of code
 * when the button is clicked. The actions can be linked to data elements, view elements, or general
 * runnable tasks. The class supports both direct execution and multithreading through an {@link ExecutorService}.
 * </p>
 *
 * <p><strong>Note:</strong> Each user action creates a new thread if no {@link ExecutorService} is provided.</p>
 *
 * @author Vladyslav Tymchenko
 * @apiNote Pay attention that each user action will produce a new thread if no ExecutorService is provided.
 * @since 1.0
 */
public class ControlButton extends JSinglePresentableUnit implements ControlUnit<JComponent> {
    private final JButton button;
    private ExecutorService executorService = null;

    /**
     * Creates a new {@code ControlButton} with the specified ID and optional title.
     *
     * @param id    the unique ID for the button, must not be {@code null}
     * @param title the text displayed on the button, can be {@code null}
     */
    public ControlButton(String id, String title) {
        this.button = title == null ? new JButton() : new JButton(title);
        this.button.setFocusPainted(false);
        this.button.setMargin(new Insets(5, 5, 5, 5));
        this.button.setName(id);
        setComponent(this.button);
    }

    /**
     * Creates a new {@code ControlButton} with the specified ID and no title.
     *
     * @param id the unique ID for the button, must not be {@code null}
     */
    public ControlButton(String id) {
        this(id, null);
    }

    /**
     * Sets the {@link ExecutorService} to be used for executing actions.
     * If no executor service is set, each user interaction will create a new thread.
     *
     * @param executorService the executor service for managing action execution, can be {@code null}
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Binds the given {@link Runnable} action to the button, so it will be executed
     * when the button is clicked.
     *
     * @param runnable the action to be executed on button click
     * @return an {@link ActionRegistration} object that allows revoking the action
     */
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
