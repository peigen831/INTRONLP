package Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	String namePattern = "(([A-Z][a-z]+ )+)";
	String datePattern = "([A-Z][a-z]{2,8}\\.* [0-9]{1,2},* [0-9]{4})|"
			+ "([A-Z][a-z]{2,8}\\.* [0-9]{4})|"
			+ "([1-2][0-9]{3})"; // ex Dec. 6,2004   Disyembre 10, 1992
	//String datePattern = "([1-2][0-9]{3})"; // year
	//String datePattern = "([A-Z][a-z]{2,8}\\.* [0-9]{4})"; // year
	
	String sLocation = "Location";
	String sName = "Name";
	String sDate = "Date";
	
	public void parse(String rawText){
		//parseDate(rawText);
		parseName(rawText);
	}
	
	public void parseName(String rawText){
		Pattern p = Pattern.compile(namePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter nameWriter = new PrintWriter(new FileOutputStream(new File(sName), true));
//			PrintWriter locationWriter = new PrintWriter(new FileOutputStream(new File(sLocation), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				//1 - longest word count
				if(m.group(1) != null)
				{
					//TODO condition to separate Name from Location
					//TODO condition to avoid months
					System.out.println(m.group(1));
					//if name
					nameWriter.write(m.group(1)+"\n");
					
					//else locationWriter
				}
		    } 
			nameWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void parseDate(String rawText){
		Pattern p = Pattern.compile(namePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(sDate), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				for (int j = 1; j <= m.groupCount(); j++) 
				{
					if(m.group(j) != null)
					{
						System.out.println(m.group(j));
						pw.write(m.group(j)+"\n");
					}
		    	}
		    } 
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
