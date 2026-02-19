package greg.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import greg.exception.GregException;
import greg.model.Task;

/**
 * Handles loading tasks from disk and saving tasks to disk.
 * <p>
 * Tasks are stored line-by-line using each task's save-string representation.
 */
public class Storage {

    private final File file;

    /**
     * Creates a Storage instance backed by the given file path.
     *
     * @param filePath path to the save file
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves all tasks to disk, overwriting the existing file.
     *
     * @param tasks tasks to save
     * @throws GregException if the tasks cannot be written
     */
    public void saveAll(List<Task> tasks) throws GregException {
        ensureFileReady();
        writeTasks(tasks);
    }

    /**
     * Loads all tasks from disk.
     *
     * @return list of tasks loaded from file (possibly empty)
     * @throws GregException if the file cannot be read or is malformed
     */
    public List<Task> loadAll() throws GregException {
        ensureFileReady();
        return readTasks();
    }


    private void ensureFileReady() throws GregException {
        ensureParentDirectoryExists();
        ensureDataFileExists();
    }

    private void ensureParentDirectoryExists() throws GregException {
        File parent = file.getParentFile();
        if (parent == null) {
            return; // file is in current working directory
        }

        if (parent.exists()) {
            return;
        }

        if (!parent.mkdirs()) {
            throw new GregException("Could not create data directory: " + parent.getPath());
        }
    }

    private void ensureDataFileExists() throws GregException {
        if (file.exists()) {
            return;
        }

        try {
            if (!file.createNewFile()) {
                throw new GregException("Could not create data file: " + file.getPath());
            }
        } catch (IOException e) {
            throw new GregException("Could not create data file: " + file.getPath());
        }
    }

    private void writeTasks(List<Task> tasks) throws GregException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                pw.println(task.toSaveString());
            }
        } catch (IOException e) {
            throw new GregException("Failed to save tasks to file: " + file.getPath());
        }
    }

    private List<Task> readTasks() throws GregException {
        List<Task> tasks = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    tasks.add(Task.fromSaveString(line));
                }
            }
        } catch (IOException e) {
            throw new GregException("Failed to read tasks from file: " + file.getPath());
        }

        return tasks;
    }
}