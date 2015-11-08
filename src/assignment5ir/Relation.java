package assignment5ir;

public class Relation {
	
	private String term;
	private String documentFilepath;
	private int termFrequency;
	
	public String getTerm() {
		return term;
	}
	
	public Relation() {
		this.term = null;
		this.documentFilepath = null;
		this.termFrequency = -1;
	}
	
	public Relation(String term, String documentFilepath, int termFrequency) {
		this.term = term;
		this.documentFilepath = documentFilepath;
		this.termFrequency = termFrequency;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public String getDocumentFilepath() {
		return documentFilepath;
	}
	
	public void setDocumentFilepath(String documentFilepath) {
		this.documentFilepath = documentFilepath;
	}
	
	public int getTermFrequency() {
		return termFrequency;
	}
	
	public void setTermFrequency(int termFrequency) {
		this.termFrequency = termFrequency;
	}
	
	@Override
	public String toString() {
		return term + " - " + documentFilepath + " - " + termFrequency;
	}
}
