package greg;

import java.time.LocalDate;

public class Deadline extends Task {

    private LocalDate byDate;
    private String byTime;

    public Deadline(String description, String byRaw) throws GregException {
        super(description);

        this.byDate = parseDate(byRaw);
        this.byTime = parseOptionalTime(byRaw);
    }

//    @Override
//    public String toString() {
//        return "[D]" + super.toString() + " (by: " + by + ")";
//    }
//
//    @Override
//    public String toSaveString() {
//        return "D | " + (marked ? 1 : 0) + " | " + description + " | " + by;
//    }

    @Override
    public String toString() {
        String by = byTime.isEmpty()
                ? byDate.toString()
                : byDate + " " + byTime;

        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toSaveString() {
        // D | done | desc | yyyy-mm-dd | HHmm
        return "D | " + (marked ? 1 : 0)
                + " | " + description
                + " | " + byDate
                + " | " + byTime;
    }
}
