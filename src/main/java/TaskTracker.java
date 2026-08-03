package main.java;

import java.util.List;
import java.util.Scanner;

public class TaskTracker {
    TaskStorage storage;
    TaskTracker(TaskStorage storage) {
        this.storage = storage;
    }
    public void run(String[] args) {
        List<TaskComponents> data = storage.load();
        TaskManager manager = new TaskManager(data);

        if(args == null || args[0].equals("help")){
            manager.help();
            return;
        }
        String command = args[0];
        switch (command) {
            case "add" -> {
                if (args.length != 2) {
                    System.out.println("Command: add <description>");
                    return;
                }
                manager.addTask(args[1]);
            }
            case "list" -> {
                if (args.length > 2) {
                    System.out.println("Command: list [id]");
                    return;
                }
                if (args.length == 1) {
                    manager.listAllTasks();
                    return;
                }
                Status filterStatus;
                try{
                    filterStatus = Status.valueOf(args[1].toUpperCase().replace("-","_"));
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Invalid status: '" + args[1]+"'");
                    System.out.println("Sufficient status is todo/in-progress/done");
                    return;
                }
                manager.listTasks(filterStatus);
                return;
            }
            case "update" -> {
                if (args.length != 3) {
                    System.out.println("Command: update [id] <description>");
                    return;
                }
                int id = isInteger(args[1]);
                if(id == -1) return;
                manager.updateTask(id, args[2]);
            }
            case "mark-in-progress" -> {
                if (args.length != 2) {
                    System.out.println("Command: mark-in-progress [id]");
                    return;
                }
                int id = isInteger(args[1]);
                if(id == -1) return;
                manager.markInProgressTask(id);
            }
            case "mark-done" -> {
                if (args.length != 2) {
                    System.out.println("Command: mark-done [id]");
                    return;
                }
                int id = isInteger(args[1]);
                if(id == -1) return;
                manager.markDoneTask(id);
            }
            case "delete" -> {
                if (args.length > 2){
                    System.out.println("Command: delete [id]");
                    return;
                }
                if (args.length == 1){
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Confirm to clear all tasks [Y/n]: ");
                    String confirm = scanner.nextLine().toLowerCase();
                    if(confirm.equals("y")) manager.deleteAllTask();
                    else{
                        scanner.close();
                        return;
                    }
                }
                else{
                    int id = isInteger(args[1]);
                    if(id == -1) return;
                    manager.deleteTask(id);
                }
            }
            default -> System.out.println("Unknown command: " + command);
        }
        data = manager.getData();
        storage.save(data);
    }
    public static int isInteger(String str){
        int filterId;
        try{
            filterId = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            System.out.println("The ID has to be an integer");
            return -1;
        }
        return filterId;
    }
}
