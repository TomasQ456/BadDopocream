package domain;

import domain.enums.Direction;

/**
 * Strategy interface for determining the next direction of an AI-controlled entity.
 */
@FunctionalInterface
public interface MovementPattern {
    /**
     * Computes the next direction that the supplied entity should move.
     */
    Direction nextDirection(Entity entity, DopoIceCream game);
}
