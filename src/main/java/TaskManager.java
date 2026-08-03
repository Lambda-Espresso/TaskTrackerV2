package main.java;

import java.util.List;
import java.util.Optional;

public class TaskManager {
    List<TaskComponents> taskList;
    int last_id;
    JsonParser parser = new JsonParser();

    TaskManager(List<TaskComponents> data) {
        this.taskList = data;
        this.last_id = syncLastId(data);
    }

    public void help(){
        System.out.println("Add              - 'add <description>'");
        System.out.println("List             - 'list [status]'");
        System.out.println("Update           - 'update [id] <description>'");
        System.out.println("Mark in-progress - 'mark-in-progress [id]'");
        System.out.println("Mark done        - 'mark-done [id]'");
        System.out.println("Delete           - 'delete [id]'");
    }
    public void addTask(int last_id, String description) {
        taskList.add(new TaskComponents(last_id, description));
        System.out.println("Task added: " + description);
    }
    public void listAllTasks(){
        if(listIsEmpty()) return;
        taskList.forEach(task -> System.out.println(task.toString()));
    }
    public void listTasks(Status status) {
        if(listIsEmpty()) return;
        List<TaskComponents> filtered = taskList.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
        if (filtered.isEmpty()){
            System.out.println("There is no task with status '" + status + "'");
            return;
        }
        filtered.forEach(System.out::println);
    }
    public void updateTask(int id, String description) {
        if (listIsEmpty()) return;
        Optional<TaskComponents> filterList = findId(id);
        if(filterList.isEmpty()){
            System.out.println("There is no task with ID " + id);
            return;
        }
        TaskComponents task = filterList.get();
        task.updateDescription(description);
        System.out.println("Task updated: " + description);
    }
    public void markInProgressTask(int id){
        if (listIsEmpty()) return;
        Optional<TaskComponents> filterList = findId(id);
        if(filterList.isEmpty()){
            System.out.println("There is no task with ID " + id);
            return;
        }
        TaskComponents task = filterList.get();
        task.markInProgress();
        System.out.println("Task marked as in-progress (ID: " + id +")");
    }
    public void markDoneTask(int id){
        if (listIsEmpty()) return;
        Optional<TaskComponents> filterList = findId(id);
        if(filterList.isEmpty()){
            System.out.println("There is no task with ID " + id);
            return;
        }
        TaskComponents task = filterList.get();
        task.markDone();
        System.out.println("Task marked as done (ID: " + id +")");
    }
    public void deleteAllTask() {
        taskList.clear();
        System.out.println("All tasks deleted");
    }
    public void deleteTask(int id) {
        if(listIsEmpty()) return;
        boolean removable = taskList.removeIf(task -> task.getId() == id);
        if (!removable) {
            System.out.println("There is no task with ID " + id);
            return;
        }
        System.out.println("Task deleted (ID: " + id +")");
    }
    private boolean listIsEmpty(){
        if (taskList.isEmpty()) {
            System.out.println("There is no remaining task");
            return true;
        }
        return false;
    }
    private Optional<TaskComponents> findId(int id) {
        return taskList.stream().filter((task) -> task.getId() == id).findFirst();
    }
    private int syncLastId(List<TaskComponents> data){
        return data.stream()
                .mapToInt(TaskComponents::getId)
                .max()
                .orElse(0);
    }
}
