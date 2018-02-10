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
		}
	}
}
