package core.Spaces;

import java.util.ArrayList;

import core.Phrases.PhraseParser;

public class SpaceContainer {
	private ArrayList<Space> spaceList;
	
	public SpaceContainer() {
		spaceList = new ArrayList<Space>();
	}
	
	public ArrayList<Space> getList() { return spaceList; }
	public void create(String phrase) {
		PhraseParser parser = new PhraseParser(phrase);
		int len = phrase.length();
		for (int x = 0; x < len; x++) {
			if (parser.isPunctuation(phrase.substring(x, x+1))) {
				spaceList.add(new PunctuationSpace(phrase.charAt(x)));
			} else if (parser.isBlankSpace(phrase.substring(x, x+1))) { 
				spaceList.add(new BlankSpace(' '));
			} else {
				spaceList.add(new LetterSpace('f', phrase.charAt(x)));
			}
		}
	}

}
