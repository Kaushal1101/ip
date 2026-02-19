package greg.logic;

/**
 * A structured representation of a user command after parsing.
 * <p>
 * Fields are populated depending on the {@link CommandType}. For example:
 * MARK/UNMARK/DELETE uses {@code index}, TODO uses {@code description},
 * DEADLINE uses {@code description} + {@code byRaw}, EVENT uses {@code description} + {@code fromRaw} + {@code toRaw}.
 */
public class ParsedCommand {
    public CommandType type;

    // for mark / unmark / delete
    public int index;

    // for todo / deadline / event
    public String description;
    public String byRaw;
    public String fromRaw;
    public String toRaw;

    public ParsedCommand(CommandType type) {
        this.type = type;
    }
}