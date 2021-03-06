package assignment2ner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	
	//change this to your local path
	String defaultPath = ReadConfigurationFile.getProperty("defaultPath");
	
	String filepath;
	
	//to add 2012
	String[] yearList = {"2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012"};
	
	String [] monthList = {"January", "February", "March", "April" , "May", "June" , "July", "August" , "September", "October" , "November", "December" };
	
	File file;
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	DocumentBuilder dBuilder;
	
	Document doc;
	
	RegexParser rParser;
	
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
			//e.printStackTrace();
		}
	}
	
	public void parseXML(){
		try
		{
			initialize();
			
			//get the article node
			NodeList nList = doc.getElementsByTagName("article");
	
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
					
					rParser.parse(eElement.getElementsByTagName("body").item(0).getTextContent());
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	//tried under Opinyon folder
	public void start(){
		
		//parse XML files under News folder
//		filepath = defaultPath + "News/2001/May.xml";
//		parseXML();
		for(int i = 0; i < yearList.length; i++)
		{
			for(int j = 0; j < monthList.length; j++)
			{
				writeArticleDate(yearList[i], monthList[j]);
				filepath = defaultPath + "News/" + yearList[i] + "/" + monthList[j] + ".xml";
				parseXML();
				
				if(i == yearList.length - 1 && monthList[j].equals("September"))
					break;
			}
		}
			
		//parse XML files under Opinyon folder
		for(int i = 0; i < yearList.length; i++)
		{
			for(int j = 0; j < monthList.length; j++)
			{
				writeArticleDate(yearList[i], monthList[j]);
				filepath = defaultPath + "Opinyon/" + yearList[i] + "/" + monthList[j] + ".xml";
				parseXML();

				if(i == yearList.length - 1 && monthList[j].equals("September"))
					break;
			}
		}
	}
	
	//write the year and month for the result files
	public void writeArticleDate(String year, String month){
		try{
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(RegexParser.sLocation), true));
			pw.write("Result from " + year + " - " + month + ":\n");
			pw.close();
			
			pw = new PrintWriter(new FileOutputStream(new File(RegexParser.sDate), true));
			pw.write("Result from " + year + " - " + month + ":\n");
			pw.close();
			
			pw = new PrintWriter(new FileOutputStream(new File(RegexParser.sPerson), true));
			pw.write("Result from " + year + " - " + month + ":\n");
			pw.close();
			
			pw = new PrintWriter(new FileOutputStream(new File(RegexParser.sOrganization), true));
			pw.write("Result from " + year + " - " + month + ":\n");
			pw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
