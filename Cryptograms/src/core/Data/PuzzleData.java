package core.Data;

public class PuzzleData {
	private String phrase = null;
	private String subject = null;
	private String author = null;
	private int numOfPhraseByAuthor = -1;
	private char charToSplitKey = ':';
	
	public PuzzleData(String key, String phrase, String subject) {
		this.phrase = phrase;
		this.subject = subject;
		
		splitKey(key);
	}
	
	private void splitKey(String key) {
		int indexOfSplit = key.indexOf(charToSplitKey);
		author = key.substring(0, indexOfSplit);
		
		try {
			numOfPhraseByAuthor = Integer.valueOf(key.substring(indexOfSplit + 1));
		} catch (Exception ex) {
			System.out.println("Something went wrong with the key split");
			
		}
	}
	
	public String getPhrase() { return phrase; }
	public String getSubject() { return subject; }
	public String getAuthor() { return author; }
	public int getNumber() { return numOfPhraseByAuthor; }
	
}
