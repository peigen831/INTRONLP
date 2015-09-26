package common;

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
	private static String PACKAGENAME = null;
	
	public static void setPackage(String packageName) {
		PACKAGENAME = packageName;
	}
	
	public static String getProperty(String property) {
		return getProperty(PACKAGENAME, property);
	}
	
	public static String getProperty(String packageName, String property) {
		Properties properties = readFile(packageName, CONFIGFILENAME);
		
		Enumeration<Object> enuKeys = properties.keys();
		Map<String, String> propertyMap = new HashMap<>();
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String value = properties.getProperty(key);
			propertyMap.put(key, value);
		}
		
		return propertyMap.get(property);
	}
	
	private static Properties readFile(String packageName, String fileName) {
		try {
			File file = new File("src/" + packageName + "/" + fileName);
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