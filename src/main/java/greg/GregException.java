package greg;

public class GregException extends Exception {
    public GregException(String message) {
        super("Error: " + message);
    }
}
