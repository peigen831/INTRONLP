package Assignment2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	
	private String filepath;
	
	private File file;
	
	private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	private DocumentBuilder dBuilder;
	
	private Document doc;
	
	private RegexParser rParser;
	
	public XMLParser(String filepath){
		this.filepath = filepath;
	}
	
	public void setFilePath(String filepath){
		this.filepath = filepath;
	}
	
	public void initialize(){
		try
		{
			file = new File(filepath);
			
			dbFactory = DocumentBuilderFactory.newInstance();
			
			dBuilder = dbFactory.newDocumentBuilder();
			
			doc = dBuilder.parse(file);
			
			rParser = new RegexParser();
			
			doc.getDocumentElement().normalize();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseXML(){
		try
		{
			initialize();
					
			NodeList nList = doc.getElementsByTagName("article");
	
//			for (int temp = 0; temp < nList.getLength(); temp++) {
			for (int temp = 0; temp < 1; temp++) {
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
//					System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
//					System.out.println("Date : " + eElement.getElementsByTagName("month").item(0).getTextContent() +
//							" " + eElement.getElementsByTagName("day").item(0).getTextContent() + 
//							" " + eElement.getElementsByTagName("year").item(0).getTextContent());
//					System.out.println("Body : " + eElement.getElementsByTagName("body").item(0).getTextContent());
					rParser.parse(eElement.getElementsByTagName("title").item(0).getTextContent());
					rParser.parse(eElement.getElementsByTagName("body").item(0).getTextContent());
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	
}
