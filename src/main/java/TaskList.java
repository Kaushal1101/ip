import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private final List<Task> tasks;

    /* ========== CONSTRUCTORS ========== */

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /* ========== BASIC QUERIES ========== */

    public int size() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return tasks;
    }

    /* ========== MUTATIONS ========== */

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

    /* ========== INTERNAL HELPERS ========== */

    private int toZeroIndex(int oneIndexedIndex) throws GregException {
        int idx = oneIndexedIndex - 1;

        if (idx < 0 || idx >= tasks.size()) {
            throw new GregException("Invalid task number.");
        }

        return idx;
    }
}