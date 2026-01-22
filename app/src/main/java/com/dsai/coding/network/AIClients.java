package com.dsai.coding.network;

import com.vcspace.plugins.PluginContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AIClients {
    
    public interface Callback {
        void onSuccess(String result);
        void onError(String error);
    }

    public static void request(PluginContext ctx, String modelType, String prompt, Callback callback) {
        new Thread(() -> {
            try {
                String apiKey = KeyManager.getKey(modelType);
                String endpoint = getUrl(modelType, apiKey);
                
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                
                // Header Authorization untuk GLM dan DeepSeek
                if (!modelType.equals("GEMINI")) {
                    conn.setRequestProperty("Authorization", "Bearer " + apiKey);
                }

                conn.setDoOutput(true);
                String body = createBody(modelType, prompt);
                
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body.getBytes(StandardCharsets.UTF_8));
                }

                int code = conn.getResponseCode();
                
                // Auto-rotate jika Limit (429)
                if (code == 429) {
                    KeyManager.rotateKey(modelType);
                    request(ctx, modelType, prompt, callback);
                    return;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                    code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream()
                ));
                
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) res.append(line);
                
                if (code >= 200 && code < 300) {
                    callback.onSuccess(res.toString());
                } else {
                    callback.onError("HTTP " + code + ": " + res.toString());
                }

            } catch (Exception e) {
                callback.onError("Connection Error: " + e.getMessage());
            }
        }).start();
    }

    private static String getUrl(String modelType, String key) {
        if (modelType.equals("GEMINI")) {
            return "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=" + key;
        }
        if (modelType.equals("DEEPSEEK")) {
            return "https://api.deepseek.com/chat/completions";
        }
        return "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    }

    private static String createBody(String modelType, String prompt) {
        // Melakukan escape pada karakter berbahaya agar JSON tidak rusak
        String safePrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n");

        if (modelType.equals("GEMINI")) {
            return "{\"contents\":[{\"parts\":[{\"text\":\"" + safePrompt + "\"}]}]}";
        }
        
        String modelId;
        if (modelType.equals("GLM")) modelId = "glm-4.7";
        else if (modelType.equals("DEEPSEEK")) modelId = "Deepseek-V3.2-speciale";
        else modelId = modelType;

        return "{\"model\":\"" + modelId + "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + safePrompt + "\"}]}";
    }
}
