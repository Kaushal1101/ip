package greg;

import java.util.List;
import java.util.Scanner;


/**
 * Handles all user-facing output for the Greg task manager.
 * <p>
 * This class is responsible only for formatting and printing messages, and does not
 * contain command logic or data storage logic.
 */
public class Ui {
    private static final String LINE =
            "____________________________________________________________\n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public void showLine() {
        System.out.print(LINE);
    }

    public void showWelcome() {
        System.out.print(LINE);
        System.out.println("Hello! I'm Greg");
        System.out.println("What can I do for you?");
        System.out.print(LINE);
    }

    public void showGoodbye() {
        System.out.print(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.print(LINE);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task: ");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.print(LINE);
    }

    public void showTaskDeleted(String deletedTask, int taskCount) {
        System.out.println("Noted. I've removed this task: ");
        System.out.println(deletedTask);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.print(LINE);
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done: ");
        System.out.println(task.toString());
        System.out.print(LINE);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet: ");
        System.out.println(task.toString());
        System.out.print(LINE);
    }

    public void showTaskList(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.println();
        System.out.print(LINE);
    }

    public void showFindResults(List<Task> matches) {
        System.out.println(LINE);
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
        System.out.println(LINE);
    }


    public void showError(String message) {
        System.out.println(message);
        System.out.print(LINE);
    }

    public void showWarning(String message) {
        System.out.println("Warning: " + message);
    }
}