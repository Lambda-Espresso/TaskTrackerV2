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
            System.out.println("Failed to load JSON file");
            System.out.println("Cannot read the file");
        }
        return parser.fromJson(rawString);
    }
    public void save(List<TaskComponents> taskList) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (TaskComponents task : taskList) {
            if (!first) {
                sb.append(",");
            }
            sb.append("\n");
            sb.append(parser.toJson(task));
            first = false;
        }
        if (!taskList.isEmpty()) sb.append("\n");
        sb.append("]");
        String jsonContent = sb.toString();
        try {
            Files.writeString(path, jsonContent);
        }
        catch (IOException e) {
            System.out.println("Cannot locate to JSON file");
            System.out.println("Cannot write the file");
        }
    }
}
