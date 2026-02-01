package greg;

/**
 * A basic task that only stores a description and completion status.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description Description of the todo.
     * @throws GregException If the description is empty.
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
        return "T | " + (marked ? 1 : 0) + " | " + description;
    }
}