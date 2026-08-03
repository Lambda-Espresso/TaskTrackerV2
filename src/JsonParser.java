import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public List<TaskComponents> fromJson(String rawString) {
        String jsonString = rawString.strip();
        List<String> jsonTokens = splitJson(jsonString);

        List<TaskComponents> previousData = new ArrayList<>();
        for (String jsonToken : jsonTokens) {
            previousData.add(taskComponentsParse(jsonToken));
        }
        return previousData;
    }
    public String toJson(TaskComponents task) {
        return String.format("{\"id\": %d, \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}",
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedDate(),
                task.getUpdatedDate());
    }
    private List<String> splitJson(String rawString) {
        if (rawString.equals("[]") || rawString.isEmpty()) return new ArrayList<>();
        List<String> jsonTokens = new ArrayList<>();
        String[] jsonComponents = rawString
                .replace("[","")
                .replace("]","")
                .split("},");
        for (String json : jsonComponents){
            if (!json.endsWith("}")){
                json = json + "}";
            }
            jsonTokens.add(json);
        }
        return jsonTokens;
    }
    private TaskComponents taskComponentsParse(String jsonToken) {
        jsonToken = jsonToken
                .replace("{","")
                .replace("}","")
                .replace("\"","");
        String[] taskComponents = jsonToken.split(",");
        String idString = taskComponents[0].split(":")[1].strip();
        String description = taskComponents[1].split(":", 2)[1].strip();
        String statusString = taskComponents[2].split(":")[1].strip();
        String createdAtString = taskComponents[3].split(":", 2)[1].strip();
        String updatedAtString = taskComponents[4].split(":", 2)[1].strip();
        int qualifiedId = 0;
        try{
            qualifiedId = Integer.parseInt(idString);
        }
        catch (NumberFormatException e) {
            System.out.println("Failed to load JSON file");
            System.out.println("Unknown ID:" + idString);
            System.exit(1);
        }
        String statusType = statusString.toUpperCase();
        if(!(statusType.equals("TODO") || statusType.equals("IN_PROGRESS") || statusType.equals("DONE"))){
            System.out.println("Failed to load JSON file");
            System.out.println("Unknown status:" + statusString);
            System.exit(1);
        }
        Status qualifiedStatus = Status.valueOf(statusType);
        return new TaskComponents(qualifiedId, description, qualifiedStatus, createdAtString, updatedAtString);
    }
}
