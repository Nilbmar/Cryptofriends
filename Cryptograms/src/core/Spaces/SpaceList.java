package core.Spaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import core.Data.Phrase;
import core.Processes.PhraseParser;

public class SpaceList {
	private ArrayList<Space> spaceList;
	private String displayChars;
	private Phrase phraseContainer = new Phrase();
	
	public SpaceList() {
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
			if (parser.isLetter(phrase.substring(x, x+1))) {
				// Letter space
				LetterSpace letter = new LetterSpace('|', phrase.charAt(x));
				letter.setID(x);
				spaceList.add(letter);
				
			} else if (parser.isBlankSpace(phrase.substring(x, x+1))) { 
				// Blank space
				BlankSpace blank = new BlankSpace(' ');
				blank.setID(x);
				spaceList.add(blank);
				//wordBreakList.add(x);
			} else {
				// Punctuation space
				PunctuationSpace punc = new PunctuationSpace(phrase.charAt(x));
				punc.setID(x);
				spaceList.add(punc);
			}
			
			setDisplayChars();
		}
		
		//Phrase phraseContainer = new Phrase();
		Word word = new Word();
		for (Space space : spaceList) {
			switch (space.getSpaceType()) {
			case LETTER:
			case PUNC:
				word.addSpace(space);
				
				if (spaceList.size() == space.getID() + 1) {
					phraseContainer.addWord(word);
				}
				break;
			case BLANK:
				// Add previous word to Phrase if it's not blank
				if (word.size() > 0) {
					phraseContainer.addWord(word);
				}
				// Create a Blank Word to add to phrase
				word = new Word();
				word.addSpace(space);
				phraseContainer.addWordBreak(word);
				// Clear out word so a new one is started
				word = new Word();
				break;
			}
		}
	}

	public Phrase getPhrase() { return phraseContainer; }
	
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
