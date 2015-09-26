package assignment2ner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Parser;
import common.Date;

public class RegexParser extends Parser {

	String[] dateName = {"January", "Enero", "Jan\\.", "February", "Pebrero","Feb\\.","March","Marso","Mar\\.","April","Abril","Apr\\.","May","Mayo","June","Hunyo","Jun\\.","July","Hulyo","Jul\\.","August","Agosto","Aug\\.","September","Setyembre","Sept\\.","October","Oktubre","Oct\\.","November","Nobyembre","Nov\\.","December","Disyembre", "Dec\\.",
			"Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	String[] locationName = {"Rm.", "Ave.", "St.", "Center", "Blvd.", "City", "Brgy", "Room", "Street", "Village", "Office", "Avenue", "Floor", "Building", "Apartment", "Boulevard", "Estate", "Mountain", "Road", "Port", "River", "Ocean", "Place", "Station"};
	
	String[] orgName = {"Co.", "Coop.", "Corp.", "Ent.", "of", "Organization", "Inc.", "University", "Department", "Association", "Company", "Cooperative", "Corporation", "Enterprise", "Incorporated", "limited"};
	
	String[] personCoor = {"Si ", "Ni ", "Nina ", "Sila ", "Sina ", "Kay ", "Kila ", "Kanila "};
	
	String locCoor = "Sa ";
	
	String[] dateCoor = {"Sa ", "Noong "};
	
	String[] orgCoor = {"Ang "};
	
	String datePattern = 
			  "([A-Z][a-z]{2,8}\\.* [0-9]{1,2},* [0-9]{4})|"			 				// Dec. 6, 2004
			+ "([A-Z][a-z]{2,8}\\.* [0-9]{4})|"											// December 2004
			+ "([1-2][0-9]{3})|" 														// 2004
			+ "([0-9]{1,2}(-|/|/s)[0-9]{1,2}(-|/|/s)[0-9]{4})|"							// 8-8-2001
			+ "([Ii]ka(-|\\s)[0-9]{1,2}(th|\\s)*(ng|\\s)*([A-Za-z]*)*)|"				// Ika-3 ng Agosto
			+ "([0-9]{1,2},* [A-Z][A-Za-z]{2,8}\\.* [0-9]{4})|"							// 30 Apr. 2010
			+ "([0-9]{1,2}[a-z]{2} [a-z]{2} [A-Z][A-Za-z]{2,8}\\.* ([0-9]{4})*)|"		// 22nd of January		
			+ getDatenameRegex();
	
	String namePattern = "(([Nn]ina|[Ss]i|[Nn]i|[Ss]ila|[Ss]ina|[Ss]a|[Kk]ay|[Kk]anila|[Kk]ila|[Nn]oong)*[0-9]{0,4}(\\s|-)([A-Z][A-Za-z0-9]*)(((-)|(\\s)|(\\.)|(,\\s))*(([A-Z][0-9A-Za-z]*)|((of the)|(of))|([0-9]{0,4}))*)*)|"
			+ "(([A-Z]\\.)+)|"
			+ datePattern;
	
	static final String sLocation = "Location";
	static final String sOrganization = "Organization";
	static final String sPerson = "Person";
	static final String sDate = "Date";
	
	public void parse(String rawText){
		parseName(rawText);
	}
	
	private void parseName(String rawText){
		Pattern p = Pattern.compile(namePattern);
		
		Matcher m = p.matcher(rawText);
		
		try {
			PrintWriter nameWriter = new PrintWriter(new FileOutputStream(new File(sPerson), true));
			PrintWriter locationWriter = new PrintWriter(new FileOutputStream(new File(sLocation), true));
			PrintWriter orgWriter = new PrintWriter(new FileOutputStream(new File(sOrganization), true));
			PrintWriter dateWriter = new PrintWriter(new FileOutputStream(new File(sDate), true));
		
			//write on the file every time the regex is match
			while (m.find()) {
				String result = m.group();
				
				if(result.startsWith(" "))
					result = result.substring(1, result.length());
				
				if(isDate(result))
				{
					result = format(result, dateCoor);
					dateWriter.write(result + "\n");
				}
				else if(isLocation(result))
				{
					result = format(result, locCoor);
					locationWriter.write(result + "\n");
				}
				
				else if (isPerson(result))
				{
					result = format(result, personCoor);
					nameWriter.write(result + "\n");
				}
				
				else if (isOrganization(result))
				{
					orgWriter.write(result + "\n");
				}
				else
				{
					result = format(result, personCoor);
					if(!result.trim().equals(""))
						nameWriter.write(result + "\n");
				}
		    } 
			
			dateWriter.close();
			nameWriter.close();
			locationWriter.close();
			orgWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	private String format(String text, String[] keywordList){
		for(int i = 0; i < keywordList.length; i++){
			if(text.toLowerCase().startsWith(keywordList[i].toLowerCase())){
				return text.substring(keywordList[i].length(), text.length());
			};
		}
		return text;
	}
	
	private String format(String text, String keyword){
		if(text.toLowerCase().startsWith(keyword.toLowerCase()))
			return text.substring(keyword.length(), text.length());
		return text;
	}
	
	private boolean isDate(String text){
		Pattern p = Pattern.compile(datePattern);
		
		Matcher m = p.matcher(text);
		
		if(m.find()) {
			return true;
		}
		
		return false;
	}
	
	private boolean isPerson(String text){
		for(int i = 0; i < personCoor.length; i++)
		{
			if(text.toLowerCase().startsWith(personCoor[i].toLowerCase()))
				return true;
		}
		return false;
	}
	
	private boolean isLocation(String text){
		
		for(int i = 0; i < locationName.length; i++)
		{
			if(text.toLowerCase().contains(locationName[i].toLowerCase()))
				return true;
		}
		
		if(text.toLowerCase().startsWith(locCoor.toLowerCase()))
			return true;
		
		return false;
	}
	
	private boolean isOrganization(String text){
		for(int i = 0; i < orgName.length; i++)
		{
			if(text.toLowerCase().contains(orgName[i].toLowerCase()))
				return true;
		}
		return false;
	}
	
	private String getDatenameRegex(){
		String result = "(";
		
		for(int i = 0; i < dateName.length; i++){
			
			result += dateName[i];
			
			if(i != dateName.length-1)
				result += "|";
			
		}
		result += ")";
		return result;
	}

	@Override
	public void parse(String name, Date date, String body) {
		parse(body);
	}
}
