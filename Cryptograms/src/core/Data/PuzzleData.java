package core.Data;

public class PuzzleData {
	private int key = -1;
	private int numOfPhraseByAuthor = -1;
	private String author = null;
	private String phrase = null;
	private String subject = null;
	
	public PuzzleData(int key, String phrase, String subject, String author, int num) {
		this.key = key;
		this.phrase = phrase;
		this.subject = subject;
		this.author = author;
		numOfPhraseByAuthor = num;
	}
	
	/*
	private void splitKey(String key) {
		char charToSplitKey = ':';
		int indexOfSplit = key.indexOf(charToSplitKey);
		author = key.substring(0, indexOfSplit);
		
		try {
			numOfPhraseByAuthor = Integer.valueOf(key.substring(indexOfSplit + 1));
		} catch (Exception ex) {
			System.out.println("Something went wrong with the key split");
			
		}
	}
	*/
	public int getKey() { return key; }
	public String getPhrase() { return phrase; }
	public String getSubject() { return subject; }
	public String getAuthor() { return author; }
	public int getNumber() { return numOfPhraseByAuthor; }
	
}
