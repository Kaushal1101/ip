package greg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import greg.logic.ParsedCommand;
import greg.logic.Parser;
import greg.logic.CommandType;
import greg.exception.GregException;

public class ParserTest {

    @Test
    public void parse_bye_returnsByeCommand() throws GregException {
        ParsedCommand cmd = Parser.parse("bye");
        assertEquals(CommandType.BYE, cmd.type);
    }

    @Test
    public void parse_list_returnsListCommand() throws GregException {
        ParsedCommand cmd = Parser.parse("list");
        assertEquals(CommandType.LIST, cmd.type);
    }

    @Test
    public void parse_mark_validIndex_setsIndex() throws GregException {
        ParsedCommand cmd = Parser.parse("mark 2");
        assertEquals(CommandType.MARK, cmd.type);
        assertEquals(2, cmd.index);
    }

    @Test
    public void parse_deadline_valid_setsDescriptionAndByRaw() throws GregException {
        ParsedCommand cmd = Parser.parse("deadline return book /by 2026-03-02 1700");
        assertEquals(CommandType.DEADLINE, cmd.type);
        assertEquals("return book", cmd.description);
        assertEquals("2026-03-02 1700", cmd.byRaw);
    }

    @Test
    public void parse_event_valid_setsAllFields() throws GregException {
        ParsedCommand cmd = Parser.parse("event project meeting /from 2026-01-30 1400 /to 2026-02-03");
        assertEquals(CommandType.EVENT, cmd.type);
        assertEquals("project meeting", cmd.description);
        assertEquals("2026-01-30 1400", cmd.fromRaw);
        assertEquals("2026-02-03", cmd.toRaw);
    }

    @Test
    public void parse_deadline_missingBy_throws() {
        GregException e = assertThrows(GregException.class,
                () -> Parser.parse("deadline return book 2026-03-02"));
        assertTrue(e.getMessage().startsWith("Error: Invalid deadline format. Use: deadline <desc> /by yyyy-mm-dd [HHmm]"));
    }

    @Test
    public void parse_mark_invalidNumber_throws() {
        GregException e = assertThrows(GregException.class,
                () -> Parser.parse("mark two"));
        assertEquals("Error: Invalid task number.", e.getMessage());
    }

    @Test
    public void parse_unknownCommand_throws() {
        GregException e = assertThrows(GregException.class,
                () -> Parser.parse("blah"));
        assertEquals("Error: Invalid command.", e.getMessage());
    }
}