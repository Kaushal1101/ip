import java.util.Scanner;

public class Greg {
    public static void main(String[] args) {
        int MAX = 100;

        String myIntro = " ____________________________________________________________\n" +
                " Hello! I'm Greg\n" +
                " What can I do for you?\n";

        String myOutro = "____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n";

        System.out.println(myIntro);

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX];
        int counter = 0;

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equals("bye")) {
                // To exit the program
                System.out.println(myOutro);
                scanner.close();
                break;
            } else if (line.startsWith("mark ") || line.startsWith("unmark ")){
                // To mark/unmark list items

                // Minus one because list is 1 indexed, but array is 0 indexed
                int index = Integer.parseInt(line.split(" ")[1]) - 1;
                if (index > counter || index < 0) {
                    System.out.println("Error: Invalid task selected to mark/unmark");
                } else if (line.startsWith("mark")) {
                    tasks[index].mark(true);
                    System.out.println("Nice! I've marked this task as done: \n" + tasks[index].toString() );
                } else {
                    tasks[index].mark(false);
                    System.out.println(" OK, I've marked this task as not done yet: \n" + tasks[index].toString() );
                }

            } else if (line.equals("list")){
                // To list task items
                int i = 0;
                while (tasks[i] != null) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                    i++;
                }
            } else {
                Task task = createTask(line);

                if (task == null) {
                    System.out.println("Error: Invalid task, must be either 'todo', 'event', or 'deadline' task.");
                } else {
                    tasks[counter] = task;
                    counter++;

                    System.out.println("Got it. I've added this task: \n" +
                            task.toString() +
                            "\n Now you have " + counter + " tasks in the list.");
                }
            }
        }
    }

    private static Task createTask(String input) {

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
            // Invalid case
            return null;
        }
    }
}
