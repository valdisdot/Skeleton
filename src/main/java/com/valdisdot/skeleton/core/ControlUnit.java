package com.valdisdot.skeleton.core;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The {@code ControlUnit} interface represents an abstraction for control elements in the application.
 * <p>
 * Control elements can execute actions based on user interactions, and this interface defines methods
 * for binding data or view actions to those interactions. To avoid freezing or locking the application,
 * it is recommended to implement this interface using threads or thread pools.
 * </p>
 *
 * @param <ComponentViewType> the type of component view associated with this control unit
 * @author Vladyslav Tymchenko
 * @apiNote Be cautious, as the execution of on-action methods can lock the thread or freeze the application. It is best to implement this interface using threads or thread pools.
 * @since 1.0
 */
public interface ControlUnit<ComponentViewType> extends PresentableUnit<ComponentViewType> {

    /**
     * Adds an action to be performed when there is a user interaction with a control element
     * for the specified data unit.
     *
     * @param <DataType>   the data type of the data unit
     * @param element      the data unit on which the action is to be performed
     * @param withMethod   the action (a consumer of the data unit) to be performed upon user interaction
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    <DataType> ActionRegistration addDataActionFor(DataUnit<DataType> element, Consumer<DataUnit<DataType>> withMethod);

    /**
     * Adds an action to be performed when there is a user interaction with a control element
     * for each data unit in the specified collection.
     *
     * @param <DataType>   the data type of the data units
     * @param elements     the collection of data units on which the action is to be performed
     * @param withMethod   the action (a consumer of each data unit) to be performed upon user interaction
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    <DataType> ActionRegistration addDataActionFor(Collection<DataUnit<DataType>> elements, Consumer<DataUnit<DataType>> withMethod);

    /**
     * Adds a general action to be performed upon user interaction with a control element.
     *
     * @param action   the action (a runnable) to be executed
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    ActionRegistration addAction(Runnable action);

    /**
     * Adds an action with a result that should be performed upon user interaction, with handling for both
     * the result and any exceptions.
     *
     * @param <DataType>         the result type of the action
     * @param callable           the action to be performed, returning a result of type {@code DataType}
     * @param resultConsumer     a consumer for processing the result of the action
     * @param exceptionConsumer  a consumer for handling exceptions thrown by the action
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    <DataType> ActionRegistration addAction(Callable<DataType> callable, Consumer<DataType> resultConsumer, Consumer<Exception> exceptionConsumer);

    /**
     * Adds an action to be performed when there is a user interaction with a control element
     * for the specified presentable unit.
     *
     * @param <ComponentBViewType>  the view type of the presentable unit
     * @param element               the presentable unit on which the action is to be performed
     * @param withMethod            the action (a consumer of the presentable unit) to be performed upon user interaction
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    <ComponentBViewType> ActionRegistration addViewActionFor(PresentableUnit<ComponentBViewType> element, Consumer<PresentableUnit<ComponentBViewType>> withMethod);

    /**
     * Adds an action to be performed when there is a user interaction with a control element
     * for each presentable unit in the specified collection.
     *
     * @param <ComponentBViewType>  the view type of the presentable units
     * @param elements              the collection of presentable units on which the action is to be performed
     * @param withMethod            the action (a consumer of each presentable unit) to be performed upon user interaction
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    <ComponentBViewType> ActionRegistration addViewActionFor(Collection<PresentableUnit<ComponentBViewType>> elements, Consumer<PresentableUnit<ComponentBViewType>> withMethod);

    /**
     * Adds an action to be performed on this control unit itself when there is a user interaction
     * with the control element.
     *
     * @param withMethod the action (a consumer of this presentable unit) to be performed upon user interaction
     * @return an {@code ActionRegistration} object that can be used to revoke the action
     */
    default ActionRegistration addViewActionForThis(Consumer<PresentableUnit<ComponentViewType>> withMethod) {
        return addViewActionFor(this, withMethod);
    }
}
