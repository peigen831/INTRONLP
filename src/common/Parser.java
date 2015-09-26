package common;

public abstract class Parser {
	
	protected String name;
	protected Date date;
	protected String body;
	
	protected Parser() {}
	
	protected Parser(String name, Date date, String body) {
		this.name = name;
		this.date = date;
		this.body = body;
	}
	
	public void parse() {
		parse(name, date, body);
	}
	
	public abstract void parse(String rawText);
	public abstract void parse(String name, Date date, String body);
	
}
