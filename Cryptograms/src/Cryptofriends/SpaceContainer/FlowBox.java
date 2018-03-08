package Cryptofriends.SpaceContainer;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FlowBox extends VBox {
	private int spacesPerLine = 14;
	private ArrayList<HBox> lines;
	
	public FlowBox() {
		lines = new ArrayList<HBox>();
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
	
	public ArrayList<WordBox> getWordBoxes() {
		ArrayList<WordBox> children = new ArrayList<WordBox>();
		
		for (HBox hbox : lines) {
			for (Node wordBox : hbox.getChildren()) {
				children.add((WordBox) wordBox);
			}
		}
		
		return children;
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
}
