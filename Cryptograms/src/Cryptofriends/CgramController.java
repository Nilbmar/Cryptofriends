package Cryptofriends;

import Cryptofriends.SpaceContainer.SpaceBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

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
		addSpaceBox('I', 'T', true);
		addSpaceBox(' ', ' ', false);
		addSpaceBox('L', 'B', true);
		addSpaceBox('O', 'Q', true);
		addSpaceBox('V', 'I', true);
		addSpaceBox('E', 'Z', true);
		addSpaceBox(' ', ' ', false);
		addSpaceBox('Y', 'C', true);
		addSpaceBox('O', 'Q', true);
		addSpaceBox('U', 'S', true);
		addSpaceBox(',', ' ', false);
		addSpaceBox(' ', ' ', false);
		addSpaceBox('S', 'M', true);
		addSpaceBox('O', 'Q', true);
		addSpaceBox(' ', ' ', false);
		addSpaceBox('M', 'D', true);
		addSpaceBox('U', 'S', true);
		addSpaceBox('C', 'P', true);
		addSpaceBox('H', 'A', true);
		addSpaceBox('!', ' ', false);
		
	}
}
