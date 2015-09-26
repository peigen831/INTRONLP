package assignment4tagsa;

import common.Date;
import common.FileWriter;
import common.Parser;

public class TagsaParser extends Parser {
	
	private final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZÑbcdfghjklmnpqrstvwxyzñ";
	private final String VOWELS = "AEIOUaeiou";
	private final String[] PREFIX = { "i?", "ka?", "ma?", "mag?", "mang?", "na?", "nag?", "nang?", "pa?", "pag?", "pang?" };
	private final String[] SUFFIX = { "?in", "?an", "?hin", "?han" };
	private final String[] INFIX = { "?um?", "?in?" };
	
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
