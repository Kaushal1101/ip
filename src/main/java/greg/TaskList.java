package greg;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains the in-memory list of tasks and provides task operations.
 * <p>
 * Responsible for add/delete/mark/unmark/find operations and for validating indices.
 */
public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int oneIndexedIndex) throws GregException {
        int idx = toZeroIndex(oneIndexedIndex);
        return tasks.remove(idx);
    }

    public Task mark(int oneIndexedIndex) throws GregException {
        int idx = toZeroIndex(oneIndexedIndex);
        Task task = tasks.get(idx);
        task.mark(true);
        return task;
    }

    public Task unmark(int oneIndexedIndex) throws GregException {
        int idx = toZeroIndex(oneIndexedIndex);
        Task task = tasks.get(idx);
        task.mark(false);
        return task;
    }


    private int toZeroIndex(int oneIndexedIndex) throws GregException {
        int idx = oneIndexedIndex - 1;

        if (idx < 0 || idx >= tasks.size()) {
            throw new GregException("Invalid task number.");
        }

        return idx;
    }

    public List<Task> find(String query) {
        String[] terms = query.trim().toLowerCase().split("\\s+");
        List<Task> matches = new ArrayList<>();

        for (Task t : tasks) {
            String hay = t.getDescription().toLowerCase(); // you may need to add getter
            for (String term : terms) {
                if (!term.isEmpty() && hay.contains(term)) {
                    matches.add(t);
                    break; // OR-match: any term
                }
            }
        }
        return matches;
    }
}