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
	
	private static final String CONFIGFILENAME = "config.properties";
	private static final String REGEXFILENAME = "regex.properties";
	
	public static String getProperty(String property) {
		Properties properties = readFile(CONFIGFILENAME);
		
		Enumeration enuKeys = properties.keys();
		Map<String, String> propertyMap = new HashMap<>();
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String value = properties.getProperty(key);
			propertyMap.put(key, value);
		}
		
		return propertyMap.get(property);
	}
	
	public static String getRegex(String regex) {
		Properties properties = readFile(REGEXFILENAME);
		
		Enumeration enuKeys = properties.keys();
		Map<String, String> regexMap = new HashMap<>();
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String value = properties.getProperty(key);
			regexMap.put(key, value);
		}
		
		return regexMap.get(regex);
	}
	
	private static Properties readFile(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			
			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}