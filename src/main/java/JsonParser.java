package main.java;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public List<TaskComponents> fromJson(String rawString) {
        String jsonString = rawString.strip();
        List<String> jsonTokens = splitJsonArray(jsonString);
        if (jsonTokens.isEmpty()) return new ArrayList<>();

        List<TaskComponents> previousData = new ArrayList<>();
        for (String jsonToken : jsonTokens) {
            previousData.add(taskComponentsParse(jsonToken));
        }
        return previousData;
    }
    public void toJson() {

    }
    private List<String> splitJsonArray(String rawString) {
        List<String> chunks = new ArrayList<>();

        int braceCount = 0;
        boolean inQuotes = false;
        boolean isEscaped = false;
        StringBuilder currentChunk = new StringBuilder();

        for (int i = 1; i < rawString.length() - 1; i++) {
            char c = rawString.charAt(i);

            if (isEscaped) {
                if (braceCount > 0) currentChunk.append(c);
                isEscaped = false;
                continue;
            }
            if (c == '\\') {
                if (braceCount > 0) currentChunk.append(c);
                isEscaped = true;
                continue;
            }

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                }
                if (braceCount > 0) {
                    currentChunk.append(c);
                }
                if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        chunks.add(currentChunk.toString());
                        currentChunk.setLength(0);
                    }
                }
            } else {
                if (braceCount > 0) {
                    currentChunk.append(c);
                }
            }
        }
        return chunks;
    }
    private TaskComponents taskComponentsParse(String jsonToken) {
        String[] taskComponents = jsonToken.split(",");
        String idString = taskComponents[0].split(":")[1].strip();
        String description = taskComponents[1].split(":", 2)[1].strip();
        String statusString = taskComponents[2].split(":")[1].strip();
        String createdAtString = taskComponents[3].split("[a-z]:", 2)[1].strip();
        String updatedAtString = taskComponents[4].split("[a-z]:", 2)[1].strip();
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
