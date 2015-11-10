package assignment5ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import assignment4tagsa.TagsaParser;
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
		
		// TODO For each word
		ArrayList<String> normalizedWL = new ArrayList<String>();
		
		// TODO ... Normalize / stem the word normalize(word);
		// TODO ... Put the normalized word in the mapping using incrementMapping(word);
		for(String word: wordList){
			String normWord = normalize(word);
			
			normalizedWL.add(normWord);
			incrementWordFreq(normWord);
			System.out.println(word + "    " + normWord);
		}
		
//		try{
//			dbCon.openConnection();
//			
//			for(Map.Entry<String, Integer> entry: wordFreqMap.entrySet()){
//				Relation relation = new Relation(entry.getKey(), currentFilepath, entry.getValue());
//				dbCon.insertRelation(relation);
//			}
//			
//			dbCon.closeConnection();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	
	public String[] search(int option, String query){
		ArrayList<String> result = new ArrayList<String>();
		
		String[] normQuery = query.split(" ");
		for(int i = 0; i < normQuery.length; i++){
			normQuery[i] = normalize(normQuery[i]);
		}
		
		if(option == 1)
		{
			//result = getDocument(query);
		}
		else if(option == 2)
		{
			
		}
		else if (option == 3)
		{
			
		}
		return result.toArray(new String[result.size()]);
	}
	
	private ArrayList<String> getDocument(String[] query){
		ArrayList<String> validFile = new ArrayList<String>();
		//for each file under filepath
		//	 	bool flag = true
		//  	for(String word: query)
		//		{
		//			if file not contains word
		//				flag = false;
		//				break;
		//		}
		//		if(flag)
		//		validFile.add(file)
		//
		return validFile;
	}
	
	private Map<Integer, String> getDocumentTF(String[] query){
		Map<Integer, String> validFileScore = new TreeMap(Collections.reverseOrder());
		//for each file under filepath
		//		
		//		int score = 0; // score for the current file
		//  	for(String word: query)
		//		{
		//			//current document get term frequency from db
		//			int weight
		//			if(tf>0)
		//				weight = 1 + log tf
		//			else 
		//				weight = 0;
		//			score += weight
		//		}
		// 		

		//		if(score > 0)
		//			resultMap.put(score, filename);
		//
		return validFileScore;
	}
	
	private Map<Integer, String> getDocumentTFIDF(String[] query){
		Map<Integer, String> validFileScore = new TreeMap(Collections.reverseOrder());
		
		//		int score = 0		score for current file
		//  	for(String word: query)
		//		{
		//			int idf = log(N/df)     		//df = documents contains the word
		//			int tf = 1 + log tf 			//current document get term frequency from db
		//			
		//			weight = tf * idft
		//			score += weight
		//		}
		//		validFileScore.put(score, filename);
		//
		return validFileScore;
	}
	
	public String removePunctuation(String word){
		String[] punctuation = {".", ",", "?", "!"};
		
		for(String punc: punctuation)
			word = word.replace(punc, "");
		
		return word;
	}
	
	
	@Override
	public void parse(String name, Date date, String body) {
		parse(body);
	}
	
	public HashMap<String, Integer> getMapping() {
		return wordFreqMap;
	}
	
	private String normalize(String word){
		word = word.toLowerCase();
		word = removePunctuation(word);
		return word;
	}
	
	private String stem(String word) {
		String temp = new TagsaParser().parseWord(word);
				
		return temp;
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
