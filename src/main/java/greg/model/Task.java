package greg.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import greg.exception.GregException;
import greg.model.Todo;

/**
 * Represents a task that can be stored in Greg.
 * <p>
 * A task has a description and a completion status.
 * Subclasses (e.g., Todo, Deadline, Event) extend this class
 * to add additional task-specific fields.
 */
public abstract class Task {

    protected final String description;
    protected boolean marked;

    private static final String SAVE_DELIMITER_REGEX = "\\s*\\|\\s*";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String TIME_REGEX = "\\d{4}";

    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";

    private static final String ERROR_CORRUPTED_SAVE_LINE = "Corrupted save line.";
    private static final String ERROR_CORRUPTED_DEADLINE_LINE = "Corrupted deadline line.";
    private static final String ERROR_CORRUPTED_EVENT_LINE = "Corrupted event line.";
    private static final String ERROR_UNKNOWN_TASK_TYPE = "Unknown task type in save file.";
    private static final String ERROR_INVALID_DATE = "Invalid date format. Use yyyy-mm-dd [HHmm].";
    private static final String ERROR_INVALID_TIME = "Invalid time format. Use HHmm (24-hour).";

    public Task(String description) throws GregException {
        if (description == null || description.isBlank()) {
            throw new GregException("Task description cannot be empty.");
        }
        this.description = description.trim();
        this.marked = false;
    }

    public String getDescription() {
        return description;
    }

    public void mark(boolean val) {
        this.marked = val;
    }

    @Override
    public String toString() {
        String cross = marked ? "X" : " ";
        return "[" + cross + "] " + description;
    }

    /**
     * Returns the encoded representation of this task for saving to disk.
     *
     * @return save string that can later be parsed back into a Task
     */
    public abstract String toSaveString();


    /**
     * Parses the date portion from a raw input string.
     * <p>
     * The expected format is: yyyy-mm-dd [HHmm]
     * Only the date portion is extracted by this method.
     *
     * @param raw raw input string containing date (and optional time)
     * @return parsed {@link LocalDate}
     * @throws GregException if the date format is invalid
     */
    protected static LocalDate parseDate(String raw) throws GregException {
        String[] parts = splitByWhitespace(raw);
        try {
            return LocalDate.parse(parts[0]);
        } catch (DateTimeParseException e) {
            throw new GregException(ERROR_INVALID_DATE);
        }
    }

    /**
     * Parses the optional time portion from a raw input string.
     * <p>
     * If no time is provided, returns an empty string.
     * Time must be in 24-hour format HHmm (e.g., 1730).
     *
     * @param raw raw input string containing date (and optional time)
     * @return time string (HHmm) or empty string if not provided
     * @throws GregException if the time format is invalid
     */
    protected static String parseOptionalTime(String raw) throws GregException {
        String[] parts = splitByWhitespace(raw);

        if (parts.length == 1) {
            return "";
        }

        if (parts.length == 2 && parts[1].matches(TIME_REGEX)) {
            return parts[1];
        }

        throw new GregException(ERROR_INVALID_TIME);
    }

    /**
     * Formats a date and optional time into a display string.
     * <p>
     * If time is empty, only the date is shown.
     *
     * @param date parsed date
     * @param time optional time (may be empty)
     * @return formatted string representation
     */
    protected static String formatDateTime(LocalDate date, String time) {
        return time.isEmpty() ? date.toString() : date + " " + time;
    }

    /**
     * Reconstructs a Task object from a single line in the save file.
     *
     * @param line save file line
     * @return Task reconstructed from encoded data
     * @throws GregException if the line is corrupted or contains unknown task type
     */
    public static Task fromSaveString(String line) throws GregException {
        if (line == null || line.isBlank()) {
            throw new GregException(ERROR_CORRUPTED_SAVE_LINE);
        }

        String[] parts = line.split(SAVE_DELIMITER_REGEX);
        if (parts.length < 3) {
            throw new GregException(ERROR_CORRUPTED_SAVE_LINE);
        }

        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        String desc = parts[2];

        Task task = createTaskFromParts(type, desc, parts);
        task.mark(done);
        return task;
    }

    private static Task createTaskFromParts(String type, String desc, String[] parts)
            throws GregException {

        switch (type) {
            case TYPE_TODO:
                return new Todo(desc);

            case TYPE_DEADLINE:
                return buildDeadline(desc, parts);

            case TYPE_EVENT:
                return buildEvent(desc, parts);

            default:
                throw new GregException(ERROR_UNKNOWN_TASK_TYPE);
        }
    }

    private static Deadline buildDeadline(String desc, String[] parts)
            throws GregException {

        if (parts.length < 5) {
            throw new GregException(ERROR_CORRUPTED_DEADLINE_LINE);
        }

        String byRaw = joinDateTime(parts[3], parts[4]);
        return new Deadline(desc, byRaw);
    }

    private static Event buildEvent(String desc, String[] parts)
            throws GregException {

        if (parts.length < 7) {
            throw new GregException(ERROR_CORRUPTED_EVENT_LINE);
        }

        String fromRaw = joinDateTime(parts[3], parts[4]);
        String toRaw = joinDateTime(parts[5], parts[6]);

        return new Event(desc, fromRaw, toRaw);
    }

    private static String joinDateTime(String date, String time) {
        return (time == null || time.isEmpty()) ? date : date + " " + time;
    }

    private static String[] splitByWhitespace(String raw) throws GregException {
        if (raw == null) {
            throw new GregException(ERROR_CORRUPTED_SAVE_LINE);
        }
        return raw.trim().split(WHITESPACE_REGEX);
    }
}