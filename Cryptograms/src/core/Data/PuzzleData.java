package core.Data;

import core.Spaces.SpaceList;

public class PuzzleData {
	private int key = -1;
	private int numOfPhraseByAuthor = -1;
	private String author = null;
	private String phrase = null;
	private String subject = null;
	private String hints = null;
	
	public PuzzleData(int key, String hints, String phrase, String subject, String author, int num) {
		this.key = key;
		this.hints = hints;
		this.subject = subject;
		this.author = author;
		numOfPhraseByAuthor = num;
		
		// Strip quotation marks from start and end of quote
		// Quotation mark could be " or ' 
		if (phrase.substring(0, 1).contains("\"") || phrase.substring(0, 1).contains("\'")) {
			String temp = phrase.substring(1);
			
			if (temp.endsWith("\"") || temp.endsWith("\'")) {
				temp = temp.substring(0, temp.length() - 1);
			}
			
			this.phrase = temp;
		} else {
			this.phrase = phrase;
		}
	}
	
	public int getKey() { return key; }
	public String getHints() { return hints; }
	public String getPhrase() { return phrase; }
	public String getSubject() { return subject; }
	public String getAuthor() { return author; }
	public int getNumber() { return numOfPhraseByAuthor; }
	
	public Puzzle getPuzzle() {
		Puzzle puzzle = null;
		SpaceList spaces = new SpaceList();
		spaces.create(getPhrase());
		puzzle = spaces.getPhrase();
		
		return puzzle;
	}
}
