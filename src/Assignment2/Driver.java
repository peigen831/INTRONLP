package Assignment2;

public class Driver {

	public static void main(String[] args) {
	 
		String filepath = "/Users/YongZhi/Desktop/NLP/Article/Opinyon/2001/April.xml";
		
		XMLParser parser = new XMLParser(filepath);

		parser.parseXML();
	  }
}
