package greg;

/**
 * Represents a user-facing error encountered in Greg.
 * <p>
 * Used for invalid commands, invalid indices, missing descriptions, and corrupted save data.
 */
public class GregException extends Exception {
    public GregException(String message) {
        super("Error: " + message);
    }
}
