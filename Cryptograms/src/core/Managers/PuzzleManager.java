package core.Managers;

import java.util.HashMap;

import core.Data.PuzzleData;

public class PuzzleManager {
	private HashMap<String, PuzzleData> puzzleMap = null;
	
	public PuzzleManager() {
		puzzleMap = new HashMap<String, PuzzleData>();
	}
	
	public void addPuzzle(String key, String phrase, String subject) {
		PuzzleData puzzle = new PuzzleData(key, phrase, subject);
		puzzleMap.put(key, puzzle);
	}
	
	public void print(String key) {
		PuzzleData puzzle = getPuzzle(key);
		if (puzzle != null) {
			System.out.println(puzzle.getAuthor() + " on the subject of " 
					+ puzzle.getSubject() + ": " + puzzle.getPhrase()
					+ "\n\n");
		}
	}
	
	public PuzzleData getPuzzle(String key) {
		PuzzleData puzzle = null;
		
		if (puzzleMap.containsKey(key)) {
			puzzle = puzzleMap.get(key);
		}
		
		return puzzle;
	}
}
