package core.Spaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import core.Phrases.PhraseParser;

public class SpaceContainer {
	private ArrayList<Space> spaceList;
	private String displayChars;
	
	public SpaceContainer() {
		spaceList = new ArrayList<Space>();
	}
	
	public void create(String phrase) {
		// Create an ArrayList of all spaces
		// Punctuation, Blanks, and Letters
		// Which can be used by whatever display component
		phrase = phrase.toUpperCase();
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
				spaceList.add(new LetterSpace('|', phrase.charAt(x)));
			}
			
			setDisplayChars();
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
	
	public String getDisplayChars() { return displayChars; }
	public void setDisplayChars() {
		// Scramble the alphabet to use for Space's display characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String[] alphaArr = alphabet.split("");
		List<String> toScramble = Arrays.asList(alphaArr);
		Collections.shuffle(toScramble);
		StringBuilder strBuild = new StringBuilder(alphaArr.length);
		for (String c : toScramble) {
			strBuild.append(c);
		}
		
		// Reset the display chars
		displayChars = strBuild.toString();
		
		// Update each LetterSpace with correct display char
		if (spaceList != null) {
			LetterSpace letterSpace;
			char newDisplay = '|';
			int indexOfChar = 0;
			
			for (Space space : spaceList) {
				if (space.isUnderlined()) {
					letterSpace = (LetterSpace) space;
					indexOfChar = alphabet.indexOf(
							letterSpace.getCorrectChar());
					newDisplay = displayChars.charAt(indexOfChar);
					space.setDisplayChar(newDisplay);
				}
			}
		}
	}
}
