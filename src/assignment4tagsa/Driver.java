package assignment4tagsa;

import java.io.FileNotFoundException;

import common.FileWriter;
import common.XmlReader;

public class Driver {
	
	private static String PACKAGENAME = "assignment4tagsa";
	
	public static void main(String[] args) {
		XmlReader reader = new XmlReader(PACKAGENAME, new TagsaParser());
		try {
			FileWriter.getNewInstance(PACKAGENAME, "Stemmered.txt").createNewFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Processsing...");
		
		reader.start();
		
		System.out.println("Done");
	}
}
