package domain;

import domain.events.EventDispatcher;

/**
 * Specialized breakable wall used for the player's ice powers.
 */
public class IceWall extends Wall {

    /**
     * Creates a wall that is always breakable regardless of hit points provided.
     */
    public IceWall(String id, Vector2 position, int hitPoints, EventDispatcher dispatcher) {
        super(id, position, true, hitPoints, dispatcher);
    }
}
