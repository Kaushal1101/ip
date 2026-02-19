package greg.model;

import java.time.LocalDate;

import greg.exception.GregException;

/**
 * A task that occurs over a date/time range, with optional times.
 */
public class Event extends Task {

    private final LocalDate fromDate;
    private final String fromTime; // "" if not provided
    private final LocalDate toDate;
    private final String toTime;   // "" if not provided

    /**
     * Creates an event task.
     *
     * @param description task description
     * @param fromRaw raw start date/time string (yyyy-mm-dd or yyyy-mm-dd HHmm)
     * @param toRaw raw end date/time string (yyyy-mm-dd or yyyy-mm-dd HHmm)
     * @throws GregException if description is empty or date/time formats are invalid
     */
    public Event(String description, String fromRaw, String toRaw) throws GregException {
        super(description);

        this.fromDate = parseDate(fromRaw);
        this.fromTime = parseOptionalTime(fromRaw);

        this.toDate = parseDate(toRaw);
        this.toTime = parseOptionalTime(toRaw);
    }

    @Override
    public String toString() {
        String from = formatDateTime(fromDate, fromTime);
        String to = formatDateTime(toDate, toTime);
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toSaveString() {
        // E | done | desc | yyyy-mm-dd | HHmm | yyyy-mm-dd | HHmm
        return "E | " + (marked ? 1 : 0)
                + " | " + description
                + " | " + fromDate
                + " | " + fromTime
                + " | " + toDate
                + " | " + toTime;
    }
}