package domain;

import domain.events.EventDispatcher;

/**
 * Non-breakable wall.
 */
public class IndestructibleWall extends Wall {
    /**
     * Constructs an always-solid wall with effectively infinite hit points.
     */
    public IndestructibleWall(String id, Vector2 position, EventDispatcher dispatcher) {
        super(id, position, false, Integer.MAX_VALUE, dispatcher);
    }
}
