import java.util.*;

public class JsonParser {
    public List<TaskComponents> fromJson(String rawString) {
        return splitJson(rawString);
    }
    public String toJson(TaskComponents task) {
        return String.format("{\"id\": %d, \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}",
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedDate(),
                task.getUpdatedDate());
    }
    private List<TaskComponents> splitJson(String rawString) {
        Deque<String> jsonToken = new ArrayDeque<>();
        List<TaskComponents> previousData = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean isEscapeSequence = false;
        boolean isValue = false;
        boolean inQuote = false;
        for (char c : rawString.toCharArray()) {
            if (isValue) {
                if (c == '"') {
                    inQuote = !inQuote;
                    continue;
                }
                if (inQuote) {
                    if (c == '\\') {
                        isEscapeSequence = true;
                        continue;
                    }
                    if (isEscapeSequence) {
                        isEscapeSequence = false;
                        continue;
                    }
                    sb.append(c);
                } else if (c >= '0' && c <= '9') {
                    sb.append(c);
                } else {
                    if (c == ',' || c == '}') {
                        isValue = false;
                        jsonToken.add(sb.toString());
                        sb.setLength(0);
                        if (c == '}') previousData.add(taskComponentsParse(jsonToken));
                    }
                }
            } else {
                if (c == ':') {
                    isValue = true;
                }
            }
        }
        return previousData;
    }
    private TaskComponents taskComponentsParse(Deque<String> jsonToken) {
        String idString = "";
        String description = "";
        String statusString = "";
        String createdAtString = "";
        String updatedAtString = "";
        try {
            idString = jsonToken.remove();
            description = jsonToken.remove();
            statusString = jsonToken.remove();
            createdAtString = jsonToken.remove();
            updatedAtString = jsonToken.remove();
        } catch (NoSuchElementException e) {
            System.out.println("Failed to load JSON file");
            System.out.println("Missing data");
        }
        int validId = 0;
        try{
            validId = Integer.parseInt(idString);
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
        Status validStatus = Status.valueOf(statusType);
        return new TaskComponents(validId, description, validStatus, createdAtString, updatedAtString);
    }
}
