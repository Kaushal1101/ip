package greg.logic;

import greg.exception.GregException;

/**
 * Parses raw user input into a structured command representation.
 * <p>
 * Converts strings such as "todo ...", "deadline ... /by ...", "mark 2" into
 * {@link ParsedCommand} objects for downstream execution.
 */
public class Parser {

    /**
     * Parses a raw input line into a {@link ParsedCommand}.
     *
     * @param input Raw user input.
     * @return Parsed command containing command type and extracted fields.
     * @throws GregException If the input is not a valid command or is missing required parts.
     */
    public static ParsedCommand parse(String input) throws GregException {

        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE);
        }

        if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST);
        }

        if (input.startsWith("mark ")) {
            ParsedCommand cmd = new ParsedCommand(CommandType.MARK);
            cmd.index = parseIndex(input);
            return cmd;
        }

        if (input.startsWith("unmark ")) {
            ParsedCommand cmd = new ParsedCommand(CommandType.UNMARK);
            cmd.index = parseIndex(input);
            return cmd;
        }

        if (input.startsWith("delete ")) {
            ParsedCommand cmd = new ParsedCommand(CommandType.DELETE);
            cmd.index = parseIndex(input);
            return cmd;
        }

        if (input.startsWith("todo ")) {
            ParsedCommand cmd = new ParsedCommand(CommandType.TODO);
            cmd.description = input.substring(5).trim();
            return cmd;
        }

        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            String[] parts = rest.split("/by", 2);

            if (parts.length < 2) {
                throw new GregException("Invalid deadline format. Use: deadline <desc> /by yyyy-mm-dd [HHmm]");
            }

            ParsedCommand cmd = new ParsedCommand(CommandType.DEADLINE);
            cmd.description = parts[0].trim();
            cmd.byRaw = parts[1].trim();
            return cmd;
        }

        if (input.startsWith("event ")) {
            String rest = input.substring(6).trim();
            String[] fromSplit = rest.split("/from", 2);

            if (fromSplit.length < 2) {
                throw new GregException("Invalid event format. Use: event <desc> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]");
            }

            String[] toSplit = fromSplit[1].trim().split("/to", 2);
            if (toSplit.length < 2) {
                throw new GregException("Invalid event format. Use: event <desc> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]");
            }

            ParsedCommand cmd = new ParsedCommand(CommandType.EVENT);
            cmd.description = fromSplit[0].trim();
            cmd.fromRaw = toSplit[0].trim();
            cmd.toRaw = toSplit[1].trim();
            return cmd;
        }

        if (input.startsWith("find ")) {
            String query = input.substring(5).trim();
            if (query.isEmpty()) {
                throw new GregException("Find command must have a search term.");
            }
            ParsedCommand cmd = new ParsedCommand(CommandType.FIND);
            cmd.description = query;   // reuse description field
            return cmd;
        }

        throw new GregException("Invalid command.");
    }

    /**
     * Extracts a 1-indexed task number from commands like "mark 2" or "delete 3".
     *
     * @param input Raw input string.
     * @return Parsed task number (still 1-indexed).
     * @throws GregException If the task number is missing or not a valid integer.
     */
    private static int parseIndex(String input) throws GregException {
        try {
            return Integer.parseInt(input.split("\\s+")[1]);
        } catch (Exception e) {
            throw new GregException("Invalid task number.");
        }
    }
}