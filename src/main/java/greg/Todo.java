package greg;

public class Todo extends Task {

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