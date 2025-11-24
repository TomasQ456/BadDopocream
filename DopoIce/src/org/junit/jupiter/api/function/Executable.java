package org.junit.jupiter.api.function;

/**
 * Functional interface used by {@code assertThrows}.
 */
@FunctionalInterface
public interface Executable {
    void execute() throws Throwable;
}
