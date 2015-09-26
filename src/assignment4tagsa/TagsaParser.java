package assignment4tagsa;

import java.util.ArrayList;
import java.util.List;

import common.Date;
import common.FileWriter;
import common.Parser;

public class TagsaParser extends Parser {
    
    private final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZÑbcdfghjklmnpqrstvwxyzñ";
    private final String VOWELS = "AEIOUaeiou";
    private final String[] PREFIX = { "i?", "ka?", "ma?", "mag?", "mang?", "na?", "nag?", "nang?", "pa?", "pag?", "pang?" };
    private final String[] SUFFIX = { "?in", "?an", "?hin", "?han" };
    private final String[] INFIX = { "?um?", "?in?" };
    
    private List<String> foundPrefixes;
    private List<String> foundSuffixes;
    private List<String> foundInfixes;
    private List<Word> words;
    
    TagsaParser() {
        super();
    }
    
    TagsaParser(String name, Date date, String body) {
        super(name, date, body);
    }

    @Override
    public void parse(String rawText) {
    	words = new ArrayList<>();
        try{
        	// parse each word
            String[] allWords = rawText.split("[.,?!(): ]+");
            for (String sWord : allWords) {
            	foundPrefixes = new ArrayList<>();
            	foundSuffixes = new ArrayList<>();
            	foundInfixes = new ArrayList<>();
            	Word word = new Word(sWord);
                
            	// TODO Step 1 - check if in dictionary; if yes, stop; else continue
                // TODO Step 2 - check if hyphenated; if yes, is it a compound word or a prefix; if no, continue
            	// TODO Step 3 - check if has infix e.g. kINawayan; if yes, separate infix
            	
            	words.add(word);
            }
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
    
    /**
     * Checks if the word is found in the dictionary
     * @param String word
     * @return true if the word is in the dictionary;
     * 		   false otherwise
     */
    private boolean inDictionary(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a hyphen
     * @param String word
     * @return true if the word has a hyphen;
     * 		   false otherwise
     */
    private boolean hasHyphen(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a prefix
     * @param String word
     * @return true if the word has a prefix;
     * 		   false otherwise
     */
    private boolean hasPrefix(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a suffix
     * @param String word
     * @return true if the word has a suffix;
     * 		   false otherwise
     */
    private boolean hasSuffix(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a infix
     * @param String word
     * @return true if the word has a infix;
     * 		   false otherwise
     */
    private boolean hasInfix(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a partial duplicate
     * @param String word
     * @return true if the word has a partial duplicate;
     * 		   false otherwise
     */
    private boolean hasPartialDuplicate(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a full duplicate
     * @param String word
     * @return true if the word has a full duplicate;
     * 		   false otherwise
     */
    private boolean hasFullDuplicate(String word) { /* TODO */ return false; }
    
    /**
     * Processes the word to split the hyphenated words or remove
     * the prefix and check if the result is acceptable
     * @param String word
     */
    private void processWordWithHyphen(String word) { /* TODO */ }
    
    /**
     * Processes the word to remove the prefix and check
     * if the result is acceptable
     * @param String word
     */
    private void processWordWithPrefix(String word) { /* TODO */ }
    
    /**
     * Processes the word to remove the suffix and check
     * if the result is acceptable
     * @param String word
     */
    private void processWordWithSuffix(String word) { /* TODO */ }
    
    /**
     * Processes the word to remove the infix and check
     * if the result is acceptable
     * @param String word
     */
    private void processWordWithInfix(String word) { /* TODO */ }
    
    /**
     * Processes the word to remove the partial duplicate and check
     * if the result is acceptable
     * @param String word
     */
    private void processWordWithPartialDuplicate(String word) { /* TODO */ }
    
    /**
     * Processes the word to remove the full duplicate and check
     * if the result is acceptable
     * @param String word
     */
    private void processWordWithFullDuplicate(String word) { /* TODO */ }
    
    /**
     * Finds the first candidate that is found in the dictionary
     * @param String[] candidates
     * @return the best candidate
     */
    private String findBestCandidate(String[] candidates) {
    	String bestCandidate = null;
    	for (String candidate : candidates) {
    		if (inDictionary(candidate)) {
    			bestCandidate = candidate;
    			break;
    		}
    	}
    	return bestCandidate;
    }
    
    /**
     * Checks if the resulting word is "acceptable" wherein
     * acceptable means that a word starting with a vowel has at
     * least 3 letters and at least 1 consonant, while a word
     * starting with a consonant has at least 4 letters and at
     * least 1 vowel
     * @param String word
     * @return true if the word is an acceptable length;
     *         false otherwise
     */
    private boolean isAcceptable(String word) {
    	if (VOWELS.contains(word.charAt(0) + "")) {
    		if (word.length() >= 3 /* TODO && word contains at least 1 consonant */) {
    			return true;
    		}
    	}
    	else if (CONSONANTS.contains(word.charAt(0) + "")) {
    		if (word.length() >= 4 /* TODO && word contains at least 1 vowel */) {
    			return true;
    		}
    	}
    	return false;
    }
}
