package mp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MPFileReader {

	private final String[] acceptEnd = {".", ":", ";", ","};
	private String filename = "test.txt";
	private int curChapter = 0;
	private String curSection;
	private String sectionPattern = "^([0-9]+(.[0-9]+)+(\\s[a-z]\\)\\s*)*)";
	private String[] invalidStart = {"Purpose:", "Standards:", "Other References:", "Guidelines:", "Metrics and Enforcement:"};
	

	private String bulletHeadFst = "";
	private String bulletHeadSnd = "";
	private int invalidCount = 0;

	
	public ArrayList<SectionSentence> readFile(){
		Scanner scanner;
		FileReader fr;
		ArrayList<SectionSentence> result = new ArrayList<SectionSentence>();
		boolean boolBulletFst = false;
		boolean boolBulletSnd = false;
		boolean isChapter = true;
		String tmp;
		String line = "";		
		Pattern p = Pattern.compile(sectionPattern);
		Matcher m;

		try {

			fr = new FileReader(filename);
			scanner = new Scanner(fr);
				
			while(scanner.hasNext())
			{	
				tmp = scanner.nextLine();
				
				if(tmp.trim().equals(""))
					continue;
				
				m = p.matcher(tmp);
				
				//track currect section
				if(m.find()){
					curSection = m.group();
					isChapter = false;
				}
				else if(tmp.startsWith("Chapter")){
					curChapter++;
					isChapter = true;
				}
				
				//remove sections
				line = getValidSentence(tmp);
				
				String[] sentences;
				if(!bulletHeadFst.equals("")){
					sentences = new String[1];
					sentences[0] = line;
				}
				else if(line.contains(".")){
					sentences = line.split("\\.");
					sentences = concatPeriod(sentences);
				}
				else {
					sentences = new String[1];
					sentences[0] = line;
				}
				
				for(String sentence: sentences)
				{
					sentence = sentence.trim();
					//SHOULD BE PER SENTENCE ALREADY
					boolBulletFst = isBulletHead(sentence);

					if(boolBulletFst && bulletHeadFst.equals("")){
						bulletHeadFst = sentence;
						bulletHeadFst = bulletHeadFst.replaceFirst(":", "");
					}
					
					else if(!sentence.equals(""))
					{
						if(!bulletHeadFst.equals(""))
						{
							String tmp1 = sentence;
							
							if(bulletHeadSnd.equals(""))
								sentence = bulletHeadFst + " " + sentence;
							else 
								sentence = bulletHeadFst + " " + bulletHeadSnd + " " + sentence;
							
							if(tmp1.replaceAll("\\s",  "").endsWith(":")){
								bulletHeadSnd = tmp1;
								bulletHeadSnd = bulletHeadSnd.replaceFirst(":", "");
								boolBulletSnd = true;
							}
							
							else if(tmp1.replaceAll("\\s", "").endsWith("."))
								bulletHeadFst = "";
							
							else if(tmp1.replaceAll("\\s", "").endsWith(";") && !bulletHeadSnd.equals(""))
								bulletHeadSnd = "";
							
							sentence = correctPunctuation(sentence);
						}
						if(!boolBulletSnd){
							if(isChapter){

								result.add(new SectionSentence(String.valueOf(curChapter), sentence));
								//System.out.println(curChapter + ": "+ sentence);
							}
							else{ 
								result.add(new SectionSentence(curSection, sentence));
								//System.out.println(curSection + ": "+ sentence);
							}
						}
						boolBulletSnd = false;
					}
				}
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getValidSentence(String lineText){
		String result = "";

		if(bulletHeadFst.equals(""))
		{
			if(isValidStart(lineText) && isValidEnd(lineText))
			{
				result = lineText;
				result = result.replaceFirst(sectionPattern, "");
			}
			else  
				{
			invalidCount++;}
		}
		
		else if (lineText.startsWith("•")){
				result = lineText.replaceFirst("•", "");
				result = result.trim();
				if(result.length()>0)
					result = Character.toLowerCase(result.charAt(0)) + result.substring(1);
		}
		else{
			invalidCount++;
			}
		
		return result;
	}

	public String[] concatPeriod(String[] sentence){
		String[] result = new String[sentence.length];
		for(int i = 0; i < sentence.length; i++)
		{
			if(i < sentence.length-1)
				result[i] = sentence[i]+".";
			else
				result[i] = sentence[i];
		}
		return result;
			
	}
	
	public String correctPunctuation(String sentence){
		if(sentence.replaceAll("\\s", "").endsWith(";"))
			return sentence.replaceFirst(";", ".");
		else if(sentence.replaceAll("\\s", "").endsWith(""))
			return sentence+=".";
		return sentence;
	}
	
	public boolean isBulletHead(String rawLine){
		if(rawLine.replaceAll("\\s", "").endsWith(":"))
			return true;
		return false;
	}
	
	public boolean isValidStart(String sentence){
		for(String s: invalidStart)
			if(sentence.startsWith(s))
				return false;
		return true;
	}
	
	public boolean isValidEnd(String sentence){
		sentence = sentence.replaceAll("\\s", "");
		
		for(String s: acceptEnd){
			if(sentence.endsWith(s))
				return true;
		}
		return false;
	}
	
	public void writeAll(ArrayList<SectionSentence> ss ){
		try{
			FileWriter fw = new FileWriter("Sentences.txt");
			for(SectionSentence s: ss ){
				fw.write(s.getSection() + " " + s.getSentence() +"\n");
			}
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		MPFileReader fr = new MPFileReader();
		
		ArrayList<SectionSentence> ss = fr.readFile();
		fr.writeAll(ss);
		
		System.out.println("Invalid count: " + fr.invalidCount);
	}
}
