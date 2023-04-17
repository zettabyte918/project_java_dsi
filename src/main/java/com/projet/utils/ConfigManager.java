package com.projet.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "/com/projet/config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            // Load the properties file
            InputStream inputStream = ConfigManager.class.getResourceAsStream(CONFIG_FILE);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDbName() {
        return getProperty("db.name");
    }

    public static String getDbUrl() {
        return getProperty("db.url");
    }

    public static String getDbUsername() {
        return getProperty("db.username");
    }

    public static String getDbPassword() {
        return getProperty("db.password");
    }

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
