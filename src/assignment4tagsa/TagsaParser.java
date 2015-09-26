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
    
    private void inDictionary(String word) { /* TODO */ }
    
    private void hasHyphen(String word) { /* TODO */ }
    
    private void hasPrefix(String word) { /* TODO */ }
    
    private void hasSuffix(String word) { /* TODO */ }
    
    private void hasInfix(String word) { /* TODO */ }
    
    private void hasPartialDuplicate(String word) { /* TODO */ }
    
    private void hasFullDuplicate(String word) { /* TODO */ }
    
    private void foundHyphen(String word) { /* TODO */ }
    
    private void foundPrefix(String word) { /* TODO */ }
    
    private void foundSuffix(String word) { /* TODO */ }
    
    private void foundInfix(String word) { /* TODO */ }
    
    private void foundPartialDuplicate(String word) { /* TODO */ }
    
    private void foundFullDuplicate(String word) { /* TODO */ }
}
