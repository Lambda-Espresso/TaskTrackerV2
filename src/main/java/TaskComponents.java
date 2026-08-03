package main.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskComponents {
    private final int id;
    private String description;
    private Status status;
    private final String createdDate;
    private String updatedDate;

    public String currentDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    TaskComponents(int id,String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdDate = currentDate();
        this.updatedDate = createdDate;
    }
    TaskComponents(int id, String description, Status status, String date, String updatedDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdDate = date;
        this.updatedDate = updatedDate;
    }

    public int getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public Status getStatus(){
        return status;
    }
    public String getCreatedDate(){
        return createdDate;
    }
    public String getUpdatedDate(){
        return updatedDate;
    }
    public void updateDescription(String description){
        this.description = description;
        updatedDate = currentDate();
    }
    public void markInProgress(){
        this.status = Status.IN_PROGRESS;
        updatedDate = currentDate();
    }
    public void markDone(){
        this.status = Status.DONE;
        updatedDate = currentDate();
    }
    @Override
    public String toString(){
        return String.format("""
                ID : %d,
                 - Description: %s,
                 - Status: %s,
                 - Created at: %s,
                 - Updated at: %s""", id, description, status, createdDate, updatedDate);
    }
}
