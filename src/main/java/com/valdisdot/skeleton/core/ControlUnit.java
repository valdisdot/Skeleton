package com.valdisdot.skeleton.core;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The interface represents an abstraction of the control elements in the application.
 *
 * @author Vladyslav Tymchenko
 * @apiNote Pay attention that execution of all on-action methods can lock the thread or freeze the application, the best option is to implement this interface with Thread or ThreadPools.
 * @since 1.0
 */
public interface ControlUnit<ComponentViewType> extends PresentableUnit<ComponentViewType> {
    /**
     * @param element    a data unit of data type {@code <DataType>} of this unit element with which an action should be performed.
     * @param withMethod an action (consumer) with a data unit which should be performed after an explicit user interaction with a control element.
     */
    <DataType> void addDataActionFor(DataUnit<DataType> element, Consumer<DataUnit<DataType>> withMethod);

    /**
     * @param elements   data units of data type {@code <DataType>} of this unit element with which an action should be performed.
     * @param withMethod an action (consumer) with a data unit which should be performed for each unit after an explicit user interaction with a control element.
     */
    <DataType> void addDataActionFor(Collection<DataUnit<DataType>> elements, Consumer<DataUnit<DataType>> withMethod);

    /**
     * @param action an action which should be performed after an explicit user interaction with a control element.
     */
    void addAction(Runnable action);

    /**
     * @param callable          an action which should be performed after an explicit interaction with a control element.
     * @param resultConsumer    a consumer of the execution result of the callable action.
     * @param exceptionConsumer a consumer of the exceptional which callable action can throw.
     */
    <DataType> void addAction(Callable<DataType> callable, Consumer<DataType> resultConsumer, Consumer<Exception> exceptionConsumer);

    /**
     * @param element    a presentable unit of component type {@code <ComponentBViewType>} of this unit element with which an action should be performed.
     * @param withMethod an action (consumer) with a presentable unit which should be performed after an explicit user interaction with a control element.
     */
    <ComponentBViewType> void addViewActionFor(PresentableUnit<ComponentBViewType> element, Consumer<PresentableUnit<ComponentBViewType>> withMethod);

    /**
     * @param elements   presentable units of component type {@code <ComponentBViewType>} of this unit element with which an action should be performed.
     * @param withMethod an action (consumer) with a presentable unit which should be performed for each unit after an explicit user interaction with a control element.
     */
    <ComponentBViewType> void addViewActionFor(Collection<PresentableUnit<ComponentBViewType>> elements, Consumer<PresentableUnit<ComponentBViewType>> withMethod);

    /**
     * Method perform the function with itself.
     *
     * @param withMethod an action (consumer) with this presentable unit which should be performed after an explicit user interaction with a control element.
     */
    default void addViewActionForThis(Consumer<PresentableUnit<ComponentViewType>> withMethod) {
        addViewActionFor(this, withMethod);
    }
}