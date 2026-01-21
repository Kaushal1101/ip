public abstract class Task {
    String description;
    boolean marked;

    public Task(String description) {
        this.description = description;
        this.marked = false;
    }

    public void mark(boolean val) {
        this.marked = val;
    }


    public String toString() {
        String cross = marked ? "X" : " ";
        return "[" + cross + "] " + description;
    }
}