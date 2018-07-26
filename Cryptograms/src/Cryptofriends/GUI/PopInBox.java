package Cryptofriends.GUI;

import core.Data.PuzzleData;
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
		
		//vBox.setMaxWidth(300);
		
	}
	
	public VBox clearIncorrect(GameManager gameMan) {
		vBox.setMaxHeight(100);
		vBox.setStyle("-fx-background-color: #fc4a1a;"
				+" -fx-spacing: 10;");
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
	
	public VBox winnerBox(GameManager gameMan, PuzzleData puzzleData, String solvedBy) {
		vBox.setMaxHeight(350);
		
		// Box to hold the quote, subject, and author name
		VBox quoteBox = new VBox();
		quoteBox.setAlignment(Pos.CENTER);
		quoteBox.setMaxHeight(200);
		quoteBox.setPrefHeight(200);
		quoteBox.setStyle("-fx-background-color: #4abdac;"
				+ " -fx-spacing: 25;");
		
		VBox citationBox = new VBox();
		citationBox.setMaxWidth(300);
		citationBox.setAlignment(Pos.CENTER_RIGHT);
		citationBox.setStyle(" -fx-spacing: 5;");
		
		Label lblQuote = new Label(puzzleData.getPhrase());
		lblQuote.setMaxWidth(300);
		lblQuote.setWrapText(true);
		
		Label lblSubject = new Label(puzzleData.getSubject());
		Label lblAuthor = new Label(puzzleData.getAuthor());
		
		citationBox.getChildren().addAll(lblSubject, lblAuthor);
		
		quoteBox.getChildren().addAll(lblQuote, citationBox);
		
		// Create a blank gap between Quote box and Question Box
		VBox gapBox = new VBox();
		gapBox.setAlignment(Pos.CENTER);
		gapBox.setMaxHeight(50);
		gapBox.setPrefHeight(50);
		
		// Box with button input
		VBox questionBox = new VBox();
		questionBox.setAlignment(Pos.CENTER);
		questionBox.setMaxHeight(100);
		questionBox.setPrefHeight(100);
		questionBox.setStyle("-fx-background-color: #4abdac;"
				+" -fx-spacing: 10;");
		
		Label lblCongrats = new Label("Congratulations");
		Label lblWonBy = new Label("Puzzle was solved by " + solvedBy);
		
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
		
		questionBox.getChildren().addAll(lblCongrats, lblWonBy, hBoxButtons);
		vBox.getChildren().addAll(quoteBox, gapBox, questionBox);
		return vBox;
	}
}
