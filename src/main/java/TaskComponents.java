package main.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskComponents {
    private int last_id = 0;
    private final int id;
    private String description;
    private Status status;
    private final String date;
    private String updatedDate;

    public String currentDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public TaskComponents(int id, String description, Status status, String date, String updatedDate){
        this.id = id;
        this.description = description;
        this.status = status;
        this.date = date;
        this.updatedDate = updatedDate;
    }
}
