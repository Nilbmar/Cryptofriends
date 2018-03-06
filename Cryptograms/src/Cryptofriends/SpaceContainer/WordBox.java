package Cryptofriends.SpaceContainer;

import java.util.ArrayList;

import javafx.scene.layout.HBox;

public class WordBox extends HBox {
	
	public WordBox() {
	}
	
	
	public int size() { 
		return this.getChildren().size();
	}
	
	public boolean isBlankSpace() {
		boolean blank = false;
		blank = (getSpaceBox(0).getSpace().getDisplayChar() == ' ');
		return blank;
	}
	
	public SpaceBox getSpaceBox(int index) {
		return (SpaceBox) this.getChildren().get(index);
	}
	public void addSpaceBox(SpaceBox box) {
		this.getChildren().add(box);
	}
	
	public ArrayList<SpaceBox> getAllSpaceBoxes() {
		ArrayList<SpaceBox> spaceBoxes = new ArrayList<SpaceBox>();
		int wordSize = this.getChildren().size();
		if (wordSize > 0) {
			for (int x = 0; x < wordSize; x++) {
				spaceBoxes.add(getSpaceBox(x));
			}
		}
		return spaceBoxes;
	}
}
