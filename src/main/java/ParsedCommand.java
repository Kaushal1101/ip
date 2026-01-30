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