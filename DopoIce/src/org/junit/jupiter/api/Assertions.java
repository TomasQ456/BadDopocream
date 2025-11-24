package org.junit.jupiter.api;

import org.junit.jupiter.api.function.Executable;

import java.util.Objects;

/**
 * Minimal assertion helpers compatible with the test suite.
 */
public final class Assertions {

    private Assertions() {
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, "Expected condition to be true");
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, "Expected condition to be false");
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, "Expected <" + expected + "> but was <" + actual + ">");
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        if (Objects.equals(unexpected, actual)) {
            throw new AssertionError("Did not expect <" + unexpected + "> but both values matched");
        }
    }

    public static void assertNotNull(Object value) {
        assertNotNull(value, "Value must not be null");
    }

    public static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        try {
            executable.execute();
        } catch (Throwable throwable) {
            if (expectedType.isInstance(throwable)) {
                return expectedType.cast(throwable);
            }
            throw new AssertionError("Expected exception of type " + expectedType.getName()
                    + " but got " + throwable.getClass().getName(), throwable);
        }
        throw new AssertionError("Expected exception of type " + expectedType.getName() + " but nothing was thrown");
    }
}
