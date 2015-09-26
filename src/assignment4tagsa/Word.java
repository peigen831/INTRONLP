package assignment4tagsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word {
	
	private String originalWord;
	private String rootWord;
	private List<String> prefixes;
	private List<String> suffixes;
	private List<String> infixes;
	
	public Word(String originalWord) {
		this.originalWord = originalWord;
		rootWord = null;
		prefixes = new ArrayList<>();
		suffixes = new ArrayList<>();
		infixes = new ArrayList<>();
	}
	
	public String getOriginalWord() {
		return originalWord;
	}
	
	public String getRootWord() {
		return rootWord;
	}
	
	public String[] getPrefixes() {
		return prefixes.toArray(new String[prefixes.size()]);
	}
	
	public String[] getSuffixes() {
		return suffixes.toArray(new String[suffixes.size()]);
	}
	
	public String[] getInfixes() {
		return infixes.toArray(new String[infixes.size()]);
	}
	
	public String getPrefix(int index) {
		return prefixes.get(index);
	}
	
	public String getSuffix(int index) {
		return suffixes.get(index);
	}
	
	public String getInfix(int index) {
		return infixes.get(index);
	}
	
	public void setRootWord(String rootWord) {
		this.rootWord = rootWord;
	}
	
	public void setPrefixes(String[] prefixes) {
		this.prefixes = new ArrayList<>(Arrays.asList(prefixes));
	}
	
	public void setSuffixes(String[] suffixes) {
		this.suffixes = new ArrayList<>(Arrays.asList(suffixes));
	}
	
	public void setInfixes(String[] infixes) {
		this.infixes = new ArrayList<>(Arrays.asList(infixes));
	}
	
	public void setPrefixes(ArrayList<String> prefixes) {
		this.prefixes = new ArrayList<>(prefixes);
	}
	
	public void setSuffixes(ArrayList<String> suffixes) {
		this.suffixes = new ArrayList<>(suffixes);
	}
	
	public void setInfixes(ArrayList<String> infixes) {
		this.infixes = new ArrayList<>(infixes);
	}
	
	public void addPrefix(String prefix) {
		prefixes.add(prefix);
	}
	
	public void addSuffix(String suffix) {
		suffixes.add(suffix);
	}
	
	public void addInfix(String infix) {
		infixes.add(infix);
	}
}
