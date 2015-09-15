package Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	//Shar name Regex ([A-Z][a-z.-]+( )*(of( the)* )*(de )*)* 
	String[] dateName = {"January","Enero", "Jan\\.", "February", "Pebrero","Feb\\.","March","Marso","Mar\\.","April","Abril","Apr\\.","May","Mayo","June","Hunyo","Jun\\.","July","Hulyo","Jul\\.","August","Agosto","Aug\\.","September","Setyembre","Sept\\.","October","Oktubre","Oct\\.","November","Nobyembre","Nov\\.","December","Disyembre", "Dec\\.",
			"Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", };
	
	String[] locationName = {"rm.", "st.", "city", "brgy", "room", "street"};
	
	String[] orgName = {"of", "organization", "inc", "university", "department", "association"};
	
	String namePattern = "(([A-Z][A-Za-z0-9]*)(((-)|(\\s)|(\\.))*(([A-Z][0-9A-Za-z]*)|((of the)|(of)))*)*)|"
			+ "(([A-Z]\\.)+)";
	
	String datePattern = "([A-Z][a-z]{2,8}\\.* [0-9]{1,2},* [0-9]{4})|" 	// Dec. 6, 2004
			+ "([A-Z][a-z]{2,8}\\.* [0-9]{4})|"								// December 2004
			+ "([1-2][0-9]{3})|" 											// 2004
			+ "([0-9]{1,2}(-|/|/s)[0-9]{1,2}(-|/|/s)[0-9]{4})|"				// 8-8-2001
			+ "([Ii]ka(-|\\s)[0-9]{1,2}(th|\\s)*(ng|\\s)*([A-Za-z]*)*)|"	// Ika-3 ng Agosto
			+ getDatenameRegex();   										// String[] dateName 	
	
	static final String sLocation = "Location";
	static final String sOrganization = "Organization";
	static final String sPerson = "Person";
	static final String sDate = "Date";
	
	public void parse(String rawText){
		//parseDate(rawText);
		parseName(rawText);
	}
	
	public void parseName(String rawText){
		Pattern p = Pattern.compile(namePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter nameWriter = new PrintWriter(new FileOutputStream(new File(sPerson), true));
			PrintWriter locationWriter = new PrintWriter(new FileOutputStream(new File(sLocation), true));
			PrintWriter orgWriter = new PrintWriter(new FileOutputStream(new File(sOrganization), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				String result = m.group();
				if(isDate(result))
				{
					continue;
				}
				else if(isLocation(result))
				{
					locationWriter.write(result + "\n");
					//nameWriter.write(m.group()+"\n");
				}
				else if (isOrganization(result)){
					orgWriter.write(result + "\n");
				}
				
				else{
					nameWriter.write(result + "\n");
				}
				System.out.println(m.group());
		    } 
			nameWriter.close();
			locationWriter.close();
			orgWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public boolean isLocation(String text){
		for(int i = 0; i < locationName.length; i++)
		{
			if(text.toLowerCase().contains(locationName[i]))
				return true;
		}
		return false;
	}
	
	public boolean isOrganization(String text){
		for(int i = 0; i < orgName.length; i++)
		{
			if(text.toLowerCase().contains(orgName[i]))
				return true;
		}
		return false;
	}
	
	public boolean isDate(String text){
		for(int i = 0; i < dateName.length; i++)
		{
			if(text.contains(dateName[i]))
				return true;
		}
		return false;
	}
	
	public void parseDate(String rawText){
		Pattern p = Pattern.compile(datePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(sDate), true));
		
			while (m.find()) {
				System.out.println(m.group());
				pw.write(m.group()+"\n");
		    } 
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public String getDatenameRegex(){
		String result = "(";
		
		for(int i = 0; i < dateName.length; i++){
			
			result += dateName[i];
			
			if(i != dateName.length-1)
				result += "|";
			
		}
		result += ")";
		return result;
	}
}
