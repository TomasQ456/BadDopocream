package interfaces;

import domain.IceException;
import domain.Player;

/**
 * Represents items that can be collected by a player.
 */
public interface Collectible {
    /**
     * Executes collection logic against the provided player.
     *
     * @throws IceException when the collection cannot be completed.
     */
    void collect(Player player) throws IceException;

    /**
     * @return {@code true} when the item was previously collected.
     */
    boolean isCollected();
}
