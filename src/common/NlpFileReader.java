package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class NlpFileReader extends Thread {
	
	private Parser parser;
	private String defaultPath;
	private static String currentPath = null;
	private Object notifier;
	
	public NlpFileReader() {
		parser = null;
		defaultPath = null;
	}
	
	public NlpFileReader(Parser parser) {
		this.parser = parser;
		defaultPath = null;
	}
	
	public NlpFileReader(String packageName) {
		this.parser = null;
		defaultPath = ReadConfigurationFile.getProperty(packageName, "defaultPath");
	}
	
	public NlpFileReader(String packageName, Parser parser) {
		this.parser = parser;
		defaultPath = ReadConfigurationFile.getProperty(packageName, "defaultPath");
	}
	
	public NlpFileReader(Parser parser, String defaultPath) {
		this.parser = parser;
		this.defaultPath = defaultPath;
	}
	
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	
	public void setPackageName(String packageName) {
		defaultPath = ReadConfigurationFile.getProperty(packageName, "defaultPath");
	}
	
	public static String getCurrentFilepath() {
		return currentPath;
	}
	
	public void run() {
		Collection<File> files = getAllFiles(defaultPath);
		
		for (File file : files) {
			String body = "";
			String currentLine = null;
			
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				while ((currentLine = reader.readLine()) != null) {
					body += currentLine + " ";
				}
				//System.out.println(body);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			currentPath = file.getAbsolutePath();
			parser.parse(body);
			System.out.println("done " + file.getName());
		}
		
		done();
	}
	
	public void setNotifier(Object notifier) {
		this.notifier = notifier;
	}
	
	private void done() {
		if (notifier == null) {
			return;
		}
		
		synchronized(notifier) {
			notifier.notify();
		}
	}
	
	private Collection<File> getAllFiles(String filePath) {
		Collection<File> files = null;
		try {
			files = FileUtils.listFiles(
			    new File(filePath),
			    new RegexFileFilter("^(.*(txt))"),
			    DirectoryFileFilter.DIRECTORY
			);
		} catch (Exception e) {
			files = new ArrayList<File>();
			if (filePath.endsWith(".txt")) {
				files.add(new File(filePath));
			}
		}
		return files;
	}
	
}
