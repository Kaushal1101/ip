package greg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {

    @Test
    public void constructor_dateOnly_formatsToStringCorrectly() throws GregException {
        Event e = new Event("project meeting", "2026-01-30", "2026-02-03");
        assertEquals("[E][ ] project meeting (from: 2026-01-30 to: 2026-02-03)", e.toString());
    }

    @Test
    public void constructor_dateAndTime_formatsToStringCorrectly() throws GregException {
        Event e = new Event("fight someone", "2026-01-30 1400", "2026-02-03 1700");
        assertEquals("[E][ ] fight someone (from: 2026-01-30 1400 to: 2026-02-03 1700)", e.toString());
    }

    @Test
    public void toSaveString_dateOnly_hasEmptyTimeFields() throws GregException {
        Event e = new Event("project meeting", "2026-01-30", "2026-02-03");

        String[] parts = e.toSaveString().split("\\s*\\|\\s*", -1); // keeps empty string at end
        assertEquals(7, parts.length);

        assertEquals("E", parts[0]);
        assertEquals("0", parts[1]);
        assertEquals("project meeting", parts[2]);

        assertEquals("2026-01-30", parts[3]);
        assertEquals("", parts[4]);

        assertEquals("2026-02-03", parts[5]);
        assertEquals("", parts[6]);
    }

    @Test
    public void toSaveString_withTimes_savesTimeFields() throws GregException {
        Event e = new Event("project meeting", "2026-01-30 1400", "2026-02-03 1700");

        String[] parts = e.toSaveString().split("\\s*\\|\\s*");
        assertEquals(7, parts.length);

        assertEquals("E", parts[0]);
        assertEquals("0", parts[1]);
        assertEquals("project meeting", parts[2]);

        assertEquals("2026-01-30", parts[3]);
        assertEquals("1400", parts[4]);

        assertEquals("2026-02-03", parts[5]);
        assertEquals("1700", parts[6]);
    }

    @Test
    public void markDone_updatesSaveStringDoneBit() throws GregException {
        Event e = new Event("project meeting", "2026-01-30 1400", "2026-02-03 1700");
        e.mark(true);

        String[] parts = e.toSaveString().split("\\s*\\|\\s*");
        assertEquals("1", parts[1]);
    }

    @Test
    public void constructor_invalidDate_throws() {
        assertThrows(GregException.class,
                () -> new Event("x", "30-01-2026", "2026-02-03"));
    }

    @Test
    public void constructor_invalidTimeLength_throws() {
        assertThrows(GregException.class,
                () -> new Event("x", "2026-01-30 140", "2026-02-03"));
    }

    @Test
    public void constructor_nonNumericTime_throws() {
        assertThrows(GregException.class,
                () -> new Event("x", "2026-01-30 abcd", "2026-02-03"));
    }
}