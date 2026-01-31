package greg;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class Task {
    protected String description;
    protected boolean marked;

    public Task(String description) {
        this.description = description;
        this.marked = false;
    }

    public String getDescription() {
        return description;
    }

    public void mark(boolean val) {
        this.marked = val;
    }

    public String toString() {
        String cross = marked ? "X" : " ";
        return "[" + cross + "] " + description;
    }

    protected static LocalDate parseDate(String raw) throws GregException {
        String[] parts = raw.trim().split("\\s+");
        try {
            return LocalDate.parse(parts[0]); // yyyy-mm-dd
        } catch (DateTimeParseException e) {
            throw new GregException("Invalid date format. Use yyyy-mm-dd [HHmm].");
        }
    }

    protected static String parseOptionalTime(String raw) throws GregException {
        String[] parts = raw.trim().split("\\s+");

        if (parts.length == 1) {
            return ""; // no time provided
        }

        if (parts.length == 2 && parts[1].matches("\\d{4}")) {
            return parts[1]; // HHmm
        }

        throw new GregException("Invalid time format. Use HHmm (24-hour).");
    }

    public abstract String toSaveString();

    public static Task fromSaveString(String line) throws GregException {
        String[] parts = line.split("\\s*\\|\\s*");

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
                if (parts.length < 5) {
                    throw new GregException("Corrupted deadline line.");
                }

                String byRaw = parts[3];
                if (!parts[4].isEmpty()) {
                    byRaw += " " + parts[4];
                }

                task = new Deadline(desc, byRaw);
                break;

            case "E":
                if (parts.length < 7) {
                    throw new GregException("Corrupted event line.");
                }

                String fromRaw = parts[3];
                if (!parts[4].isEmpty()) {
                    fromRaw += " " + parts[4];
                }

                String toRaw = parts[5];
                if (!parts[6].isEmpty()) {
                    toRaw += " " + parts[6];
                }

                task = new Event(desc, fromRaw, toRaw);
                break;

            default:
                throw new GregException("Unknown task type in save file.");
        }

        task.mark(done);
        return task;
    }
}