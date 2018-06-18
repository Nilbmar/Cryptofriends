package Cryptofriends.GUI;

import core.Managers.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PopInBox {
	private VBox vBox;
	
	public PopInBox() {
		vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setMaxHeight(100);
		//vBox.setMaxWidth(300);
		vBox.setStyle("-fx-background-color: #4abdac;"
				+" -fx-spacing: 10;");
	}
	
	public VBox clearIncorrect(GameManager gameMan) {
		Label lblTitle = new Label("Reveal Mistakes");
		Label lblMessage = new Label("Clear incorrect letters?");
		
		HBox hBoxButtons = new HBox();
		hBoxButtons.setAlignment(Pos.CENTER);
		hBoxButtons.setStyle("-fx-spacing: 15;");
		Button btnClear = new Button("Clear");
		Button btnCancel = new Button("No");
		hBoxButtons.getChildren().addAll(btnClear, btnCancel);
		
		// Goes to next puzzle and removes this popin box
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameMan.clearIncorrect();
				gameMan.removeAlertPopIn();
		}});
		
		// Just removes this popin box
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameMan.removeAlertPopIn();
			}
		});
		
		vBox.getChildren().addAll(lblTitle, lblMessage, hBoxButtons);
		
		return vBox;
	}
	
	public VBox winnerBox(GameManager gameMan, String solvedBy) {
		Label lblTitle = new Label("Congratulations");
		Label lblMessage = new Label("Puzzle was solved by " + solvedBy);
		
		HBox hBoxButtons = new HBox();
		hBoxButtons.setAlignment(Pos.CENTER);
		hBoxButtons.setStyle("-fx-spacing: 15;");
		Button btnNextPuzzle = new Button("Next Puzzle");
		Button btnCancel = new Button("Cancel");
		hBoxButtons.getChildren().addAll(btnNextPuzzle, btnCancel);
		
		// Goes to next puzzle and removes this popin box
		btnNextPuzzle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameMan.removeAlertPopIn();
				gameMan.loadNewPuzzle();
		}});
		
		// Just removes this popin box
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameMan.removeAlertPopIn();
			}
		});
		
		vBox.getChildren().addAll(lblTitle, lblMessage, hBoxButtons);
		
		return vBox;
	}
}
