package greg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maintains the in-memory list of tasks and provides task operations.
 * Responsible for adding, deleting, marking, unmarking and searching tasks.
 */
public class TaskList {

    private static final String ERROR_INVALID_TASK_NUMBER = "Invalid task number.";

    private final List<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialized with an existing list of tasks.
     *
     * @param tasks Initial tasks to populate this TaskList.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return Total number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns an unmodifiable view of all tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getAll() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Adds a new task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified 1-indexed position.
     *
     * @param oneIndexedIndex 1-indexed task number.
     * @return The removed task.
     * @throws GregException If the task number is invalid.
     */
    public Task delete(int oneIndexedIndex) throws GregException {
        int idx = toZeroIndex(oneIndexedIndex);
        return tasks.remove(idx);
    }

    /**
     * Marks the specified task as done.
     *
     * @param oneIndexedIndex 1-indexed task number.
     * @return The updated task.
     * @throws GregException If the task number is invalid.
     */
    public Task mark(int oneIndexedIndex) throws GregException {
        return setMarked(oneIndexedIndex, true);
    }

    /**
     * Marks the specified task as not done.
     *
     * @param oneIndexedIndex 1-indexed task number.
     * @return The updated task.
     * @throws GregException If the task number is invalid.
     */
    public Task unmark(int oneIndexedIndex) throws GregException {
        return setMarked(oneIndexedIndex, false);
    }

    /**
     * Finds tasks whose descriptions contain any of the given search terms.
     * Matching is case-insensitive.
     *
     * @param query Search string containing one or more keywords.
     * @return List of matching tasks.
     */
    public List<Task> find(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] terms = query.toLowerCase().trim().split("\\s+");
        List<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            String haystack = task.getDescription().toLowerCase();
            if (containsAnyTerm(haystack, terms)) {
                matches.add(task);
            }
        }
        return matches;
    }


    private Task setMarked(int oneIndexedIndex, boolean isMarked) throws GregException {
        Task task = getTask(oneIndexedIndex);
        task.mark(isMarked);
        return task;
    }

    private Task getTask(int oneIndexedIndex) throws GregException {
        int idx = toZeroIndex(oneIndexedIndex);
        return tasks.get(idx);
    }

    private int toZeroIndex(int oneIndexedIndex) throws GregException {
        int idx = oneIndexedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new GregException(ERROR_INVALID_TASK_NUMBER);
        }
        return idx;
    }

    private boolean containsAnyTerm(String haystack, String[] terms) {
        for (String term : terms) {
            if (!term.isEmpty() && haystack.contains(term)) {
                return true;
            }
        }
        return false;
    }
}