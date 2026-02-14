package greg;

import java.util.ArrayList;
import java.util.List;

/**
 * Main coordinator of the Greg task manager.
 * Wires together UI, storage, and task list, and provides a single entry point
 * (getResponse) for the UI layer to obtain responses.
 */
public class Greg {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    private boolean isExit = false;

    /**
     * Creates a new Greg instance using the given data file path.
     *
     * @param filePath path to the save file used for loading and saving tasks.
     */
    public Greg(String filePath) {
        assert filePath != null && !filePath.isBlank() : "filePath must not be blank";

        this.ui = new Ui();
        this.storage = new Storage(filePath);

        List<Task> loadedTasks;
        try {
            loadedTasks = storage.loadAll();
        } catch (GregException e) {
            // In JavaFX, warnings should be returned as a message, not printed.
            loadedTasks = new ArrayList<>();
        }

        this.taskList = new TaskList(loadedTasks);
    }

    /**
     * Returns the welcome message for the UI to display.
     *
     * @return welcome message
     */
    public String getWelcomeMessage() {
        return ui.getWelcome();
    }

    /**
     * Returns whether the latest processed command requests the app to exit.
     *
     * @return true if the app should exit
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Processes a single user command and returns the response to be shown in the UI.
     *
     * @param input raw user input
     * @return response string for the UI to display
     */
    public String getResponse(String input) {
        assert input != null : "input should not be null";

        try {
            ParsedCommand cmd = parse(input);
            assert cmd != null && cmd.type != null : "Parser must return a command with a type"; // Assert code from merge 
            return execute(cmd);
        } catch (GregException e) {
            return ui.getError(e.getMessage());
        }
    }

    private ParsedCommand parse(String input) throws GregException {
        return Parser.parse(input);
    }

    private String execute(ParsedCommand cmd) throws GregException {
        switch (cmd.type) {
            case BYE:
                return handleBye();

            case LIST:
                return handleList();

            case MARK:
                return handleMark(cmd.index);

            case UNMARK:
                return handleUnmark(cmd.index);

            case DELETE:
                return handleDelete(cmd.index);

            case TODO:
                return handleAddTodo(cmd.description);

            case DEADLINE:
                return handleAddDeadline(cmd.description, cmd.byRaw);

            case EVENT:
                return handleAddEvent(cmd.description, cmd.fromRaw, cmd.toRaw);

            case FIND:
                return handleFind(cmd.description);

            default:
                throw new GregException("Unknown command.");
        }
    }

    private void save() throws GregException {
        storage.saveAll(taskList.getAll());
    }

    private String handleBye() throws GregException {
        save();
        isExit = true;
        return ui.getGoodbye();
    }

    private String handleList() {
        return ui.getTaskList(taskList.getAll());
    }

    private String handleMark(int index) throws GregException {
        assert index > 0 : "index should be positive";
        Task task = taskList.mark(index);
        save();
        return ui.getTaskMarked(task);
    }

    private String handleUnmark(int index) throws GregException {
        assert index > 0 : "index should be positive";
        Task task = taskList.unmark(index);
        save();
        return ui.getTaskUnmarked(task);
    }

    private String handleDelete(int index) throws GregException {
        assert index > 0 : "index should be positive";
        Task task = taskList.delete(index);
        save();
        return ui.getTaskDeleted(task, taskList.size());
    }

    private String handleAddTodo(String desc) throws GregException {
        Task task = new Todo(desc);
        taskList.add(task);
        save();
        return ui.getTaskAdded(task, taskList.size());
    }

    private String handleAddDeadline(String desc, String byRaw) throws GregException {
        Task task = new Deadline(desc, byRaw);
        taskList.add(task);
        save();
        return ui.getTaskAdded(task, taskList.size());
    }

    private String handleAddEvent(String desc, String fromRaw, String toRaw) throws GregException {
        Task task = new Event(desc, fromRaw, toRaw);
        taskList.add(task);
        save();
        return ui.getTaskAdded(task, taskList.size());
    }

    private String handleFind(String query) {
        List<Task> matches = taskList.find(query);
        return ui.getFindResults(matches);
    }
}