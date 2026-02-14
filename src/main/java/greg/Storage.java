package greg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading tasks from disk and saving tasks to disk.
 * Tasks are stored line-by-line using each task's save-string representation.
 */
public class Storage {

    private static final String ERROR_SAVE_FAILED = "Failed to save tasks to file.";
    private static final String ERROR_LOAD_FAILED = "Failed to read tasks from file.";
    private static final String ERROR_CREATE_FILE = "Could not create data file.";

    private final File file;

    /**
     * Creates a storage handler that reads/writes tasks from the given file path.
     *
     * @param filePath Path to the save file.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves all tasks to the save file, overwriting any existing content.
     *
     * @param tasks Tasks to persist.
     * @throws GregException If the file cannot be created or written to.
     */
    public void saveAll(List<Task> tasks) throws GregException {
        ensureFileExists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.println(task.toSaveString());
            }
        } catch (IOException e) {
            throw new GregException(ERROR_SAVE_FAILED);
        }
    }

    /**
     * Loads all tasks from the save file.
     *
     * @return List of tasks loaded from disk.
     * @throws GregException If the file cannot be created or read, or if saved data is corrupted.
     */
    public List<Task> loadAll() throws GregException {
        ensureFileExists();

        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    tasks.add(Task.fromSaveString(line));
                }
            }
        } catch (IOException e) {
            throw new GregException(ERROR_LOAD_FAILED);
        }

        return tasks;
    }

    /**
     * Ensures the save file exists by creating missing parent directories and the file itself.
     *
     * @throws GregException If the file cannot be created due to I/O errors.
     */
    private void ensureFileExists() throws GregException {
        ensureParentDirectoryExists();
        ensureDataFileExists();
    }

    private void ensureParentDirectoryExists() throws GregException {
        File parent = file.getParentFile();
        if (parent == null || parent.exists()) {
            return;
        }

        boolean created = parent.mkdirs();
        if (!created && !parent.exists()) {
            throw new GregException(ERROR_CREATE_FILE);
        }
    }

    private void ensureDataFileExists() throws GregException {
        if (file.exists()) {
            return;
        }

        try {
            boolean created = file.createNewFile();
            if (!created && !file.exists()) {
                throw new GregException(ERROR_CREATE_FILE);
            }
        } catch (IOException e) {
            throw new GregException(ERROR_CREATE_FILE);
        }
    }
}