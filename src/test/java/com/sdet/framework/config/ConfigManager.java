package com.sdet.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES = loadProperties();

    private ConfigManager() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream stream = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (stream == null) {
                throw new IllegalStateException("config.properties was not found on the classpath");
            }
            properties.load(stream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load API configuration", exception);
        }
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        if ("api.key".equals(key)) {
            String environmentValue = System.getenv("REQRES_API_KEY");
            if (environmentValue != null && !environmentValue.isBlank()) {
                return environmentValue;
            }
        }

        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration property: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
