package assignment5ir;

import java.util.HashMap;

import assignment4tagsa.TagsaParser;
import common.Date;
import common.Parser;
import common.XmlReader;

public class IrParser extends Parser {
	
	private HashMap<String, Integer> mapping;
	private String currentFilepath;
	private String packageName;
	
	public IrParser() {
		super();
		mapping = null;
		currentFilepath = null;
	}
	
	public IrParser(String packageName) {
		super();
		mapping = null;
		currentFilepath = null;
		this.packageName = packageName;
	}
	
	public IrParser(String name, Date date, String body) {
		super(name, date, body);
		mapping = null;
		currentFilepath = null;
	}
	
	@Override
	public void parse(String rawText) {
		// Variables setup
		DatabaseConnector5 dbCon = new DatabaseConnector5(packageName);
		if (!XmlReader.getCurrentFilepath().equals(currentFilepath)) {
			mapping = new HashMap<>();
			currentFilepath = XmlReader.getCurrentFilepath();
			
			// Add filepath to db
			try {
				dbCon.openConnection();
				dbCon.insertDocument(currentFilepath);
				dbCon.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// TODO Tokenize rawText / split them into words
		
		// TODO For each word
		
		// TODO ... Normalize / stem the word normalize(word);
		
		// TODO ... Put the normalized word in the mapping using incrementMapping(word);
		
		// TODO For each mapping
		
		// TODO ... Try to insert it in the db
		
		// TODO ... Relation relation = new Relation(word, currentFilepath, mapping.get(word));
		// TODO ... dbCon.openConnection();
		// TODO ... dbCon.insertRelation(relation);
		// TODO ... dbCon.closeConnection();
	}
	
	@Override
	public void parse(String name, Date date, String body) {
		parse(body);
	}
	
	public HashMap<String, Integer> getMapping() {
		return mapping;
	}
	
	private String normalize(String word) {
		return (new TagsaParser()).parseWord(word);
	}
	
	private void incrementMapping(String word) {
		int count = 0;
		try {
			count = mapping.get(word);
		}
		catch (Exception e) {}
		mapping.put(word, ++count);
	}
	
}
