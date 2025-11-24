package presentation;

import domain.events.GameEvent;

/**
 * Listener for presentation-ready events.
 */
@FunctionalInterface
public interface UIListener {
    /**
     * Reacts to a game event with the current view model snapshot.
     */
    void onEvent(GameEvent event, PresentationBridge.ViewModel viewModel);
}
