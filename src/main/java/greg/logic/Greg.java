package greg.logic;

import greg.exception.GregException;
import greg.storage.Storage;
import greg.model.TaskList;
import greg.ui.Ui;
import greg.model.Task;
import greg.model.Event;
import greg.model.Todo;
import greg.model.Deadline;

import java.util.ArrayList;
import java.util.List;

public class Greg {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    private boolean isExit = false;

    public Greg(String filePath) {
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

    public String getWelcomeMessage() {
        return ui.getWelcome();
    }

    public boolean isExit() {
        return isExit;
    }

    public String getResponse(String input) {
        try {
            ParsedCommand cmd = Parser.parse(input);

            switch (cmd.type) {
                case BYE:
                    storage.saveAll(taskList.getAll());
                    isExit = true;
                    return ui.getGoodbye();

                case LIST:
                    return ui.getTaskList(taskList.getAll());

                case MARK: {
                    Task task = taskList.mark(cmd.index);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskMarked(task);
                }

                case UNMARK: {
                    Task task = taskList.unmark(cmd.index);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskUnmarked(task);
                }

                case DELETE: {
                    Task task = taskList.delete(cmd.index);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskDeleted(task, taskList.size());
                }

                case TODO: {
                    Task task = new Todo(cmd.description);
                    taskList.add(task);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskAdded(task, taskList.size());
                }

                case DEADLINE: {
                    Task task = new Deadline(cmd.description, cmd.byRaw);
                    taskList.add(task);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskAdded(task, taskList.size());
                }

                case EVENT: {
                    Task task = new Event(cmd.description, cmd.fromRaw, cmd.toRaw);
                    taskList.add(task);
                    storage.saveAll(taskList.getAll());
                    return ui.getTaskAdded(task, taskList.size());
                }

                case FIND: {
                    List<Task> matches = taskList.find(cmd.description);
                    return ui.getFindResults(matches);
                }

                default:
                    throw new GregException("Unknown command.");
            }

        } catch (GregException e) {
            return ui.getError(e.getMessage());
        }
    }
}