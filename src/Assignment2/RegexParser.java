package Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	String namePattern = "(([A-Z][A-Za-z0-9]+)(((-)|(\\s))*(([A-Z][0-9A-Za-z]+)|((of the)|(of))))*)|"
			+ "(([A-Z]\\.)+)";
	
	String datePattern = "([A-Z][a-z]{2,8}\\.* [0-9]{1,2},* [0-9]{4})|" 	// Dec. 6, 2004
			+ "([A-Z][a-z]{2,8}\\.* [0-9]{4})|"								// December 2004
			+ "([1-2][0-9]{3})|" 											// 2004
			+ "([0-9]{1,2}(-|/|/s)[0-9]{1,2}(-|/|/s)[0-9]{4})|"				// 8-8-2001
			+ "([Ii]ka(-|\\s)[0-9]{1,2}(th|\\s)*(ng|\\s)*([A-Za-z]*)*)";   	// Ika-3 ng Agosto
	
	String sLocation = "Location";
	String sName = "Name";
	String sDate = "Date";
	
	public void parse(String rawText){
		parseDate(rawText);
		//parseName(rawText);
	}
	
	public void parseName(String rawText){
		Pattern p = Pattern.compile(namePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
//			PrintWriter nameWriter = new PrintWriter(new FileOutputStream(new File(sName), true));
//			PrintWriter locationWriter = new PrintWriter(new FileOutputStream(new File(sLocation), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				//TODO condition to separate Name from Location
				//TODO condition to avoid months
				String temp = m.group();
				
				if(temp.toLowerCase().contains("city") || temp.toLowerCase().contains("street") || temp.toLowerCase().contains("st."))
				{
					System.out.println(m.group());
					//nameWriter.write(m.group()+"\n");
				}
					
					//else locationWriter
		    } 
//			nameWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void parseDate(String rawText){
		Pattern p = Pattern.compile(datePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(sDate), true));
		
			while (m.find()) {
				//System.out.println(m.group());
				pw.write(m.group()+"\n");
		    } 
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
