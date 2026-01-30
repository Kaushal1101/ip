import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void saveAll(List<Task> tasks) throws GregException {
        ensureFileExists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                pw.println(task.toSaveString());
            }
        } catch (IOException e) {
            throw new GregException("Failed to save tasks to file.");
        }
    }

    public List<Task> loadAll() throws GregException {
        ensureFileExists();

        List<Task> tasks = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    tasks.add(Task.fromSaveString(line));
                }
            }
        } catch (IOException e) {
            throw new GregException("Failed to read tasks from file.");
        }

        return tasks;
    }

    private void ensureFileExists() throws GregException {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new GregException("Could not create data file.");
        }
    }
}