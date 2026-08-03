package main.java;

import java.util.List;

public class TaskTracker {
    TaskStorage storage;
    TaskTracker(TaskStorage storage) {
        this.storage = storage;
    }
    public void run(String[] args) {
        List<TaskComponents> data = storage.load();
        TaskManager manager = new TaskManager(data);

        String command = args[0];
        if (command.isEmpty()) {

        }
        switch(command) {

        }

        storage.save();
    }
}
