package domain.events;

/**
 * Listener abstraction used by the dispatcher.
 */
@FunctionalInterface
public interface GameEventListener {
    /**
     * Reacts to a dispatched game event.
     *
     * @param event immutable event payload.
     */
    void onEvent(GameEvent event);
}
