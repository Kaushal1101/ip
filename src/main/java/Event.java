import java.time.LocalDate;

public class Event extends Task {

    private LocalDate fromDate;
    private String fromTime; // "" if not provided
    private LocalDate toDate;
    private String toTime;

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

