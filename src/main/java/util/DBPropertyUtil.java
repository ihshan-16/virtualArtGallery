package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis); 
        } catch (IOException e){
            System.err.println("Error loading properties file: " + fileName);
            e.printStackTrace();
        }
        return properties;
    }
}

