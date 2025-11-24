package domain.events;

import domain.enums.GameEventType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Mediates domain events between producers and listeners. Dispatching is synchronous by default
 * to keep deterministic tests but can be toggled to asynchronous delivery when the constructor flag
 * is set to {@code true}.
 */
public class EventDispatcher {

    private final List<GameEventListener> globalListeners = new ArrayList<>();
    private final Map<GameEventType, List<GameEventListener>> listenersByType = new EnumMap<>(GameEventType.class);
    private final ExecutorService executor;
    private final boolean async;

    /**
     * Creates a synchronous dispatcher.
     */
    public EventDispatcher() {
        this(false);
    }

    /**
     * @param async {@code true} to dispatch events on an executor thread, {@code false} to run listeners inline.
     */
    public EventDispatcher(boolean async) {
        this.async = async;
        this.executor = Executors.newSingleThreadExecutor(new EventThreadFactory());
    }

    /**
     * Registers a global listener invoked for every event.
     */
    public synchronized void addListener(GameEventListener listener) {
        globalListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * Registers a listener bound to a specific event type.
     */
    public synchronized void addListener(GameEventType type, GameEventListener listener) {
        listenersByType.computeIfAbsent(type, key -> new ArrayList<>()).add(Objects.requireNonNull(listener));
    }

    /**
     * Removes the provided listener from both global and typed lists.
     */
    public synchronized void removeListener(GameEventListener listener) {
        globalListeners.remove(listener);
        listenersByType.values().forEach(list -> list.remove(listener));
    }

    /**
     * Dispatches the event either inline or via the executor depending on the configuration; exceptions
     * are swallowed so that one faulty listener never prevents others from running.
     */
    public void dispatch(GameEvent event) {
        Objects.requireNonNull(event, "event");
        List<GameEventListener> snapshot = collectListeners(event.getType());
        for (GameEventListener listener : snapshot) {
            if (async) {
                executor.submit(() -> safeInvoke(listener, event));
            } else {
                safeInvoke(listener, event);
            }
        }
    }

    /**
     * Builds a snapshot of listeners interested in the provided type.
     */
    private List<GameEventListener> collectListeners(GameEventType type) {
        List<GameEventListener> snapshot = new ArrayList<>();
        synchronized (this) {
            snapshot.addAll(globalListeners);
            List<GameEventListener> specific = listenersByType.get(type);
            if (specific != null) {
                snapshot.addAll(specific);
            }
        }
        return snapshot;
    }

    /**
     * Safely invokes a listener and logs any thrown exception.
     */
    private void safeInvoke(GameEventListener listener, GameEvent event) {
        try {
            listener.onEvent(event);
        } catch (Throwable ignored) {
            // Logged via standard error to aid debugging without halting dispatch.
            ignored.printStackTrace();
        }
    }

    /**
     * Stops the executor and interrupts any queued deliveries.
     */
    public void shutdown() {
        executor.shutdownNow();
    }

    private static final class EventThreadFactory implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("event-dispatcher-" + counter.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        }
    }
}
