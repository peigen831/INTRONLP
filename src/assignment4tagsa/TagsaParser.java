package assignment4tagsa;

import java.util.ArrayList;
import java.util.List;

import common.Date;
import common.FileWriter;
import common.Parser;

public class TagsaParser extends Parser {
    
    private static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZÑbcdfghjklmnpqrstvwxyzñ";
    private static final String VOWELS = "AEIOUaeiou";
    /* TODO add and reorder prefix, suffix. 
     * order of affix matters*/
    private static final String[] PREFIX = { "mang", "mag", "ma",
    										 "nang", "nag", "na",
    										 "pang", "pag", "pa",
    										 "ka", "ki", "i" };
    private static final String[] SUFFIX = { "han", "hin", "an", "in" };
    
    private List<String> foundPrefixes;
    private List<String> foundSuffixes;
    private List<String> foundInfixes;
    private List<Word> resultWords;
    
    public static void main(String[] args) {
    	TagsaParser parser = new TagsaParser();
    	parser.parse("kinakabahan");
    }
    
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
            	
            	if (hasHyphen(currentWord)) {
                    currentWord = processWordWithHyphen(currentWord);
                }
            	
            	// Step 2 - get and remove /-in-/
                if (hasInfix(currentWord, "in")) {
            	   currentWord = processWordWithInfix(currentWord, "in");
                }
            	
            	String lastAcceptableWord = currentWord;
            	
            	// Step 3 - get and remove prefix
            	while (lastAcceptableWord.equals(currentWord) && hasPrefix(currentWord)) {
	            	currentWord = processWordWithPrefix(currentWord);
	            	if (isAcceptable(currentWord)) {
	            		lastAcceptableWord = currentWord;
	            	}
            	}
            	
                currentWord = lastAcceptableWord;
            	
            	// Step 4 - get and remove /-um-/
                if (hasInfix(currentWord, "um")) {
                    currentWord = processWordWithInfix(currentWord, "um");
                }
            	
                lastAcceptableWord = currentWord;

                // Step 5 - get and remove partial duplications
                while (lastAcceptableWord.equals(currentWord) && hasPartialDuplicate(currentWord)) {
                    currentWord = processWordWithPartialDuplicate(currentWord);
                    if (isAcceptable(currentWord)) {
                        lastAcceptableWord = currentWord;
                    }
                }
            	
                currentWord = lastAcceptableWord;
            	
                // Step 6 - get and remove suffixes
                while (lastAcceptableWord.equals(currentWord) && hasSuffix(currentWord)) {
                    currentWord = processWordWithSuffix(currentWord);
                    if (isAcceptable(currentWord)) {
                        lastAcceptableWord = currentWord;
                    }
                }
                
                currentWord = lastAcceptableWord;
            	
                // Step 7 - get and remove full duplications
                if (hasFullDuplicate(currentWord)) {
                    currentWord = processWordWithFullDuplicate(currentWord);
                }
            	
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

    private boolean hasHyphen(String word) {
    	return word.contains("-");
    }
    
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
                if (currentSubstring.startsWith(prefix)) {
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
    private boolean hasSuffix(String word) {
    	boolean hasSuffix = false;
        try {
            String currentSubstring = new String(word);
            for (String suffix : SUFFIX) {
                if (currentSubstring.endsWith(suffix)) {
                    hasSuffix = true;
                    break;
                }
            }
        } catch (Exception e) {}

        return hasSuffix;
    }
    
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
                currentSubstring = currentSubstring.substring(1, currentSubstring.length());
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
	private boolean hasPartialDuplicate(String word) { /* TODO check */
		if(isAcceptable(word)){
			if(word.charAt(0) == word.charAt(1))
				return true;
			else if(word.substring(0, 2).equals(word.substring(2,4)))
				return true;
		}
				
		return false; 
	}
    
    /**
     * Checks if the word contains a full duplicate
     * @param String word
     * @return true if the word has a full duplicate;
     * 		   false otherwise
     */
    private boolean hasFullDuplicate(String word) {
    	boolean hasFullDuplicate = false;
    	
    	try {
    		String half1 = null;
    		String half2 = null;
    		
    		if (word.length() % 2 == 0) {
	    		half1 = word.substring(0, word.length() / 2);
	    		half2 = word.substring(word.length() / 2, word.length());
	    		
	    		if ("u".equals(half1.charAt(half1.length() - 1) + "")) {
	    			half1 = half1.substring(0, half1.length() - 1) + "o";
	    		}
	    		else if (CONSONANTS.contains(half1.charAt(half1.length() - 1) + "")
	    				 && "u".equals(half1.charAt(half1.length() - 2) + "")) {
	    			half1 = half1.substring(0, half1.length() - 2) + "o" + half1.charAt(half1.length() - 1);
	    		}
	    		
	    		hasFullDuplicate = half1.equals(half2);
    		}
    	} catch (Exception e) {}
    	
    	return hasFullDuplicate;
    }
    
    /**
     * Processes the word to split the hyphenated words or remove
     * the prefix and check if the result is acceptable
     * @param String word
     */
    private String processWordWithHyphen(String word) { /* TODO check*/
    	//always gets the second word if it's acceptable
    	String[] splitWord = word.split("-");
    	
    	if(isAcceptable(splitWord[1]))
    		return splitWord[1];
    	
    	else return splitWord[0]; 
    }
    
    /**
     * Processes the word to remove the prefix and check
     * if the result is acceptable
     * @param String word
     */
	private String processWordWithPrefix(String word) { 
        for (String prefix : PREFIX) {
            if (word.startsWith(prefix)) {
                return word.substring(prefix.length());
            }
        }
		return word; 
	}
    
    /**
     * Processes the word to remove the suffix and check
     * if the result is acceptable
     * @param String word
     */
	private String processWordWithSuffix(String word) { 
        for (String suffix : SUFFIX) {
            if (word.endsWith(suffix)) {
                return word.substring(0, word.length()-suffix.length());
            }
        }
		return word;
	}
    
    /**
     * Processes the word to remove the infix and check
     * if the result is acceptable
     * @param String word - word to be processed
     * @param String infix - infix to check for
     */
	public String processWordWithInfix(String word, String infix){
		for(int i = 0; i < word.length()-2; i++)
		{
			//if the infix is as prefix, check it's followed by a vowel
			if(i == 0 && word.startsWith(infix) && VOWELS.contains(Character.toString(word.charAt(i+2))))
					return word.substring(i+2);
				
			//if the infix is as infix, check the before character is consonant and the after character is vowel
			else if (word.substring(i, i+2).equals(infix) && CONSONANTS.contains(Character.toString(word.charAt(i-1))) && VOWELS.contains(Character.toString(word.charAt(i+2))))
				return word.substring(0, i) + word.substring(i + 2);
		}

		return word;
	}
    
    /**
     * Processes the word to remove the partial duplicate and check
     * if the result is acceptable
     * @param String word
     */
	private String processWordWithPartialDuplicate(String word) { /* TODO */ 
		if(isAcceptable(word)){
			if(word.charAt(0) == word.charAt(1))
				return word.substring(1);
			else if(word.substring(0, 2).equals(word.substring(2,4)))
				return word.substring(2);
		}
		return word; 
	}
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
