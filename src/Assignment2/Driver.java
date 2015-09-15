package Assignment2;

public class Driver {

	public static void main(String[] args) {
		
		XMLParser parser = new XMLParser();
		
		//error occur because of XML &???
		System.out.println("Processsing...");
		
		parser.start();
		
		System.out.println("Done");
	}
}
