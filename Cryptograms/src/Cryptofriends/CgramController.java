package Cryptofriends;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Cryptofriends.GUI.PlayerMenu;
import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.PuncBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.Data.Player;
import core.Data.Puzzle;
import core.Data.PuzzleData;
import core.Data.PuzzleState;
import core.Loaders.PuzzleLoader;
import core.Managers.GameManager;
import core.Managers.PuzzleManager;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import core.Spaces.Word;
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
			moveHilightVertically(selectedID, spacesToAdjust);
			break;
		case DOWN:
			spacesToAdjust = 1;
			moveHilightVertically(selectedID, spacesToAdjust);
			break;
		case LEFT:
			spacesToAdjust = -1;
			moveHilightHorizontally(selectedID, spacesToAdjust);
			break;
		case RIGHT:
			spacesToAdjust = 1;
			moveHilightHorizontally(selectedID, spacesToAdjust);
			break;
		default:
			break;
		}
		
		
	}

	/* Used when moving through LetterSpaces
	 * with UP and DOWN arrow keys
	 * if there is no LetterSpace in the same position
	 * on the new line, find the closest LetterSpace
	 */
	private SpaceBox getClosestLetter(ArrayList<SpaceBox> newLine, SpaceBox spaceBox, int origPos) {
		ArrayList<SpaceBox> letterBoxesOnNewLine = new ArrayList<SpaceBox>();
		SpaceBox spaceBoxToReturn = null;
		int spaceBoxID = spaceBox.getSpace().getID();			
		Space space = null;
		
		// Try to go forward first
		if(origPos > 0 && origPos < newLine.size()) {
			// Going backward would cause a NullPointer so go forward
			// but make sure it's still on the newLine
			// Finds the first LetterSpace after the space trying to move from
			for (SpaceBox currentSpaceBox : newLine) {
				space = currentSpaceBox.getSpace();
				if (space.getSpaceType() == SpaceType.LETTER && space.getID() > spaceBoxID) {
					return currentSpaceBox;
				}
			}
		}

		// Ensure not returning a null SpaceBox
		// without this, it could return a blank or punctuation space
		// if it tried to move to the right but no LetterSpaces where available
		if (spaceBoxToReturn == null) {
			// Add LetterSpaces with an ID lower than the one trying to move from
			// Ensures the last item in the array is the LetterSpace just before
			// the space trying to move from
			for (SpaceBox currentSpaceBox : newLine) {
				space = currentSpaceBox.getSpace();
				if (space.getSpaceType() == SpaceType.LETTER && space.getID() < spaceBoxID) {
					letterBoxesOnNewLine.add(currentSpaceBox);
				}
			}
			// The LetterSpace closest will be the last one in the array
			spaceBoxToReturn = letterBoxesOnNewLine.get(letterBoxesOnNewLine.size() - 1);
		}
		
		return spaceBoxToReturn;
	}
	
	
	/* Move between LetterSpaces with LEFT and RIGHT arrow keys
	 * If at the beginning of a line, and used LEFT, will go up
	 * to previous line and select the last LetterSpace on it
	 * If at the end of a line, and used RIGHT, will go down
	 * to next line and select the first LetterSpace on it
	 */
	private void moveHilightHorizontally(int spaceToMoveFrom, int spacesToAdjust) {
		int nextIndex = -1;
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		
		// Finds the index of the original space in the LetterBox array
		// and moves to previous or next index to select the new LetterBox
		// spacesToAdjust is positive or negative based on if you need
		// to move forward or backward
		for (SpaceBox spaceBox : letterBoxes) {
			if (spaceToMoveFrom == spaceBox.getSpace().getID()) {
				nextIndex = letterBoxes.indexOf(spaceBox) + spacesToAdjust;
			}
		}
		
		// Make sure index does exist and toggle the selection on for that SpaceBox
		if (nextIndex >= 0) {
			if (nextIndex < letterBoxes.size()) {
				// Within range handle normally
				flow.clearSelection();
				letterBoxes.get(nextIndex).toggleSelection();
			} else if (nextIndex >= letterBoxes.size()) {
				// If moving right from the last LetterSpace
				// go to the first LetterSpace on the first line
				flow.clearSelection();
				letterBoxes.get(0).toggleSelection();
			}
		} else if (nextIndex < 0) {
			// Allow cycling backward from first LetterSpace on first line
			// to last LetterSpace on last line
			flow.clearSelection();
			letterBoxes.get(letterBoxes.size() - 1).toggleSelection();
		}
	}
	
	private void moveHilightVertically(int selectedID, int spacesToAdjust) {
		int origLineNum = flow.lineOfSpaceBox(selectedID);
		int newLineNum = origLineNum + spacesToAdjust;
		ArrayList<SpaceBox> newLine = null;
		
		if (newLineNum >= 0 && newLineNum < flow.lines()) {
			newLine = flow.spaceBoxesOnLine(newLineNum);
			int origPos = flow.positionOnLine(origLineNum, selectedID);

			// If origPos exists on the newLine, and is a LetterSpace, select it
			// Else get closest letter to that position on the newLine
			if (origPos < newLine.size()) {
				SpaceBox spaceBox = newLine.get(origPos);
				if (spaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
					flow.clearSelection();
					newLine.get(origPos).toggleSelection();
				} else {
					spaceBox = getClosestLetter(newLine, spaceBox, origPos);
					flow.clearSelection();
					spaceBox.toggleSelection();
				}
			} else {
				// Can't get a SpaceBox on the new line at origPos
				// so it gets the last SpaceBox on the newLine
				SpaceBox spaceBox = newLine.get(newLine.size() - 1);
				spaceBox = getClosestLetter(newLine, spaceBox, origPos);
				flow.clearSelection();
				spaceBox.toggleSelection();
			}
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
		gameMan.getBoardBuilder().clearPuzzle();
	}
	
	
	public void loadRandomPuzzle() {
		gameMan.loadRandomPuzzle();
	}
	
	public void loadNewPuzzle() {
		gameMan.loadNewPuzzle();
	}
		
	public void createBoard() {
		gameMan.createBoard();
		flow = gameMan.getBoardBuilder().getFlowBox();
		anchor.getChildren().add(flow);
	}
		
	public void exitProgram() {
		System.out.println("Exiting Cyrptofriends.");
		Platform.exit();
	}
}
