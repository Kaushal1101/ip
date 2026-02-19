package greg;

/**
 * A basic task that only stores a description and completion status.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSaveString() {
        // T | done | desc
        return "T | " + (marked ? 1 : 0)
                + " | " + description;
    }
}