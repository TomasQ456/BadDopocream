package presentation;

/**
 * Rendering abstraction used by the bridge to update whatever UI is active.
 */
public interface Renderer {
    /**
     * Renders the provided immutable snapshot of the game state.
     */
    void renderSnapshot(PresentationBridge.ViewModel viewModel);
}
