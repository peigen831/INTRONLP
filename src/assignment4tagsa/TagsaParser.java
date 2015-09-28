package assignment4tagsa;

import java.util.ArrayList;
import java.util.List;

import common.Date;
import common.FileWriter;
import common.Parser;

public class TagsaParser extends Parser {
    
    private final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZÑbcdfghjklmnpqrstvwxyzñ";
    private final String VOWELS = "AEIOUaeiou";
    private final String[] PREFIX = { "i", "ka", "ma", "mag", "mang", "na", "nag", "nang", "pa", "pag", "pang" };
    private final String[] SUFFIX = { "in", "an", "hin", "han" };
    
    private List<String> foundPrefixes;
    private List<String> foundSuffixes;
    private List<String> foundInfixes;
    private List<Word> resultWords;
    
    TagsaParser() {
        super();
    }
    
    TagsaParser(String name, Date date, String body) {
        super(name, date, body);
    }

    @Override
    public void parse(String rawText) {
    	resultWords = new ArrayList<>();
        try{
        	// parse each word
            String[] allWords = rawText.split("[*.,?!(): ]+");
            for (String sWord : allWords) {
            	foundPrefixes = new ArrayList<>();
            	foundSuffixes = new ArrayList<>();
            	foundInfixes = new ArrayList<>();
            	Word word = new Word(sWord);
            	
            	String currentWord = sWord;
            	
            	// TODO Step 1 - get and remove hyphen
            	if (hasHyphen(currentWord)) {
                    currentWord = processWordWithHyphen(currentWord);
                }
            	
            	// TODO Step 2 - get and remove /-in-/
                if (hasInfix(currentWord, "in")) {
            	   currentWord = processWordWithInfix(currentWord, "in");
                }
            	
            	String lastAcceptableWord = currentWord;
            	
            	// TODO Step 3 - get and remove prefix
            	while (lastAcceptableWord.equals(currentWord) && hasPrefix(currentWord)) {
	            	currentWord = processWordWithPrefix(currentWord);
	            	if (isAcceptable(currentWord)) {
	            		lastAcceptableWord = currentWord;
	            	}
            	}

                currentWord = lastAcceptableWord;
            	
            	// TODO Step 4 - get and remove /-um-/
                if (hasInfix(currentWord, "um")) {
                    currentWord = processWordWithInfix(currentWord, "um");
                }

                lastAcceptableWord = currentWord;

                // TODO Step 5 - get and remove partial duplications
                while (lastAcceptableWord.equals(currentWord) && hasPartialDuplicate(currentWord)) {
                    currentWord = processWordWithPartialDuplicate(currentWord);
                    if (isAcceptable(currentWord)) {
                        lastAcceptableWord = currentWord;
                    }
                }
            	
                currentWord = lastAcceptableWord;

                // TODO Step 6 - get and remove suffixes
                while (lastAcceptableWord.equals(currentWord) && hasSuffix(currentWord)) {
                    currentWord = processWordWithSuffix(currentWord);
                    if (isAcceptable(currentWord)) {
                        lastAcceptableWord = currentWord;
                    }
                }
                
                currentWord = lastAcceptableWord;

                // TODO Step 7 - get and remove full duplications
                if (hasFullDuplicate(currentWord)) {
                    currentWord = processWordWithFullDuplicate(currentWord);
                }

//            	System.out.println(sWord);
//            	boolean hasOperated = true;
//            	
//            	while(hasOperated){
//            		hasOperated = false;
//            		// TODO Step 1 - check if in dictionary; if yes, stop; else continue
//                    // TODO Step 2 - check if hyphenated; if yes, is it a compound word or a prefix; if no, continue
//                	// TODO Step 3 - check if has infix e.g. kINawayan; if yes, separate infix
//                	// TODO Step 4 - check if has prefix; if yes, separate prefix
//                	// TODO Step 5 - check if has suffix; if yes, separate suffix
//                	// TODO Step 6 - check if has partial duplicate; if yes, remove duplicate
//                	// TODO Step 7 - check if has full duplicate; if yes, remove duplicate
//            	}

            	if(isAcceptable(sWord))
            	{
	            	word.setPrefixes(foundPrefixes);
	            	word.setSuffixes(foundSuffixes);
	            	word.setInfixes(foundInfixes);
	            	word.setRootWord(sWord);
	            	resultWords.add(word);
            	}
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
//    private boolean inDictionary(String word) { /* TODO */ return false; }
    
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
    private boolean hasPrefix(String word) {
    	boolean hasPrefix = false;
        try {
            String currentSubstring = new String(word);
            for (String prefix : PREFIX) {
                if (currentSubstring.startsWith(prefix, 1)) {
                    hasPrefix = true;
                    break;
                }
            }
        } catch (Exception e) {}

        return hasPrefix;
    }
    
    /**
     * Checks if the word contains a suffix
     * @param String word
     * @return true if the word has a suffix;
     * 		   false otherwise
     */
    private boolean hasSuffix(String word) { /* TODO */ return false; }
    
    /**
     * Checks if the word contains a infix
     * @param String word - word to be checked
     * @param String infix - infix to check for
     * @return true if the word has a infix;
     * 		   false otherwise
     */
    private boolean hasInfix(String word, String infix) {
        boolean hasInfix = false;
        try {
            String currentSubstring = word;
            while (!CONSONANTS.contains(currentSubstring.charAt(0) + "")) {
                currentSubstring = currentSubstring.substring(1, currentSubstring.length() - 1);
            }

            if (currentSubstring.startsWith(infix, 1)) {
            	hasInfix = true;
            }
        } catch (Exception e) {}

        return hasInfix;
    }
    
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
    private String processWordWithHyphen(String word) { /* TODO */ return null; }
    
    /**
     * Processes the word to remove the prefix and check
     * if the result is acceptable
     * @param String word
     */
    private String processWordWithPrefix(String word) { /* TODO */ return null; }
    
    /**
     * Processes the word to remove the suffix and check
     * if the result is acceptable
     * @param String word
     */
    private String processWordWithSuffix(String word) { /* TODO */ return null; }
    
    /**
     * Processes the word to remove the infix and check
     * if the result is acceptable
     * @param String word - word to be processed
     * @param String infix - infix to check for
     */
    private String processWordWithInfix(String word, String infix) { /* TODO */ return null; }
    
    /**
     * Processes the word to remove the partial duplicate and check
     * if the result is acceptable
     * @param String word
     */
    private String processWordWithPartialDuplicate(String word) { /* TODO */ return null; }
    
    /**
     * Processes the word to remove the full duplicate and check
     * if the result is acceptable
     * @param String word
     */
    private String processWordWithFullDuplicate(String word) { /* TODO */ return null; }
    
    /**
     * Finds the first candidate that is found in the dictionary
     * @param String[] candidates
     * @return the best candidate
     */
//    private String findBestCandidate(String[] candidates) {
//    	String bestCandidate = null;
//    	for (String candidate : candidates) {
//    		if (inDictionary(candidate)) {
//    			bestCandidate = candidate;
//    			break;
//    		}
//    	}
//    	return bestCandidate;
//    }
    
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
    		if (word.length() >= 3 && hasConsonant(word)) {
    			return true;
    		}
    	}
    	else if (CONSONANTS.contains(word.charAt(0) + "")) {
    		if (word.length() >= 4 && hasVowel(word)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public boolean hasVowel(String word){
		
		for(int i = 0; i < word.length(); i++)
			if(VOWELS.contains(Character.toString(word.charAt(i))))
				return true;
		
		return false;		
	}
	
	public boolean hasConsonant(String word){
		
		for(int i = 0; i < word.length(); i++)
			if(CONSONANTS.contains(Character.toString(word.charAt(i))))
				return true;
		
		return false;	
	}
}
