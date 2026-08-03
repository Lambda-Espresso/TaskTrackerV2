package main.java;

import java.util.ArrayList;

public class TaskStorage {
    String jsonFileName;
    TaskStorage(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    ArrayList<String> list;

    public void load() {}
    public void save() {}
}
