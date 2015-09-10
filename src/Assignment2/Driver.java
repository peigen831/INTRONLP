package Assignment2;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Driver {

	public static void main(String[] args) {
	 
		try
		{
			File filePath = new File("/Users/USER/Desktop/NLP/Assign2/Opinyon/2001/April.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(filePath);
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
	
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("article");
					
			System.out.println("----------------------------");
	
			for (int temp = 0; temp < nList.getLength(); temp++) {
	
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//	
//					Element eElement = (Element) nNode;
//	
//					System.out.println("Staff id : " + eElement.getAttribute("id"));
//					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
//					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
//	
//				}
			}
			
			System.out.println(nList.getLength());
			
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }
}
