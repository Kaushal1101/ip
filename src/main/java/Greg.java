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
                if (line.startsWith("mark")) {
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
                // To add task
                tasks[counter] = new Task(line);
                counter++;
                System.out.println("Greg added: " + line);
            }
        }
    }
}
