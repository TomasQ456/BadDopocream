package test.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import test.domain.CellTest;
import test.domain.DopoIceCreamTest;
import test.domain.EventDispatcherTest;
import test.domain.FruitTest;
import test.domain.IceWallTest;
import test.domain.MonsterTest;
import test.domain.PathFinderTest;
import test.domain.PlayerTest;
import test.memory.LevelRepositoryTest;
import test.presentation.PresentationBridgeTest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lightweight reflection-based runner that executes the in-repo tests without Maven.
 */
public final class TestRunner {

    private static final List<Class<?>> TEST_CLASSES = List.of(
            CellTest.class,
            DopoIceCreamTest.class,
            EventDispatcherTest.class,
            FruitTest.class,
            IceWallTest.class,
            MonsterTest.class,
            PathFinderTest.class,
            PlayerTest.class,
            LevelRepositoryTest.class,
            PresentationBridgeTest.class
    );

    private TestRunner() {
    }

    public static void main(String[] args) {
        List<TestResult> results = new ArrayList<>();
        TEST_CLASSES.forEach(clazz -> runTestsForClass(clazz, results));
        long failures = results.stream().filter(result -> !result.passed).count();
        results.forEach(result -> System.out.println(result.format()));
        System.out.println("Executed " + results.size() + " tests: "
                + (results.size() - failures) + " passed, " + failures + " failed.");
        if (failures > 0) {
            System.exit(1);
        }
    }

    private static void runTestsForClass(Class<?> clazz, List<TestResult> results) {
        List<Method> beforeEach = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEach.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        testMethods.sort(Comparator.comparing(Method::getName));
        for (Method testMethod : testMethods) {
            results.add(runSingleTest(clazz, beforeEach, testMethod));
        }
    }

    private static TestResult runSingleTest(Class<?> clazz, List<Method> beforeEach, Method testMethod) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            injectTempDirs(instance);
            for (Method hook : beforeEach) {
                hook.setAccessible(true);
                hook.invoke(instance);
            }
            testMethod.setAccessible(true);
            testMethod.invoke(instance);
            return new TestResult(clazz.getSimpleName(), testMethod.getName(), true, null);
        } catch (InvocationTargetException invocationTargetException) {
            return new TestResult(clazz.getSimpleName(), testMethod.getName(), false, invocationTargetException.getCause());
        } catch (Exception exception) {
            return new TestResult(clazz.getSimpleName(), testMethod.getName(), false, exception);
        }
    }

    private static void injectTempDirs(Object instance) throws IOException, IllegalAccessException {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(TempDir.class)) {
                if (!Path.class.equals(field.getType())) {
                    throw new IllegalStateException("@TempDir can only annotate Path fields");
                }
                Path dir = Files.createTempDirectory("tests-");
                field.setAccessible(true);
                field.set(instance, dir);
            }
        }
    }

    private record TestResult(String className, String methodName, boolean passed, Throwable failure) {
        String format() {
            if (passed) {
                return "[PASS] " + className + "." + methodName;
            }
            return "[FAIL] " + className + "." + methodName + " -> " + failure.getClass().getSimpleName()
                    + (failure.getMessage() == null ? "" : ": " + failure.getMessage());
        }
    }
}
