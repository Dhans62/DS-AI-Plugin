package com.dsai.coding.network;

public class ResponseParser {
    public static String extract(String rawJson, String modelType) {
        try {
            String targetKey = modelType.equals("GEMINI") ? "\"text\":\"" : "\"content\":\"";
            int start = rawJson.indexOf(targetKey);
            if (start == -1) return "Error: Response format invalid";
            
            start += targetKey.length();
            int end = rawJson.indexOf("\"", start);
            
            return rawJson.substring(start, end)
                         .replace("\\n", "\n")
                         .replace("\\\"", "\"")
                         .replace("\\\\", "\\");
        } catch (Exception e) {
            return "Parsing Error: " + e.getMessage();
        }
    }
}
