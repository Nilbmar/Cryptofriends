package Cryptofriends.SpaceContainer;

import java.util.ArrayList;

import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FlowBox extends VBox {
	private int spacesPerLine = 14;
	private ArrayList<HBox> lines;
	
	public FlowBox() {
		lines = new ArrayList<HBox>();
	}
	
	public void clear() {
		lines = new ArrayList<HBox>();
		this.getChildren().clear();
	}
	
	public void clearSelection() {
		for (SpaceBox spaceBox : getLetterBoxes()) {
			// Set to true first so it's toggled to false
			// then deselected
			spaceBox.setSelected(true);
			spaceBox.toggleSelection();
		}
	}
	
	public int lines() { return lines.size(); }
	public int positionOnLine(int line, int spaceID) {
		int pos = -1;
		
		for (Node node : lines.get(line).getChildren()) {
			WordBox wordBox = (WordBox) node;
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				
				pos++;
				if (spaceID == spaceBox.getSpace().getID()) {
					return pos;
				}
			}
			
		}
		
		return pos;
	}
	
	public SpaceBox spaceInPosOnLine(int line, int pos) {
		SpaceBox boxToGet = null;
		int currentSpace = -1;
		
		for (Node node : lines.get(line).getChildren()) {
			WordBox wordBox = (WordBox) node;
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				
				currentSpace++;
				if (currentSpace == pos) {
					boxToGet = spaceBox;
				}
				
			}
		}
		
		return boxToGet;
	}
	
	public int lineOfSpaceBox(int spaceID) {
		int lineBoxIsOn = -1;
		
		for (HBox hbox : lines) {
			lineBoxIsOn++;
			for (Node node : hbox.getChildren()) {
				WordBox wordBox = (WordBox) node;
				for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
					if (spaceID == spaceBox.getSpace().getID()) {
						return lineBoxIsOn;
					}
				}
			}
		}
		
		return lineBoxIsOn;
	}
	
	public ArrayList<SpaceBox> spaceBoxesOnLine(int lineNum) {
		ArrayList<SpaceBox> spaceBoxes = new ArrayList<SpaceBox>();
		HBox line = lines.get(lineNum);
		WordBox wordBox = null;
		
		for (Object object : line.getChildren().toArray()) {
			wordBox = (WordBox) object;
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				spaceBoxes.add(spaceBox);
			}
		}
		
		return spaceBoxes;
	}
	
	public ArrayList<SpaceBox> getLetterBoxes() {
		ArrayList<SpaceBox> letterBoxes = new ArrayList<SpaceBox>();
		for (SpaceBox spaceBox : getSpaceBoxes()) {
			if (spaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
				letterBoxes.add(spaceBox);
			}
		}
		
		return letterBoxes;
	}
	
	public ArrayList<WordBox> getWordBoxes() {
		ArrayList<WordBox> wordBoxes = new ArrayList<WordBox>();
		
		for (HBox hbox : lines) {
			for (Node wordBox : hbox.getChildren()) {
				wordBoxes.add((WordBox) wordBox);
			}
		}
		
		return wordBoxes;
	}
	
	public ArrayList<SpaceBox> getSpaceBoxes() {
		ArrayList<SpaceBox> spaceBoxes = new ArrayList<SpaceBox>();
		
		for (WordBox wordBox : getWordBoxes()) {
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				spaceBoxes.add(spaceBox);
			}
		}
		
		return spaceBoxes;
	}
	
	public void setSpacesPerLine(int spaces) {
		spacesPerLine = spaces;
	}
	
	private void addNewLine() {
		HBox hbox = new HBox();
		this.getChildren().add(hbox);
		lines.add(hbox);
	}
	
	// Will return true if adding the latest word would
	// push the line over it's maximum number of spaces
	public boolean goToNextLine(int wordSize) {
		boolean newLine = false;
		int currentLine = lines.size() - 1;
		int sizeOfLine = 0;
		
		// Calculate the number of all SpaceBoxes on a line
		for (Node node : lines.get(currentLine).getChildren()) {
			try {
				WordBox wordBox = (WordBox) node;
				sizeOfLine += wordBox.size();
			} catch (ClassCastException e) {
				System.out.println(e);
			}
		}
		
		int sizePostAdd = sizeOfLine + wordSize;
		
		if (sizePostAdd > spacesPerLine) {
			newLine = true;
		}

		return newLine;
	}
	
	public void addWordBox(WordBox wordBox) {
		// Create first line if it doesn't already exist.
		// Creating it in the constructor doesn't work for some reason
		if (this.getChildren().size() <= 0) {
			addNewLine();
		}
		
		int currentLine = lines.size() - 1;
		
		// If word can fit on a new line, add it
		// if not, create a new line
		// if that word is not blank, then add word to the new line
		if (!goToNextLine(wordBox.size())) {
			lines.get(currentLine).getChildren().add(wordBox);
		} else {
			addNewLine();
			currentLine = lines.size() - 1;
			
			if (!wordBox.isBlankSpace()) {
				lines.get(currentLine).getChildren().add(wordBox);
			}
		}
	}
	
	public void setupPuncAlignment() {
		ArrayList<SpaceBox> spaceBoxes = getSpaceBoxes();
		
		
		SpaceBox spaceBox = null;
		Space space = null;
		SpaceType prevType = null;
		SpaceType nextType = null;
		int spaceBoxesSize = spaceBoxes.size();
		int nextSpaceIndex = -1;
		
		for (int x = 0; x < spaceBoxesSize; x++) {
			spaceBox = spaceBoxes.get(x);
			space = spaceBox.getSpace();
			
			if (space.getSpaceType() == SpaceType.PUNC) {
				((PuncBox) spaceBox).setPrevType(prevType);
				nextSpaceIndex = x + 1;
				if (spaceBoxesSize > nextSpaceIndex) {
					nextType = spaceBoxes.get(nextSpaceIndex).getSpace().getSpaceType();
				} else {
					nextType = null;
				}
				((PuncBox) spaceBox).setNextType(nextType);
			}
			
			// Set prevType to be used in the next iteration
			prevType = space.getSpaceType();
		}
		
		// Finally setting alignment
		for (SpaceBox puncBox : spaceBoxes) {
			if (puncBox.getSpace().getSpaceType() != SpaceType.PUNC) {
				puncBox.setAlignment();
			} else {
				((PuncBox) puncBox).setAlignment();
			}
		}
	}
	
	// TODO: TRY TO CHANGE THIS BACK TO PRIVATE?
	public ArrayList<SpaceBox> getIncorrectSpaceBoxes() {
		ArrayList<SpaceBox> incorrectSpaceBoxes = new ArrayList<SpaceBox>();
		
		for (SpaceBox spaceBox : getLetterBoxes()) {
			Space space = spaceBox.getSpace();
			if (space.getSpaceType() == SpaceType.LETTER) {
				if (!((LetterSpace) space).isCorrect()) {
					incorrectSpaceBoxes.add(spaceBox);
				}
			}
		}
		
		return incorrectSpaceBoxes;
	}
	
	// Hilighting
	public void clearIncorrect() {
		ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
		LetterSpace letterSpace = null;
		for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
			letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
			letterSpace.setCurrentChar(' ');
			incorrectSpaceBoxes.get(x).setAnswerCharLabel(true);
		}
		
		//clearHilights();
	}
}
