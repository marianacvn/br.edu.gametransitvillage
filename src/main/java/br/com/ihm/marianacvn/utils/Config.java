package br.com.ihm.marianacvn.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final String CONFIG_FILE = "src/main/resources/assets/files/config.json";
    private static Map<String, Object> configValues = new HashMap<>();

    public static Object getConfigValue(String key) {
        return configValues.get(key);
    }

    public static void setConfigValue(String key, Object value) {
        configValues.put(key, value);
    }


    public static void setConfigValueAndSave(String key, Object value) {
        configValues.put(key, value);
        try {
            Config.save();
        } catch (IOException e) {
            ErrorHandler.logAndExit(e);
        }
    }

    public static void load() throws IOException {
        Path configPath = Paths.get(CONFIG_FILE);
        if (!Files.exists(configPath)) {
            Files.createDirectories(configPath.getParent());
            Files.createFile(configPath);
            // Set default volume
            configValues.put("volume", 0.5);
            configValues.put("music", true);
            save();
        }
        String content = new String(Files.readAllBytes(configPath));
        JSONObject json = new JSONObject(content);
        for (String key : json.keySet()) {
            configValues.put(key, json.get(key));
        }
    }

    public static void save() throws IOException {
        JSONObject json = new JSONObject(configValues);
        Files.write(Paths.get(CONFIG_FILE), json.toString().getBytes());
    }
}