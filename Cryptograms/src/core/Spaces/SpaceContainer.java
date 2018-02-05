package core.Spaces;

import java.util.ArrayList;

import core.Phrases.PhraseParser;

public class SpaceContainer {
	private ArrayList<Space> spaceList;
	
	public SpaceContainer() {
		spaceList = new ArrayList<Space>();
	}
	
	public void create(String phrase) {
		// Create an ArrayList of all spaces
		// Punctuation, Blanks, and Letters
		// Which can be used by whatever display component
		PhraseParser parser = new PhraseParser(phrase);
		int len = phrase.length();
		for (int x = 0; x < len; x++) {
			if (parser.isPunctuation(phrase.substring(x, x+1))) {
				// Punctuation space
				spaceList.add(new PunctuationSpace(phrase.charAt(x)));
			} else if (parser.isBlankSpace(phrase.substring(x, x+1))) { 
				// Blank space
				spaceList.add(new BlankSpace(' '));
			} else {
				// Letter space
				spaceList.add(new LetterSpace('f', phrase.charAt(x)));
			}
		}
	}
	

	public ArrayList<Space> getList() { return spaceList; }
	public Space getSpace(int index) {
		Space space = null;
		if (spaceList != null && index <= spaceList.size()) {
			space = spaceList.get(index);
		}
		
		return space;
	}

}
