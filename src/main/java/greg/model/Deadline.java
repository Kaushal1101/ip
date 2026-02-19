package greg.model;

import java.time.LocalDate;

import greg.exception.GregException;

/**
 * A task that must be completed by a specific date (and optional time).
 */
public class Deadline extends Task {

    private LocalDate byDate;
    private String byTime;

    /**
     * Creates a deadline task.
     *
     * @param description Task description.
     * @param byRaw       Raw date/time string provided by the user (e.g., yyyy-mm-dd or yyyy-mm-dd HHmm).
     * @throws GregException If the description is empty or date/time format is invalid.
     */
    public Deadline(String description, String byRaw) throws GregException {
        super(description);

        this.byDate = parseDate(byRaw);
        this.byTime = parseOptionalTime(byRaw);
    }


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
