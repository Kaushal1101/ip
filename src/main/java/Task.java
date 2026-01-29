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

    public abstract String toSaveString();

    public static Task fromSaveString(String line) throws GregException {
        String[] parts = line.split("\\s*\\|\\s*"); // handles " T | 1 | desc " safely

        if (parts.length < 3) {
            throw new GregException("Corrupted save line.");
        }

        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) throw new GregException("Corrupted deadline line.");
                task = new Deadline(desc, parts[3]);
                break;
            case "E":
                if (parts.length < 4) throw new GregException("Corrupted event line.");
                String timeRange = parts[3].trim().substring(5).trim(); // removes "from"
                String[] splitTime = timeRange.split(" - ", 2);
                System.out.println("SPLIT TIME: " + splitTime[0]);
                String from_part = splitTime[0];
                String to_part = splitTime[1];
                task = new Event(desc, from_part, to_part);
                break;
            default:
                throw new GregException("Unknown task type in save file.");
        }

        task.mark(done);
        return task;
    }
}