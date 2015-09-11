package Assignment2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	String[] pattern = {"([A-Z][a-z]+)*"};
	
	public void parse(String rawText){
		
		for(int i = 0; i < pattern.length; i++)
		{
			Pattern p = Pattern.compile(pattern[i]);
			
			Matcher m = p.matcher(rawText);
			
			while (m.find()) {
				for (int j = 1; j <= m.groupCount(); j++) {
					if(m.group(j) != null)
						System.out.println(m.group(j));
		    	}
		    } 
		}
		
		
	}
}
