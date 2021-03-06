package common;

import java.io.File;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader extends Thread {
	
	private Parser parser;
	private String defaultPath;
	private static String currentPath = null;
	private Object notifier;
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	Document doc;
	
	public XmlReader() {
		parser = null;
		defaultPath = null;
	}
	
	public XmlReader(Parser parser) {
		this.parser = parser;
		defaultPath = null;
	}
	
	public XmlReader(String packageName) {
		this.parser = null;
		defaultPath = ReadConfigurationFile.getProperty(packageName, "defaultPath");
	}
	
	public XmlReader(String packageName, Parser parser) {
		this.parser = parser;
		defaultPath = ReadConfigurationFile.getProperty(packageName, "defaultPath");
	}
	
	public XmlReader(Parser parser, String defaultPath) {
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
			currentPath = file.getAbsolutePath();
			parseXML(file);
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
		Collection<File> files = FileUtils.listFiles(
            new File(filePath),
            new RegexFileFilter("^(.*xml)"),
            DirectoryFileFilter.DIRECTORY
		);
		return files;
	}
	
	private void parseXML(File file) {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			
			dBuilder = dbFactory.newDocumentBuilder();
			
			doc = dBuilder.parse(file);
			
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//get the article node
			NodeList nList = doc.getElementsByTagName("article");
	
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nNode;
					String name = element.getElementsByTagName("title").item(0)
								  	     .getTextContent();
					String body = element.getElementsByTagName("body").item(0)
							             .getTextContent();
					Date date = new Date(Integer.parseInt(doc.getElementsByTagName("day")
							                    .item(0).getTextContent()),
										 doc.getElementsByTagName("month").item(0)
										    .getTextContent(),
										 Integer.parseInt(doc.getElementsByTagName("year")
											    .item(0).getTextContent()));
					
					parser.parse(name, date, body);
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
