import java.util.Scanner;

public class Greg {
    public static void main(String[] args) {
        String myIntro = " ____________________________________________________________\n" +
                " Hello! I'm Greg\n" +
                " What can I do for you?\n";

        String myOutro = "____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n";

        System.out.println(myIntro);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine().trim();

            if (line.equals("bye")) {
                System.out.println(myOutro);
                scanner.close();
                break;
            } else {
                System.out.println("Greg says " + line);
            }
        }
    }
}
