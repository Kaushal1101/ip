package greg;

public class Parser {

    private static final String INVALID_COMMAND = "Invalid command.";

    private static final String DEADLINE_FORMAT =
            "Invalid deadline format. Use: deadline <desc> /by yyyy-mm-dd [HHmm]";
    private static final String EVENT_FORMAT =
            "Invalid event format. Use: event <desc> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]";

    private static final int SPLIT_TWO = 2;


    public static ParsedCommand parse(String input) throws GregException {
        if (input == null) throw new GregException(INVALID_COMMAND);

        input = input.trim();
        if (input.isEmpty()) throw new GregException(INVALID_COMMAND);

        String[] parts = input.split("\\s+", SPLIT_TWO);
        String keyword = parts[0];
        String rest = parts.length == 2 ? parts[1].trim() : "";

        switch (keyword) {
            case "bye":
                return new ParsedCommand(CommandType.BYE);

            case "list":
                return new ParsedCommand(CommandType.LIST);

            case "mark":
                return parseIndexed(CommandType.MARK, rest);

            case "unmark":
                return parseIndexed(CommandType.UNMARK, rest);

            case "delete":
                return parseIndexed(CommandType.DELETE, rest);

            case "todo":
                return parseTextCommand(CommandType.TODO, rest, "Todo must have a description.");

            case "find":
                return parseTextCommand(CommandType.FIND, rest, "Find command must have a search term.");

            case "deadline":
                return parseDeadline(rest);

            case "event":
                return parseEvent(rest);

            default:
                throw new GregException(INVALID_COMMAND);
        }
    }

    private static ParsedCommand parseIndexed(CommandType type, String rest) throws GregException {
        String arg = requireRest(rest, "Invalid task number.");

        try {
            int index = Integer.parseInt(arg);
            ParsedCommand cmd = new ParsedCommand(type);
            cmd.index = index;
            return cmd;
        } catch (NumberFormatException e) {
            throw new GregException("Invalid task number.");
        }
    }

    private static ParsedCommand parseTextCommand(CommandType type, String rest, String emptyMsg) throws GregException {
        String text = requireRest(rest, emptyMsg);

        ParsedCommand cmd = new ParsedCommand(type);
        cmd.description = text; // works for TODO and FIND (FIND reuses description)
        return cmd;
    }

    private static ParsedCommand parseDeadline(String rest) throws GregException {
        String body = requireRest(rest, DEADLINE_FORMAT);

        String[] split = splitRequired(body, "/by", DEADLINE_FORMAT);

        String desc = requireNonEmpty(split[0], DEADLINE_FORMAT);
        String byRaw = requireNonEmpty(split[1], DEADLINE_FORMAT);

        ParsedCommand cmd = new ParsedCommand(CommandType.DEADLINE);
        cmd.description = desc;
        cmd.byRaw = byRaw;
        return cmd;
    }

    private static ParsedCommand parseEvent(String rest) throws GregException {
        String body = requireRest(rest, EVENT_FORMAT);

        String[] fromSplit = splitRequired(body, "/from", EVENT_FORMAT);
        String desc = requireNonEmpty(fromSplit[0], EVENT_FORMAT);

        String[] toSplit = splitRequired(fromSplit[1], "/to", EVENT_FORMAT);
        String fromRaw = requireNonEmpty(toSplit[0], EVENT_FORMAT);
        String toRaw = requireNonEmpty(toSplit[1], EVENT_FORMAT);

        ParsedCommand cmd = new ParsedCommand(CommandType.EVENT);
        cmd.description = desc;
        cmd.fromRaw = fromRaw;
        cmd.toRaw = toRaw;
        return cmd;
    }

    // ---- Common helpers ----

    private static String requireRest(String rest, String errorMsg) throws GregException {
        if (rest == null || rest.trim().isEmpty()) {
            throw new GregException(errorMsg);
        }
        return rest.trim();
    }

    private static String requireNonEmpty(String s, String errorMsg) throws GregException {
        if (s == null || s.trim().isEmpty()) {
            throw new GregException(errorMsg);
        }
        return s.trim();
    }

    private static String[] splitRequired(String text, String delimiter, String errorMsg) throws GregException {
        String[] split = text.split(delimiter, SPLIT_TWO);
        if (split.length < 2) {
            throw new GregException(errorMsg);
        }
        // Trim both sides early so callers don't forget
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        return split;
    }
}