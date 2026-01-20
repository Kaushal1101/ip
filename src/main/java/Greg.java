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

        String[] tasks = new String[MAX];
        int counter = 0;

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equals("bye")) {
                System.out.println(myOutro);
                scanner.close();
                break;
            } else if (line.equals("list")){
                int i = 0;
                while (tasks[i] != null) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                    i++;
                }
            } else {
                tasks[counter] = line;
                counter++;
                System.out.println("Greg added: " + line);
            }
        }
    }
}
