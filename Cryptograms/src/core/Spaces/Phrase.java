package core.Spaces;

import java.util.ArrayList;

public class Phrase {
	private ArrayList<Word> phrase;
	private ArrayList<Integer> wordBreakIndexes;
	
	public Phrase() {
		phrase = new ArrayList<Word>();
		wordBreakIndexes = new ArrayList<Integer>();
	}
	
	public ArrayList<Word> getPhrase() { return phrase; }
	
	public void addWord(Word word) {
		phrase.add(word);
	}
	
	public ArrayList<Integer> getWordBreakIndexes() { return wordBreakIndexes; }
	public void addWordBreak(Word word) {
		phrase.add(word);
		wordBreakIndexes.add(phrase.size()); // maybe - 1
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Word word : phrase) {
			for (Space space : word.getWord()) {
				sb.append(space.getDisplayChar());
			}
		}
		
		return sb.toString();
	}

}
