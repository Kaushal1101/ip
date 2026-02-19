package greg.ui;

import java.util.List;

import greg.model.Task;

/**
 * Provides user-facing messages for the Greg task manager.
 * <p>
 * This class is responsible only for formatting output strings.
 * It does not handle input or printing to console.
 */
public class Ui {

    private static final String PREFIX_ERROR = "Error: ";
    private static final String PREFIX_WARNING = "Warning: ";

    private static final String MSG_EMPTY_TASK_LIST = "Your task list is empty.";
    private static final String MSG_NO_MATCHES = "No matching tasks found.";
    private static final String MSG_MATCHES_HEADER = "Here are the matching tasks in your list:";

    /**
     * Returns the welcome message shown at application startup.
     *
     * @return formatted welcome message
     */
    public String getWelcome() {
        return """
                Hello! I'm Greg
                What can I do for you?
                """.trim();
    }

    /**
     * Returns the goodbye message shown when exiting the application.
     *
     * @return formatted goodbye message
     */
    public String getGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Formats a message indicating a task has been added.
     *
     * @param task the task that was added
     * @param taskCount the updated total number of tasks
     * @return formatted confirmation message
     */
    public String getTaskAdded(Task task, int taskCount) {
        return formatTaskCountMessage(
                "Got it. I've added this task:",
                task,
                taskCount
        );
    }

    /**
     * Formats a message indicating a task has been deleted.
     *
     * @param deletedTask the task that was removed
     * @param taskCount the updated total number of tasks
     * @return formatted confirmation message
     */
    public String getTaskDeleted(Task deletedTask, int taskCount) {
        return formatTaskCountMessage(
                "Noted. I've removed this task:",
                deletedTask,
                taskCount
        );
    }

    /**
     * Formats a message indicating a task has been marked as done.
     *
     * @param task the task that was marked
     * @return formatted confirmation message
     */
    public String getTaskMarked(Task task) {
        return formatSingleTaskMessage(
                "Nice! I've marked this task as done:",
                task
        );
    }

    /**
     * Formats a message indicating a task has been unmarked.
     *
     * @param task the task that was unmarked
     * @return formatted confirmation message
     */
    public String getTaskUnmarked(Task task) {
        return formatSingleTaskMessage(
                "OK, I've marked this task as not done yet:",
                task
        );
    }

    /**
     * Formats the full task list for display.
     *
     * @param tasks list of tasks
     * @return formatted task list
     */
    public String getTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return MSG_EMPTY_TASK_LIST;
        }
        return formatNumberedTasks(tasks);
    }

    /**
     * Formats search results from a find operation.
     *
     * @param matches list of matching tasks
     * @return formatted find results
     */
    public String getFindResults(List<Task> matches) {
        if (matches.isEmpty()) {
            return MSG_NO_MATCHES;
        }
        return MSG_MATCHES_HEADER + "\n" + formatNumberedTasks(matches);
    }

    /**
     * Returns the help message listing all available commands.
     *
     * @return formatted help message
     */
    public String getHelp() {
        return """
                Available commands:
                  list
                    - Displays all tasks.

                  todo <description>
                    - Adds a todo task.

                  deadline <description> /by yyyy-mm-dd [HHmm]
                    - Adds a task with a deadline.

                  event <description> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]
                    - Adds an event task.

                  mark <task number>
                    - Marks a task as done.

                  unmark <task number>
                    - Marks a task as not done.

                  delete <task number>
                    - Deletes a task.

                  find <keyword>
                    - Finds tasks containing the keyword.

                  help
                    - Shows this help message.

                  bye
                    - Exits the application.
                """.trim();
    }

    /**
     * Formats an error message.
     *
     * @param message error description
     * @return formatted error message
     */
    public String getError(String message) {
        return PREFIX_ERROR + safeMessage(message);
    }

    /**
     * Formats a warning message.
     *
     * @param message warning description
     * @return formatted warning message
     */
    public String getWarning(String message) {
        return PREFIX_WARNING + safeMessage(message);
    }



    private String formatSingleTaskMessage(String header, Task task) {
        return header + "\n" + task;
    }

    private String formatTaskCountMessage(String header, Task task, int taskCount) {
        return header + "\n"
                + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    private String formatNumberedTasks(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    private String safeMessage(String message) {
        if (message == null || message.isBlank()) {
            return "Something went wrong.";
        }
        return message.trim();
    }
}