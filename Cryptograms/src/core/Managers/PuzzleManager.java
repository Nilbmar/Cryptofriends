package core.Managers;

import java.util.HashMap;

import core.Data.PuzzleData;

public class PuzzleManager {
	private HashMap<Integer, PuzzleData> puzzleMap = null;
	
	public PuzzleManager() {
		puzzleMap = new HashMap<Integer, PuzzleData>();
	}
	
	public void addPuzzle(int key, String phrase, String subject, String author, int numOfAuthorQuote) {
		PuzzleData puzzle = new PuzzleData(key, phrase, subject, author, numOfAuthorQuote);
		puzzleMap.put(key, puzzle);
	}
	
	public void print(int key) {
		PuzzleData puzzle = getPuzzle(key);
		if (puzzle != null) {
			System.out.println(puzzle.getAuthor() + " on the subject of " 
					+ puzzle.getSubject() + ": " + puzzle.getPhrase()
					+ "\n\n");
		}
	}
	
	public PuzzleData getPuzzle(int key) {
		PuzzleData puzzle = null;
		
		if (puzzleMap.containsKey(key)) {
			puzzle = puzzleMap.get(key);
		}
		
		return puzzle;
	}
	
	public PuzzleData getPuzzleByAuthor(String author, int num) {
		PuzzleData puzzle = null;
		String key = author + ":" + num;
		
		if (puzzleMap.containsKey(key)) {
			puzzle = puzzleMap.get(key);
		}
		
		return puzzle;
	}
}
