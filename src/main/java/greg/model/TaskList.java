package greg.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greg.exception.GregException;

/**
 * Maintains the in-memory list of tasks and provides task operations.
 * <p>
 * Responsible for add/delete/mark/unmark/find operations and for validating indices.
 */
public class TaskList {

    private static final String ERROR_INVALID_TASK_NUMBER = "Invalid task number.";

    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param tasks initial tasks (non-null)
     */
    public TaskList(List<Task> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("tasks must not be null");
        }
        this.tasks = new ArrayList<>(tasks); // defensive copy
    }

    /**
     * Returns number of tasks in the list.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns an unmodifiable view of tasks to prevent external mutation.
     *
     * @return unmodifiable list of tasks
     */
    public List<Task> getAll() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add (non-null)
     */
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("task must not be null");
        }
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the given 1-indexed position.
     *
     * @param oneIndexedIndex 1-indexed task number
     * @return deleted task
     * @throws GregException if index is invalid
     */
    public Task delete(int oneIndexedIndex) throws GregException {
        return tasks.remove(toZeroIndex(oneIndexedIndex));
    }

    /**
     * Marks the task at the given 1-indexed position as done.
     *
     * @param oneIndexedIndex 1-indexed task number
     * @return the updated task
     * @throws GregException if index is invalid
     */
    public Task mark(int oneIndexedIndex) throws GregException {
        return setMarked(oneIndexedIndex, true);
    }

    /**
     * Marks the task at the given 1-indexed position as not done.
     *
     * @param oneIndexedIndex 1-indexed task number
     * @return the updated task
     * @throws GregException if index is invalid
     */
    public Task unmark(int oneIndexedIndex) throws GregException {
        return setMarked(oneIndexedIndex, false);
    }

    /**
     * Finds tasks whose description contains ANY word in the query (case-insensitive).
     *
     * @param query search query
     * @return list of matching tasks (possibly empty)
     */
    public List<Task> find(String query) {
        String[] terms = normalizeQuery(query);
        List<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            if (matchesAnyTerm(task, terms)) {
                matches.add(task);
            }
        }
        return matches;
    }

    private Task setMarked(int oneIndexedIndex, boolean marked) throws GregException {
        Task task = tasks.get(toZeroIndex(oneIndexedIndex));
        task.mark(marked);
        return task;
    }

    private int toZeroIndex(int oneIndexedIndex) throws GregException {
        int idx = oneIndexedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new GregException(ERROR_INVALID_TASK_NUMBER);
        }
        return idx;
    }

    private String[] normalizeQuery(String query) {
        if (query == null) {
            return new String[0];
        }
        String trimmed = query.trim().toLowerCase();
        return trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");
    }

    private boolean matchesAnyTerm(Task task, String[] terms) {
        if (terms.length == 0) {
            return false;
        }

        String haystack = task.getDescription().toLowerCase();
        for (String term : terms) {
            if (!term.isEmpty() && haystack.contains(term)) {
                return true;
            }
        }
        return false;
    }
}