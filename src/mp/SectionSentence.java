package mp;

public class SectionSentence {
//	private int Chapter;
	private String section;
	private String sentence;
	
	public SectionSentence(String section, String sentence){
		this.section = section;
		this.sentence = sentence;
	}
	
//	public int getChapter() {
//		return Chapter;
//	}
//
//	public void setChapter(int chapter) {
//		Chapter = chapter;
//	}
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	

	
}
