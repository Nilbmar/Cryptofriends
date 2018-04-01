package core.Data;

import core.Spaces.SpaceList;

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
	
	public int getKey() { return key; }
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
