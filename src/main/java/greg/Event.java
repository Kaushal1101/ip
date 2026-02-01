package greg;

import java.time.LocalDate;


/**
 * A task that occurs over a date/time range, with optional times.
 */
public class Event extends Task {

    private LocalDate fromDate;
    private String fromTime; // "" if not provided
    private LocalDate toDate;
    private String toTime;

    /**
     * Creates an event task.
     *
     * @param description Task description.
     * @param fromRaw     Raw start date/time string (e.g., yyyy-mm-dd or yyyy-mm-dd HHmm).
     * @param toRaw       Raw end date/time string (e.g., yyyy-mm-dd or yyyy-mm-dd HHmm).
     * @throws GregException If description is empty or any date/time format is invalid.
     */
    public Event(String description, String fromRaw, String toRaw) throws GregException {
        super(description);

        this.fromDate = parseDate(fromRaw);
        this.fromTime = parseOptionalTime(fromRaw);

        this.toDate = parseDate(toRaw);
        this.toTime = parseOptionalTime(toRaw);
    }

//    @Override
//    public String toString() {
//        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
//    }
//
//    @Override
//    public String toSaveString() {
//        return "E | " + (marked ? 1 : 0) + " | " + description + " | " + " from: " + from + " - " + to;
//    }

    @Override
    public String toString() {
        String from = fromTime.isEmpty() ? fromDate.toString() : fromDate + " " + fromTime;
        String to = toTime.isEmpty() ? toDate.toString() : toDate + " " + toTime;
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toSaveString() {
        // E | done | desc | fromDate | fromTime | toDate | toTime
        return "E | " + (marked ? 1 : 0)
                + " | " + description
                + " | " + fromDate
                + " | " + fromTime
                + " | " + toDate
                + " | " + toTime;
    }
}

