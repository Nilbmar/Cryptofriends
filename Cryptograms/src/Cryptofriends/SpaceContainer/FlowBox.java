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
		//HBox hbox = new HBox();
		//this.getChildren().add(hbox);
		//lines.add(hbox);
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
	
	public boolean goToNextLine(int wordSize) {
		boolean newLine = false;
		int currentLine = lines.size() - 1;
		int sizeOfLine = 0;
		
		for (Node node : lines.get(currentLine).getChildren()) {
			WordBox wordBox = (WordBox) node;
			sizeOfLine += wordBox.size();
		}
		
		int sizePostAdd = sizeOfLine + wordSize;
		System.out.println(" Sizes: wordSize - " + wordSize + " postAdd - " + sizePostAdd);
		if (sizePostAdd > spacesPerLine) {
			newLine = true;
		}

		return newLine;
	}
	
	public void addWordBox(WordBox wordBox) {
		
		if (this.getChildren().size() <= 0) {
			addNewLine();
		}
		
		int currentLine = lines.size() - 1;
		// Find out if word can fit on the line
		// if not, create a new line
		// if that word is not blank, then add word
		printWord(wordBox);
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
	
	
	public void printWord(WordBox wordBox) {
		for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
			Space space = spaceBox.getSpace();
			if (space.getSpaceType() == SpaceType.LETTER) {
				System.out.print(((LetterSpace) spaceBox.getSpace()).getCorrectChar());
			} else {
				System.out.print(space.getDisplayChar());
			}
		}
	}
}
