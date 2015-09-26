package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileWriter {
	
	private static FileWriter INSTANCE;
	
	public static FileWriter getInstance() {
		if (INSTANCE == null) {
			return getNewInstance();
		}
		return INSTANCE;
	}
	
	public static FileWriter getNewInstance() {
		return INSTANCE = new FileWriter();
	}
	
	public static FileWriter getNewInstance(String packageName, String fileName) {
		return INSTANCE = new FileWriter(packageName, fileName);
	}
	
	private String path;
	private String fileName;
	
	private FileWriter() {
		path = null;
		fileName = null;
	}
	
	private FileWriter(String packageName, String fileName) {
		path = "src/" + packageName;
		this.fileName = fileName;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setPackageName(String packageName) {
		path = "src/" + packageName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void createNewFile() throws FileNotFoundException {
		File file = new File(path + "/" + fileName);
		System.out.println(path + "/" + fileName);
		if (file.exists()) {
			file.delete();
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
		pw.write("");
		pw.close();
	}
	
	public void writeLine(String text) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File(path + "/" + fileName), true));
		pw.write(text + "\n");
		pw.close();
	}
}
