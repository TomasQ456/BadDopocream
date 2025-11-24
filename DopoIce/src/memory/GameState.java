package memory;

import domain.Vector2;

/**
 * Serializable snapshot of the running game used for persistence.
 */
public class GameState {

    private final int levelId;
    private final int score;
    private final Vector2 playerPosition;

    private GameState(Builder builder) {
        this.levelId = builder.levelId;
        this.score = builder.score;
        this.playerPosition = builder.playerPosition;
    }

    /**
     * @return identifier of the level stored in the snapshot.
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * @return score value captured in the snapshot.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return player position stored in the snapshot.
     */
    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    /**
     * @return builder suitable for constructing immutable instances.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int levelId;
        private int score;
        private Vector2 playerPosition = new Vector2(0, 0);

        /**
         * @param levelId identifier referencing {@link LevelDescriptor} entries.
         */
        public Builder levelId(int levelId) {
            this.levelId = levelId;
            return this;
        }

        /**
         * @param score score captured in the save.
         */
        public Builder score(int score) {
            this.score = score;
            return this;
        }

        /**
         * @param playerPosition coordinates for the player spawn during load.
         */
        public Builder playerPosition(Vector2 playerPosition) {
            this.playerPosition = playerPosition;
            return this;
        }

        /**
         * @return immutable {@link GameState} instance.
         */
        public GameState build() {
            return new GameState(this);
        }
    }
}
