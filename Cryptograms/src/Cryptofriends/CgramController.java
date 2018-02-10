package Cryptofriends;

import Cryptofriends.SpaceContainer.SpaceBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class CgramController {
	@FXML
	private FlowPane flow;
	
	public void addSpaceBox(char answer, char display, boolean underline) {
		if (flow != null) {
			SpaceBox spaceBox = new SpaceBox();
			spaceBox.setAnswerCharLabel(answer);
			spaceBox.setDisplayCharLabel(display);
			spaceBox.setUnderlined(underline);
			flow.getChildren().add(spaceBox);
			System.out.println("Adding Space Box");
		}
	}
	
	public void addLotsOfBoxes() {
		for (int x = 0; x < 10; x++) {
    		addSpaceBox('A', 'T', true);
    		addSpaceBox(' ', ' ', false);
    		addSpaceBox(' ', '!', false);
    	}
	}
}
