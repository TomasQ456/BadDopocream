package interfaces;

import domain.IceException;
import domain.Vector2;
import domain.enums.Direction;

/**
 * Contract for movable entities.
 */
public interface Movable {
    /**
     * Moves the entity in the provided direction.
     *
     * @throws IceException if the move cannot be completed.
     */
    void move(Direction direction) throws IceException;

    /**
     * @return current position of the movable actor.
     */
    Vector2 getPosition();
}
