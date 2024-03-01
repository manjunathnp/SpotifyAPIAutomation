package com.spotify.oath2.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath) throws FileNotFoundException {
        Properties properties = new Properties();
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(filePath));
        try {
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to load properties file "+ filePath);
        }
        return properties;
    }
}
