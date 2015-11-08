package assignment5ir;

import common.XmlReader;

public class Driver {
	
	private static String PACKAGENAME = "assignment5ir";
	
	public static void main(String[] args) {
		XmlReader reader = new XmlReader(PACKAGENAME, new IrParser());
		
		System.out.println("Processsing...");
		
		reader.start();
		
		System.out.println("Done");
	}
}
