package domain.enums;

/**
 * Every significant state change must be expressed via one of these event types.
 */
public enum GameEventType {
    PLAYER_MOVED,
    FRUIT_COLLECTED,
    BLOCK_CREATED,
    BLOCK_DESTROYED,
    PLAYER_HIT,
    PLAYER_DIED,
    LEVEL_LOADED,
    LEVEL_COMPLETED,
    ENEMY_SPAWNED,
    ENEMY_KILLED,
    SAVE_GAME,
    LOAD_GAME,
    MAP_UPDATED,
    CELL_UPDATED,
    COLLISION,
    ICE_CREATED,
    ICE_DESTROYED
}
