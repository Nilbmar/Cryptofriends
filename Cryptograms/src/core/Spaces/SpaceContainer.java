package core.Spaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import core.Phrases.PhraseParser;

public class SpaceContainer {
	private ArrayList<Space> spaceList;
	private String displayChars;
	private int currentDisplayChar = 0;
	private ArrayList<String> displayCharTaken;
	
	public SpaceContainer() {
		spaceList = new ArrayList<Space>();
	}
	
	public void create(String phrase) {
		// Create an ArrayList of all spaces
		// Punctuation, Blanks, and Letters
		// Which can be used by whatever display component
		PhraseParser parser = new PhraseParser(phrase);
		displayCharTaken = new ArrayList<String>();
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
			
			// TODO: HOW DO I STORE WHEN A LETTER SPACE HAS ALREADY TAKEN A CODE CHAR?
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
	
	public String getDisplayCharacters() { return displayChars; }
	public void setDisplayCharacters() {
		// Scramble the alphabet to use for Space's display characters
		String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
		List<String> toScramble = Arrays.asList(alphabet);
		Collections.shuffle(toScramble);
		StringBuilder strBuild = new StringBuilder(alphabet.length);
		for (String c : toScramble) {
			strBuild.append(c);
		}
		
		// Reset the display chars
		displayChars = strBuild.toString();
		currentDisplayChar = 0;
	}
	
	public char getCharToDisplay() {
		// Should not see this dash
		char display = '-';
		
		// Grabs a char form the scrambled string
		// and updates to the next char so the next one is different
		display = displayChars.charAt(currentDisplayChar);
		currentDisplayChar++;
		
		return display;
	}

}
