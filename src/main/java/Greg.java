import javax.sound.midi.SysexMessage;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Greg {
    private static final String LINE =
            "____________________________________________________________\n";

    public static void main(String[] args) {
        int MAX = 100;
        Scanner scanner = new Scanner(System.in);

        Storage storage = new Storage("data/greg.txt");
        ArrayList<Task> tasks = initializeTasks(storage);

        String myIntro = LINE +
                "Hello! I'm Greg\n" +
                "What can I do for you?\n" + LINE;

        String myOutro = LINE +
                " Bye. Hope to see you again soon!\n" +
                LINE;

        System.out.println(myIntro);


        while (true) {
            String line = scanner.nextLine().trim();
            System.out.println(LINE);
            try {
                if (line.equals("bye")) {
                    // To exit the program
                    saveTasks(storage, tasks);
                    System.out.println(myOutro);
                    scanner.close();
                    break;

                } else if (line.startsWith("delete ")) {
                    // To delete a task
                    int index = Integer.parseInt(line.split(" ")[1]) - 1;

                    if (index >= tasks.size() || index < 0) {
                        throw new GregException("Invalid task selected to delete.");
                    } else {
                        String deletedTask = tasks.get(index).toString();
                        tasks.remove(index);
                        saveTasks(storage, tasks);
                        System.out.println("Noted. I've removed this task: \n" + deletedTask + "\n" + "Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
                    }

                } else if (line.startsWith("mark ") || line.startsWith("unmark ")){
                    // To mark/unmark list items

                    // Minus one because list is 1 indexed, but array is 0 indexed
                    int index = Integer.parseInt(line.split(" ")[1]) - 1;

                    // Equal is also error because counter holds index for next task to be inserted
                    if (index >= tasks.size() || index < 0) {
                        throw new GregException("Invalid task selected to mark/unmark.");
                    } else if (line.startsWith("mark")) {
                        tasks.get(index).mark(true);
                        System.out.println("Nice! I've marked this task as done: \n" + tasks.get(index).toString() + "\n" + LINE);
                    } else {
                        tasks.get(index).mark(false);
                        System.out.println(" OK, I've marked this task as not done yet: \n" + tasks.get(index).toString() + "\n" + LINE);
                    }

                    // Save marked/unmarked task
                    saveTasks(storage, tasks);

                } else if (line.equals("list")){
                    // To list task items
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i).toString());
                    }
                    System.out.println("\n" + LINE);

                } else {
                    Task task = createTask(line);
                    tasks.add(task);
                    saveTasks(storage, tasks);

                    System.out.println("Got it. I've added this task: \n" + task.toString() + "\n" + "Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
                }
            } catch (GregException e) {
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }

    private static Task createTask(String input) throws GregException {

        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            return new Todo(description);
        }

        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim(); // "<desc> /by <date [time]>"
            String[] parts = rest.split("/by", 2);

            if (parts.length < 2) {
                throw new GregException("Invalid deadline format. Use: deadline <desc> /by yyyy-mm-dd [HHmm]");
            }

            String description = parts[0].trim();
            String byRaw = parts[1].trim();

            return new Deadline(description, byRaw);
        }

        if (input.startsWith("event ")) {
            String rest = input.substring(6).trim(); // "<desc> /from ... /to ..."

            String[] fromSplit = rest.split("/from", 2);
            if (fromSplit.length < 2) {
                throw new GregException("Invalid event format. Use: event <desc> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]");
            }

            String description = fromSplit[0].trim();

            String[] toSplit = fromSplit[1].trim().split("/to", 2);
            if (toSplit.length < 2) {
                throw new GregException("Invalid event format. Use: event <desc> /from yyyy-mm-dd [HHmm] /to yyyy-mm-dd [HHmm]");
            }

            String fromRaw = toSplit[0].trim();
            String toRaw = toSplit[1].trim();

            return new Event(description, fromRaw, toRaw);
        }

        throw new GregException("Invalid task. Must start with todo, deadline, or event.");
    }

    private static ArrayList<Task> initializeTasks(Storage storage) {
        try {
            return storage.loadAll();
        } catch (GregException e) {
            System.out.println("Warning: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveTasks(Storage storage, ArrayList<Task> tasks) {
        try {
            storage.saveAll(tasks);
        } catch (GregException e) {
            System.out.println("Warning: " + e.getMessage());
        }
    }
}
