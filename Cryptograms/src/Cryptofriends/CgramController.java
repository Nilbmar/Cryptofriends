package Cryptofriends;

import java.util.ArrayList;

import Cryptofriends.GUI.PlayerMenu;
import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import core.Data.Player;
import core.Managers.GameManager;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CgramController {
	private GameManager gameMan;
	/*
	private PuzzleManager puzzleMan = new PuzzleManager();
	private PuzzleLoader sqlLoader = new PuzzleLoader(puzzleMan);
	private int puzzleIndex = 0;
	*/
	
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
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
		
		// Allows setting the colors for these panels
		hboxScorePanel.setManaged(true);
		hboxClearIncorrect.setManaged(true);
		
		// Start with hboxClearIncorrect hidden
		// and default color for panels
		showClearIncorrectBtns();
	}
	
	public void showClearIncorrectBtns() {
		if (hboxClearIncorrect.isVisible()) {
			// Hides hboxClearIncorrect
			// Show user data
			// and set default color
			if (gameMan.getPlayerManager() != null) {
				try {
					updatePlayerInfoBox();
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
	public void updatePlayerInfoBox() {
		Player player = gameMan.getPlayerManager().getCurrentPlayer();
		
		if (player != null) {
			lblPlayerName.setText(player.getName());
			String playerKey = "Player " + player.getPlayerNum();
			float score = gameMan.getScoreManager().getPlayerScoreData(playerKey).getScore();
			lblScore.setText("" + score);
		}
	}
	
	// Created separate function
	// So GameManager can call after creating first player
	// because it can't use the addPlayer() function
	// that needs the yet-to-be-constructed GameManager
	public void addPlayerMenuItem(Player player) {
		if (player != null) {
			int playerNum = player.getPlayerNum();
			System.out.println("Number of players is : " + playerNum);
			
			PlayerMenu playerMenuItem = new PlayerMenu(this, player);
			playerMenu.getItems().add(playerMenuItem);
		}
	}
	
	public void addPlayer() { gameMan.addPlayer(); }
	
	public void renamePlayer(int numOfPlayer) {
		gameMan.renamePlayer(numOfPlayer);
		updatePlayerInfoBox();
	}
	
	public void removePlayer(int numOfPlayer) {
		gameMan.removePlayer(numOfPlayer);
		updatePlayerInfoBox();
	}
	
	public void switchPlayer() {
		gameMan.switchPlayer();
		updatePlayerInfoBox();
	}
	
	public void checkForSolved() {
		gameMan.getAnswerManager().checkForSolved();
	}

	public void clearLetter() {
		gameMan.getAnswerManager().setAnswer(" ");
	}
	
	// TODO: MAIN is using this function for letter keys
	// figure out how to move letter keys somewhere else
	// that can have access to AnswerManager
	public void setAnswer(String answer) {
		gameMan.getAnswerManager().setAnswer(answer);
	}
	
	public void clearIncorrect() {
		gameMan.getAnswerManager().clearIncorrect();
		showClearIncorrectBtns();
	}
	
	public void hilightIncorrect() {
		// Only needs to run if something has been selected on this puzzle
		// And there are items that are incorrect
		SpaceBox selectedBox = flow.getCurrentlySelected();
		int numFilled = gameMan.getAnswerManager().getFilledSpaceBoxes().size();
		int numIncorrect = gameMan.getAnswerManager().getIncorrectSpaceBoxes().size();
		
		if (selectedBox != null && numFilled > 0 && numIncorrect > 0) {
			gameMan.getAnswerManager().hilightIncorrect();
			showClearIncorrectBtns();
		}
	}
	
	public void displayLetter() {
		gameMan.getAnswerManager().displayLetter();
		
		updatePlayerInfoBox();
	}
	
	public void displayAllLetters() {
		gameMan.getAnswerManager().displayAllLetters();
	}
	

	
	/* Allows movement between SpaceBoxes with arrow keys
	 * moveHilightVertically for Up (-1) / Down (1)
	 * moveHilightHorizontally for Left (-1) / Right (1)
	 */
	public void moveSelection(KeyCode keyCode) {
		int spacesToAdjust = 0;
		int selectedID = flow.getCurrentlySelected().getSpace().getID();
		
		switch (keyCode) {
		case UP:
			spacesToAdjust = -1;
			gameMan.getBoardManager().moveHilightVertically(selectedID, spacesToAdjust);
			break;
		case DOWN:
			spacesToAdjust = 1;
			gameMan.getBoardManager().moveHilightVertically(selectedID, spacesToAdjust);
			break;
		case LEFT:
			spacesToAdjust = -1;
			gameMan.getBoardManager().moveHilightHorizontally(selectedID, spacesToAdjust);
			break;
		case RIGHT:
			spacesToAdjust = 1;
			gameMan.getBoardManager().moveHilightHorizontally(selectedID, spacesToAdjust);
			break;
		default:
			break;
		}
		
		
	}


	
	
	
	
	
	
	public void updateHilights(int id) {
		// TODO:
		// This function is called by SpaceBox
		// Might need to change how SpaceBox runs the update
		// give it access to AnswerManager???
		gameMan.getAnswerManager().updateHilights(id);
	}
	
	public void clearPuzzle() {
		gameMan.getBoardManager().clearPuzzle();
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
