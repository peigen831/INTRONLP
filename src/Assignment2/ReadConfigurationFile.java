package Assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadConfigurationFile {
	
	private static final String FILENAME = "config.properties";
	private static Map<String, String> propertyMap;
	
	public static void main(String[] args) {
		System.out.println(getProperty("defaultPath"));
	}
	
	public static String getProperty(String property) {
		try {
			File file = new File(FILENAME);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			propertyMap = new HashMap<>();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				propertyMap.put(key, value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return propertyMap.get(property);
	}
	
}