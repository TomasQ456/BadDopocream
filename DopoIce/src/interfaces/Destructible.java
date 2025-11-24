package interfaces;

import domain.IceException;

/**
 * Applied to objects that can be damaged or destroyed.
 */
public interface Destructible {
    /**
     * Applies one unit of destruction to the object.
     *
     * @throws IceException when the destruction attempt is invalid.
     */
    void breakBlock() throws IceException;

    /**
     * @return {@code true} once the object can no longer interact with the world.
     */
    boolean isDestroyed();
}
