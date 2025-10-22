package com.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Readconfig {

    private static final Map<String, Properties> cache = new HashMap<>();

    public static String readPropertyFileData(String keyName, String fileName) {
        try {
            Properties properties = cache.get(fileName);
            if (properties == null) {
                properties = new Properties();
                try (FileReader fileReader = new FileReader("./configuration/" + fileName + ".properties")) {
                    properties.load(fileReader);
                }
                cache.put(fileName, properties);
            }
            String value = properties.getProperty(keyName);
            if (value == null) {
                throw new RuntimeException("Key '" + keyName + "' not found in " + fileName + ".properties");
            }
            return value;
        } catch (IOException e) {
            throw new RuntimeException("Error reading the config file: " + fileName + ".properties", e);
        }
    }
}
