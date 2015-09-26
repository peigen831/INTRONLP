package assignment4tagsa;

import common.Date;
import common.FileWriter;
import common.Parser;

public class TagsaParser extends Parser {
	
	TagsaParser() {
		super();
	}
	
	TagsaParser(String name, Date date, String body) {
		super(name, date, body);
	}

	@Override
	public void parse(String rawText) {
		try{
			// TODO parse
			FileWriter fw = FileWriter.getInstance();
			fw.writeLine(rawText);
			fw.writeLine("");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void parse(String name, Date date, String body) {
		try{
			FileWriter fw = FileWriter.getInstance();
			fw.writeLine("Result from " + date.getYear() + " - " + date.getMonthName() + ":");
			
			parse(body);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
