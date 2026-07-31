package main.java;

public class Main {
    public static void main(String[] args) {
        String jsonFilePath = "task.json";
        TaskStorage storage = new TaskStorage(jsonFilePath);
        TaskTracker task = new TaskTracker(storage);
        try {
            task.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}