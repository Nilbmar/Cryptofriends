package Cryptofriends.SpaceContainer;

import javafx.scene.layout.HBox;

public class WordBox extends HBox {
	private HBox hbox;
	
	public WordBox() {
		hbox = new HBox();
	}
	
	public void addSpaceBox(SpaceBox box) {
		hbox.getChildren().add(box);
	}
	
	public HBox getHBox() { return hbox; }
}
