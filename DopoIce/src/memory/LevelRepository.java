package memory;

import domain.IceException;

/**
 * Abstraction over persistence for levels and game saves.
 */
public interface LevelRepository {
    /**
     * Loads the level descriptor for the provided identifier.
     *
     * @throws IceException when the source cannot be loaded or parsed.
     */
    LevelDescriptor loadLevel(int id) throws IceException;

    /**
     * Persists the supplied game state snapshot.
     */
    void saveGameState(GameState state) throws IceException;

    /**
     * Reads a saved game snapshot.
     */
    GameState loadGameState(String path) throws IceException;
}
