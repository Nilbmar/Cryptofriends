package Cryptofriends.SpaceContainer;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FlowBox extends HBox {
	private int spacesPerLine = 0;
	private ArrayList<VBox> lines;
	
	public FlowBox() {
		lines = new ArrayList<VBox>();
		lines.add(new VBox());
	}
	
	public void setSpacesPerLine(int spaces) {
		spacesPerLine = spaces;
	}
	
	public boolean goToNextLine(int wordSize) {
		boolean newLine = false;
		int currentLine = lines.size();
		int sizePostAdd = lines.get(currentLine).getChildren().size() + wordSize;
		newLine = (sizePostAdd <= spacesPerLine);
		return newLine;
	}
	
	public void addSpaceBox(WordBox wordBox) {
		int currentLine = lines.size();
		
		// Find out if word can fit on the line
		// if not, create a new line
		// if that word is not blank, then add word
		if (!goToNextLine(wordBox.size())) {
			lines.get(currentLine).getChildren().add(wordBox);
		} else {
			lines.add(new VBox());
			currentLine++;
			
			if (!wordBox.isBlankSpace()) {
				lines.get(currentLine).getChildren().add(wordBox);
			}
		}
	}
	
	

}
