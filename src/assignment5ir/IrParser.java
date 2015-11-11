package assignment5ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.Date;
import common.NlpFileReader;
import common.Parser;

public class IrParser extends Parser {
	
	private HashMap<String, Integer> wordFreqMap;
	private String currentFilepath;
	private String packageName;
	
	public IrParser() {
		super();
		wordFreqMap = null;
		currentFilepath = null;
	}
	
	public IrParser(String packageName) {
		super();
		wordFreqMap = null;
		currentFilepath = null;
		this.packageName = packageName;
	}
	
	public IrParser(String name, Date date, String body) {
		super(name, date, body);
		wordFreqMap = null;
		currentFilepath = null;
	}
	
	@Override
	public void parse(String rawText) {
		// Variables setup
		DatabaseConnector5 dbCon = new DatabaseConnector5(packageName);
		
		if (!NlpFileReader.getCurrentFilepath().equals(currentFilepath)) {
			wordFreqMap = new HashMap<>();
			currentFilepath = NlpFileReader.getCurrentFilepath();
			
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
		String[] wordList = rawText.split(" ");
		
		ArrayList<String> normalizedWL = new ArrayList<String>();
		
		ArrayList<String> stopword = new StopwordFilter().getStopwords();
		
		// TODO ... Normalize / stem the word normalize(word);
		// TODO ... Put the normalized word in the mapping using incrementMapping(word);
		for(String word: wordList){
			String normWord = InformationRetrievalModel.normalize(word);
			if(!stopword.contains(normWord))
			{
				normalizedWL.add(normWord);
				incrementWordFreq(normWord);
			}
		}
		
		try{
			dbCon.openConnection();
			
			for(Map.Entry<String, Integer> entry: wordFreqMap.entrySet()){
				dbCon.insertTerm(entry.getKey());
				Relation relation = new Relation(entry.getKey(), currentFilepath, entry.getValue());
				dbCon.insertRelation(relation);
			}
			
			dbCon.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void parse(String name, Date date, String body) {
		parse(body);
	}
	
	public HashMap<String, Integer> getMapping() {
		return wordFreqMap;
	}
	
	private void incrementWordFreq(String word) {
		int count = 0;
		try {
			count = wordFreqMap.get(word);
		}
		catch (Exception e) {}
		wordFreqMap.put(word, ++count);
	}
	
}
