package presentation;

import domain.enums.Direction;

/**
 * Value object representing a user action coming from the UI layer.
 */
public final class InputCommand {

    /**
     * Enumerates the supported UI commands routed through the bridge.
     */
    public enum CommandType {
        MOVE,
        CREATE_ICE,
        DESTROY_ICE,
        SAVE,
        LOAD
    }

    private final CommandType type;
    private final Direction direction;
    private final String argument;

    private InputCommand(CommandType type, Direction direction, String argument) {
        this.type = type;
        this.direction = direction;
        this.argument = argument;
    }

    /**
     * @return command for moving toward the specified direction.
     */
    public static InputCommand move(Direction direction) {
        return new InputCommand(CommandType.MOVE, direction, null);
    }

    /**
     * @return command instructing the bridge to create an ice block.
     */
    public static InputCommand createIce() {
        return new InputCommand(CommandType.CREATE_ICE, null, null);
    }

    /**
     * @return command for destroying ice in a direction relative to the player.
     */
    public static InputCommand destroyIce(Direction direction) {
        return new InputCommand(CommandType.DESTROY_ICE, direction, null);
    }

    /**
     * @return command asking the bridge to save the game into the indicated slot.
     */
    public static InputCommand save(String slot) {
        return new InputCommand(CommandType.SAVE, null, slot);
    }

    /**
     * @return command that triggers a load from the provided slot or path.
     */
    public static InputCommand load(String slot) {
        return new InputCommand(CommandType.LOAD, null, slot);
    }

    /**
     * @return command type enumeration.
     */
    public CommandType getType() {
        return type;
    }

    /**
     * @return relevant direction for MOVE/DESTROY_ICE commands.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return additional argument (slot paths, etc.).
     */
    public String getArgument() {
        return argument;
    }
}
