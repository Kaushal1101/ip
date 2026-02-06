package greg;

import java.util.List;

/**
 * Provides user-facing messages for the Greg task manager.
 * <p>
 * In the JavaFX version, this class does not read input or print output.
 * It only formats messages and returns them as strings for the UI layer to display.
 */
public class Ui {

    private static final String LINE =
            "____________________________________________________________";

    public String getWelcome() {
        return "Hello! I'm Greg\n"
                + "What can I do for you?";
    }

    public String getGoodbye() {
        return LINE + "\n"
                + "Bye. Hope to see you again soon!\n"
                + LINE;
    }

    public String getTaskAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    public String getTaskDeleted(Task deletedTask, int taskCount) {
        return "Noted. I've removed this task:\n"
                + deletedTask + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    public String getTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + task;
    }

    public String getTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + task;
    }

    public String getTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    public String getFindResults(List<Task> matches) {
        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    public String getError(String message) {
        return message;
    }

    public String getWarning(String message) {
        return "Warning: " + message;
    }
}