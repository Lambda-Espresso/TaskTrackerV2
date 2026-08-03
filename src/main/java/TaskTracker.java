package main.java;

public class TaskTracker {
    TaskStorage storage;
    TaskTracker(TaskStorage storage) {
        this.storage = storage;
    }
    public void run() {
        storage.load();

        storage.save();
    }
}
