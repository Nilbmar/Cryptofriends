package Cryptofriends;

import Cryptofriends.GUI.PlayerMenu;
import Cryptofriends.SpaceContainer.FlowBox;
import Enums.MoveDirections;
import core.Data.Player;
import core.Managers.GameManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CgramController {
	private Scene scene;
	private GameManager gameMan;
	
	@FXML
	private StackPane stack;
	
	@FXML
	private FlowBox flow;
	
	@FXML
	private Label lblAuthor;
	
	@FXML
	private Label lblSubject;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private MenuItem newPuzzle;
	
	@FXML
	private MenuItem clearPuzzle;
	
	@FXML
	private MenuItem randomPuzzle;
	
	@FXML
	private MenuItem exitProgram;
	
	@FXML
	private MenuItem dislplayLetter;
	
	@FXML
	private MenuItem displayMistakes;
	
	@FXML
	private MenuItem displayAllLetters;
	
	@FXML
	private MenuItem nextPlayer;
	
	@FXML 
	private MenuItem addPlayer;
	
	@FXML
	private Menu playerMenu;
	
	@FXML
	private Button btnClearIncorrectYes;

	@FXML
	private Button btnClearIncorrectNo;
	
	@FXML
	private AnchorPane anchorDisplayItems;
	
	@FXML
	private VBox vboxDisplayItems;
	
	@FXML
	private VBox vboxBottomPanel;
	
	@FXML
	private HBox hboxScorePanel;
	
	@FXML
	private Label lblPlayerName;
	
	@FXML
	private Label lblTime;
	
	@FXML
	private Label lblScore;
	
	@FXML
	private HBox hboxClearIncorrect;
	
	private boolean stageIsShowing = false;
	
	public boolean isStageShowing() { return stageIsShowing; }
	public void setStageShowing(boolean showing) { 
		stageIsShowing = showing;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		
		// Setup of Key Inputs
		this.scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
        	
        	// Letter Input
        	if (keyEvent.getCode().isLetterKey()) {
            	gameMan.setAnswer(keyEvent.getCode().toString());
            }
        	
        	if (keyEvent.getCode().isArrowKey()) {
        		moveSelection(keyEvent.getCode());
        	}
        	
        	if (keyEvent.getCode() == KeyCode.DELETE) {
        		gameMan.setAnswer(" ");
        	}
        });
	}
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
		
		// Allows setting the colors for these panels
		hboxScorePanel.setManaged(true);
		hboxClearIncorrect.setManaged(true);
		
		// Start with hboxClearIncorrect hidden
		// and default color for panels
		showClearIncorrectBtns();
	}
	
	public StackPane getStackPane() {
		return stack;
	}
	
	public Label getTimeLabel() { return lblTime; }
	
	public void showClearIncorrectBtns() {
		if (hboxClearIncorrect.isVisible()) {
			// Hides hboxClearIncorrect
			// Show user data
			// and set default color
			if (gameMan.getPlayerManager() != null) {
				try {
					gameMan.updatePlayerInfoBox();
				} catch (NullPointerException nullEx) {
					System.out.println("No player name yet.");
				}
			}
			
			vboxDisplayItems.setStyle("-fx-background-color: #4abdac;");
			vboxBottomPanel.setStyle("-fx-background-color: #4abdac;");
			hboxScorePanel.setVisible(true);
			hboxClearIncorrect.setVisible(false);
		} else {
			// Shows hboxClearIncorrect and set warning color
			vboxDisplayItems.setStyle("-fx-background-color: #fc4a1a;");
			vboxBottomPanel.setStyle("-fx-background-color: #fc4a1a;");
			hboxClearIncorrect.setVisible(true);
			hboxScorePanel.setVisible(false);
		}
	}
	
	
	public void updateAuthorLine(String author, String subject) {
		lblAuthor.setText(author);
		lblSubject.setText(subject);
	}
	
	// Changes the panel on the bottom that holds
	// player name, score, and time
	public void updatePlayerInfoBox(String playerName, int score) {
		lblPlayerName.setText(playerName);
		lblScore.setText(score + "%");
	}
	
	// Created separate function
	// So GameManager can call after creating first player
	// because it can't use the addPlayer() function
	// that needs the yet-to-be-constructed GameManager
	public void addPlayerMenuItem(Player player) {
		if (player != null) {			
			PlayerMenu playerMenuItem = new PlayerMenu(this, player);
			playerMenu.getItems().add(playerMenuItem);
		}
	}
	
	public void addPlayer() { gameMan.addPlayer(); }
	
	public void renamePlayer(int numOfPlayer) {
		gameMan.renamePlayer(numOfPlayer);
	}
	
	public void removePlayer(int numOfPlayer) {
		gameMan.removePlayer(numOfPlayer);
	}
	
	public void switchPlayer() {
		gameMan.switchPlayer();
	}
	
	
	/* Reveal Buttons */
	public void displayLetter() {
		gameMan.displayLetter();
	}
	
	public void displayAllLetters() {
		gameMan.displayAllLetters();
	}
	
	public void hilightIncorrect() {
		gameMan.hilightIncorrect();
	}
	
	public void clearIncorrect() {
		gameMan.clearIncorrect();
	}
	
	// Allows movement between SpaceBoxes with arrow keys
	public void moveSelection(KeyCode keyCode) {
		switch (keyCode) {
		case UP:
			gameMan.moveSelection(MoveDirections.UP);
			break;
		case DOWN:
			gameMan.moveSelection(MoveDirections.DOWN);
			break;
		case LEFT:
			gameMan.moveSelection(MoveDirections.LEFT);
			break;
		case RIGHT:
			gameMan.moveSelection(MoveDirections.RIGHT);
			break;
		default:
			break;
		}
	}
	
	public void clearPuzzle() {
		gameMan.clearPuzzle();
	}
	
	
	public void loadRandomPuzzle() {
		gameMan.loadRandomPuzzle();
	}
	
	public void loadNewPuzzle() {
		gameMan.loadNewPuzzle();
	}
		
	public void createBoard() {
		gameMan.createBoard();
		flow = gameMan.getBoardManager().getFlowBox();
		anchor.getChildren().add(flow);
	}
		
	public void exitProgram() {
		System.out.println("Exiting Cyrptofriends.");
		Platform.exit();
	}
}
