package Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	String namePattern = "(([A-Z][A-Za-z0-9]+)(((-)|(\\s))*(([A-Z][0-9A-Za-z]+)|((of the)|(of))))*)|"
			+ "(([A-Z]\\.)+)"; //U.S.A.   Peigen-Gen   Manila City   SPO3 Jose Escalante
//	String datePattern = "([A-Z][a-z]{2,8}\\.* [0-9]{1,2},* [0-9]{4})|"
//			+ "([A-Z][a-z]{2,8}\\.* [0-9]{4})|"
//			+ "([1-2][0-9]{3})"; // ex Dec. 6,2004   Disyembre 10, 1992
	String datePattern = "[0-9]{2,4}(-|/|/s)[0-9]{2,4}(-|/|/s)[0-9]{2,4}";
	
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
				//1 - longest word count
				if(m.group(1) != null)
				{
					//TODO condition to separate Name from Location
					//TODO condition to avoid months
					String temp = m.group(1);
					
					if(temp.toLowerCase().contains("city") || temp.toLowerCase().contains("street") || temp.toLowerCase().contains("st."))
					{
						System.out.println(m.group(1));
						//nameWriter.write(m.group(1)+"\n");
					}
					
					//else locationWriter
				}
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
		
			//write on the file every time the regex is match
			while (m.find()) {
				System.out.println(m.group());
				//pw.write(m.group()+"\n");
		    } 
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
