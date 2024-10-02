package com.valdisdot.skeleton.core;

/**
 * Functional interface for canceling a bond action associated with {@code ControlUnits}.
 * <p>
 * This interface provides a single method, {@link #revokeRegistration()},
 * that can be used to revoke or cancel a registered action.
 * </p>
 *
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
@FunctionalInterface
public interface ActionRegistration {
    /**
     * Revokes the registration of the action, canceling the bond associated with it.
     */
    void revokeRegistration();
}