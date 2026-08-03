package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    final Path path;
    TaskStorage(String jsonFileName) {
        this.path = Path.of(jsonFileName);
    }

    JsonParser parser = new JsonParser();

    public List<TaskComponents> load() {
        String rawString = "";
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            rawString = Files.readString(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return parser.fromJson(rawString);
    }
    public void save(List<TaskComponents> taskList) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        int listSize = taskList.size();
        for (TaskComponents task : taskList) {
            if (!first) {
                sb.append(",");
            }
            sb.append("\n\t");
            sb.append(parser.toJson(task));
            first = false;
        }
        if(listSize > 0) sb.append("\n");
        sb.append("]");
        String jsonContent = sb.toString();
        try {
            Files.writeString(path, jsonContent);
        }
        catch (IOException e) {
            System.out.println("Cannot locate to JSON file");
        }
    }
}
