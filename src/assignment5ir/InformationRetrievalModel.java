package assignment5ir;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import common.MapUtils;

public class InformationRetrievalModel {
	
	//document filepath with order
	public static ArrayList<String> noRanking(String[] normQuery) throws ClassNotFoundException, SQLException {
		for(String s: normQuery){
			System.out.println("Input: "+ s);
		}
		
		ArrayList<Relation> validRelation = new ArrayList<Relation>();
		DatabaseConnector5 db = new DatabaseConnector5("assignment5ir");
		db.openConnection();
		validRelation = db.getRelationsGivenTerms(normQuery);
		db.closeConnection();
		
		ArrayList<String> resultPath = new ArrayList<String>();
		for(Relation r : validRelation) {
			resultPath.add(r.getDocumentFilepath());
		}
		return resultPath;
	}
	
	public static ArrayList<String> tfRanking(String[] normQuery) throws ClassNotFoundException, SQLException {
		//Map<String, Double> validFileMap = new TreeMap();
		Map<String, Double> validFileMap = new TreeMap<String, Double> ();

		DatabaseConnector5 db = new DatabaseConnector5("assignment5ir");
		db.openConnection();
		ArrayList<Relation> rList = db.getRelationsGivenTerms(normQuery);
		db.closeConnection();
		
		for(Relation r : rList){
			String currentPath = r.getDocumentFilepath();
			
			for(String s: normQuery){
				if(r.getTerm().equals(s))
				{
					Double score = 0.0;
					try {
						score = validFileMap.get(currentPath);
						
						if(score == null)
							score = 0.0;
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
					int tf = r.getTermFrequency();
					if(tf > 0)
						score += (1 + Math.log10(tf));

					validFileMap.put(currentPath, score);
				}
			}
		}
		
		validFileMap = MapUtils.sortMapByValue(validFileMap);

		printMapValue(validFileMap);
		
		return new ArrayList<String>(validFileMap.keySet());
	}
	
	
	public static ArrayList<String> tdIdfRanking(String[] normQuery) throws ClassNotFoundException, SQLException {
		Map<String, Double> validFileMap = new TreeMap<String, Double> ();
		
		DatabaseConnector5 db = new DatabaseConnector5("assignment5ir");
		db.openConnection();
		ArrayList<Relation> rList = db.getRelationsGivenTerms(normQuery);
		
		for(Relation r : rList){
			String currentPath = r.getDocumentFilepath();
			
			for(String s: normQuery){
				if(r.getTerm().equals(s))
				{
					Double score = 0.0;
					try {
						score = validFileMap.get(currentPath);
						if(score == null)
							score = 0.0;
					}
					catch (Exception e) {}
					
					int tf = r.getTermFrequency();
					double df = db.getDocumentFrequency(s);
					double N = db.getTotalNumberOfDocuments(); 
					double idfScore = Math.log10(N/df);
					double tfScore = 0;
					
					
					if(tf > 0)
						tfScore = (1 + Math.log10(tf));
					score += (tfScore * idfScore);
					
					validFileMap.put(currentPath, score);
				}
			}
		}
		db.closeConnection();
		
		validFileMap = MapUtils.sortMapByValue(validFileMap);
		
		printMapValue(validFileMap);

		return new ArrayList<String>(validFileMap.keySet());
	}
	
	
	private static void printMapValue(Map<String, Double> map){
		System.out.println("Map size: "+ map.size());
		for(Map.Entry<String, Double>  entry: map.entrySet()){
			System.out.println("map value: " + entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static String normalize(String word){
		word = word.toLowerCase();
		word = removePunctuation(word);
		return word;
	}
	
	private static String removePunctuation(String word){
		return word.replaceAll("[.,(){}<>?!:]", "");
	}
}

