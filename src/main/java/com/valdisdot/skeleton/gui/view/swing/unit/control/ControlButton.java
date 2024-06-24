package com.valdisdot.skeleton.gui.view.swing.unit.control;

import com.valdisdot.skeleton.core.ControlUnit;
import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.core.PresentableUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * The implementation of ControlUnit for clickable button.
 * @apiNote Pay attention that each user action will produce a new Thread if no ExecutorService was provided.
 * @since 1.0
 * @author Vladyslav Tymchenko
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

    public void setExecutorService(ExecutorService executorService){
        this.executorService = executorService;
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addDataActionFor(DataUnit<Data> element, Consumer<DataUnit<Data>> withMethod) {
        button.addActionListener(
                executorService == null ?
                l -> new Thread(() -> withMethod.accept(element)).start() :
                l -> executorService.execute(() -> withMethod.accept(element))
                );
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addDataActionFor(Collection<DataUnit<Data>> elements, Consumer<DataUnit<Data>> withMethod) {
        button.addActionListener(
                executorService == null ?
                l -> new Thread(() -> elements.forEach(withMethod)).start() :
                l -> executorService.execute(() -> elements.forEach(withMethod))
        );
    }

    /**{@inheritDoc}*/
    @Override
    public void addAction(Runnable action) {
        button.addActionListener(
                executorService == null ?
                l -> new Thread(action).start() :
                l -> executorService.execute(action));
    }

    /**{@inheritDoc}*/
    @Override
    public <Data> void addAction(Callable<Data> callable, Consumer<Data> resultConsumer, Consumer<Exception> exceptionConsumer) {
        button.addActionListener(
                    executorService == null ?
                    l -> new Thread(() -> {
                        try {
                            resultConsumer.accept(callable.call());
                        } catch (Exception e) {
                            exceptionConsumer.accept(e);
                        }
                    }).start() :
                    l -> {
                        try {
                            Future<Data> resultFuture = executorService.submit(callable);
                            executorService.execute(() -> {
                                try {
                                    resultConsumer.accept(resultFuture.get());
                                } catch (Exception e) {
                                    exceptionConsumer.accept(e);
                                }
                            });
                        } catch (Exception e) {
                            exceptionConsumer.accept(e);
                        }
                    }
                );
    }

    /**{@inheritDoc}*/
    @Override
    public <AView> void addViewActionFor(PresentableUnit<AView> element, Consumer<PresentableUnit<AView>> withMethod) {
        button.addActionListener(
                executorService == null ?
                l -> new Thread(() -> withMethod.accept(element)).start() :
                l -> executorService.execute(() -> withMethod.accept(element))
        );
    }

    /**{@inheritDoc}*/
    @Override
    public <AView> void addViewActionFor(Collection<PresentableUnit<AView>> elements, Consumer<PresentableUnit<AView>> withMethod) {
        button.addActionListener(
                executorService == null ?
                l -> new Thread(() -> elements.forEach(withMethod)).start() :
                l -> executorService.execute(() -> elements.forEach(withMethod))
                );
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) button.setText(presentation);
    }
}
