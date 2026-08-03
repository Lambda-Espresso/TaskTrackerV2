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
    public void save() {}
}
