package Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	
	String namePattern = "([A-Z][a-z]+)+";
	String datePattern = "([A-Z][a-z]{2,8}\\. [0-9]{1,2}, [0-9]{4})"; // ex Dec. 6,2004
	
	String sLocation = "Location";
	String sName = "Name";
	String sDate = "Date";
	
	public void parse(String rawText){
		//now only process one of datepattern
		parseByPattern(rawText, datePattern, sDate);
	}
	
	public void parseByPattern(String rawText, String pattern, String filename){
		Pattern p = Pattern.compile(pattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(filename), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				for (int j = 1; j <= m.groupCount(); j++) {
						System.out.println(m.group(j));
						pw.write(m.group(j)+"\n");
		    	}
		    } 
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
