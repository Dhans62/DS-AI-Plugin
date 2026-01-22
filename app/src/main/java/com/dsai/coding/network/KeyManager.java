package com.dsai.coding.network;

import java.util.HashMap;
import java.util.Map;

public class KeyManager {
    private static final Map<String, String[]> keyPool = new HashMap<>();
    private static final Map<String, Integer> currentIndex = new HashMap<>();

    static {
        // MASUKKAN KUNCI API LU DI SINI
        keyPool.put("GEMINI", new String[]{"KEY_1", "KEY_2"});
        keyPool.put("GLM", new String[]{"KEY_1"});
        keyPool.put("DEEPSEEK", new String[]{"KEY_1"});

        currentIndex.put("GEMINI", 0);
        currentIndex.put("GLM", 0);
        currentIndex.put("DEEPSEEK", 0);
    }

    public static String getKey(String model) {
        String[] keys = keyPool.get(model);
        int index = currentIndex.get(model);
        return keys[index];
    }

    public static void rotateKey(String model) {
        int nextIndex = currentIndex.get(model) + 1;
        if (nextIndex < keyPool.get(model).length) {
            currentIndex.put(model, nextIndex);
        }
    }
}
