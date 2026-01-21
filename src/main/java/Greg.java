import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.ArrayList;

public class Greg {
    private static final String LINE =
            "____________________________________________________________\n";
    public static void main(String[] args) {
        int MAX = 100;

        String myIntro = LINE +
                "Hello! I'm Greg\n" +
                "What can I do for you?\n" + LINE;

        String myOutro = LINE +
                " Bye. Hope to see you again soon!\n" +
                LINE;

        System.out.println(myIntro);

        Scanner scanner = new Scanner(System.in);

        // Task[] tasks = new Task[MAX];
        ArrayList<Task> tasks = new ArrayList<>();
        int counter = 0;

        while (true) {
            String line = scanner.nextLine().trim();
            System.out.println(LINE);
            try {
                if (line.equals("bye")) {
                    // To exit the program
                    System.out.println(myOutro);
                    scanner.close();
                    break;

                } else if (line.startsWith("delete ")) {
                    // To delete a task
                    int index = Integer.parseInt(line.split(" ")[1]) - 1;

                    if (index >= counter || index < 0) {
                        throw new GregException("Invalid task selected to mark/unmark.");
                    } else {
                        String deletedTask = tasks.get(index).toString();
                        tasks.remove(index);
                        counter--;
                        System.out.println("Noted. I've removed this task: \n" + deletedTask + "\n" + "Now you have " + counter + " tasks in the list.\n" + LINE);
                    }

                } else if (line.startsWith("mark ") || line.startsWith("unmark ")){
                    // To mark/unmark list items

                    // Minus one because list is 1 indexed, but array is 0 indexed
                    int index = Integer.parseInt(line.split(" ")[1]) - 1;

                    // Equal is also error because counter holds index for next task to be inserted
                    if (index >= counter || index < 0) {
                        throw new GregException("Invalid task selected to mark/unmark.");
                    } else if (line.startsWith("mark")) {
                        tasks.get(index).mark(true);
                        System.out.println("Nice! I've marked this task as done: \n" + tasks.get(index).toString() + "\n" + LINE);
                    } else {
                        tasks.get(index).mark(false);
                        System.out.println(" OK, I've marked this task as not done yet: \n" + tasks.get(index).toString() + "\n" + LINE);
                    }

                } else if (line.equals("list")){
                    // To list task items
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i).toString());
                    }
                    System.out.println("\n" + LINE);

                } else {
                    Task task = createTask(line);
                    tasks.add(task);
                    counter++;

                    System.out.println("Got it. I've added this task: \n" + task.toString() + "\n" + "Now you have " + counter + " tasks in the list.\n" + LINE);
                }
            } catch (GregException e) {
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }

    private static Task createTask(String input) throws GregException {

        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            String[] parts = rest.split("/by", 2);

            String description = parts[0].trim();
            String by = parts.length > 1 ? parts[1].trim() : "";

            return new Deadline(description, by);

        } else if (input.startsWith("event ")) {
            String rest = input.substring(6).trim();
            String[] parts = rest.split("/", 3);

            String description = parts[0].trim();

            String from = parts.length > 1 ? parts[1].trim() : "";
            if (from.startsWith("from")) {
                from = from.substring(4).trim(); // remove "from"
            }

            String to = parts.length > 2 ? parts[2].trim() : "";
            if (to.startsWith("to")) {
                to = to.substring(2).trim(); // remove "to"
            }

            return new Event(description, from, to);

        } else if (input.startsWith("todo ")) {
            // default: todo
            String description = input.startsWith("todo ")
                    ? input.substring(5).trim()
                    : input.trim();

            return new Todo(description);
        } else {
            throw new GregException("Invalid task, must be either 'todo', 'event', or 'deadline' task with a description");
        }
    }
}
