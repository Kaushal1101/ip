package greg;

import java.util.ArrayList;
import java.util.List;

public class Greg {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Greg(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        List<Task> loadedTasks;
        try {
            loadedTasks = storage.loadAll();
        } catch (GregException e) {
            ui.showWarning(e.getMessage());
            loadedTasks = new ArrayList<>();
        }

        this.taskList = new TaskList(loadedTasks);
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            try {
                ParsedCommand cmd = Parser.parse(input);

                switch (cmd.type) {

                    case BYE:
                        storage.saveAll(taskList.getAll());
                        ui.showGoodbye();
                        ui.close();
                        return;

                    case LIST:
                        ui.showTaskList(taskList.getAll());
                        break;

                    case MARK: {
                        Task task = taskList.mark(cmd.index);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskMarked(task);
                        break;
                    }

                    case UNMARK: {
                        Task task = taskList.unmark(cmd.index);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskUnmarked(task);
                        break;
                    }

                    case DELETE: {
                        Task task = taskList.delete(cmd.index);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskDeleted(task.toString(), taskList.size());
                        break;
                    }

                    case TODO: {
                        Task task = new Todo(cmd.description);
                        taskList.add(task);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskAdded(task, taskList.size());
                        break;
                    }

                    case DEADLINE: {
                        Task task = new Deadline(cmd.description, cmd.byRaw);
                        taskList.add(task);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskAdded(task, taskList.size());
                        break;
                    }

                    case EVENT: {
                        Task task = new Event(cmd.description, cmd.fromRaw, cmd.toRaw);
                        taskList.add(task);
                        storage.saveAll(taskList.getAll());
                        ui.showTaskAdded(task, taskList.size());
                        break;
                    }

                    case FIND: {
                        List<Task> matches = taskList.find(cmd.description);
                        ui.showFindResults(matches);
                        break;
                    }

                    default:
                        throw new GregException("Unknown command.");

                }
            } catch (GregException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Greg("data/greg.txt").run();
    }
}