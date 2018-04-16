package Cryptofriends;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CgramController {
	private GameManager gameMan;
	private PuzzleManager puzzleMan = new PuzzleManager();
	private PuzzleLoader sqlLoader = new PuzzleLoader(puzzleMan);
	private int puzzleIndex = 0;
	
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
	private MenuItem renamePlayer;
	
	@FXML 
	private MenuItem removePlayer;
	
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
				String playerName = gameMan.getPlayerManager().getCurrentPlayer().getName();
				lblPlayerName.setText(playerName);
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
	
	// Changes the panel on the bottom that holds
	// player name, score, and time
	private void updatePlayerInfoBox(String playerName) {
		if (playerName != null && !playerName.isEmpty()) {
			lblPlayerName.setText(playerName);
		}
	}
	
	public void addPlayer() {
		// Returned so it can be added to menu
		Player playerAdded = gameMan.getPlayerManager().addPlayer();
	}
	
	public void renamePlayer() {
		System.out.println("Rename Player");
		String newName = gameMan.getPlayerManager().renamePlayer();
		
		updatePlayerInfoBox(newName);
	}
	
	public void removePlayer() {
		System.out.println("Remove Player");
		gameMan.getPlayerManager().removePlayer();
		String newCurrentPlayer = gameMan.getPlayerManager().getCurrentPlayer().getName(); 
		
		updatePlayerInfoBox(newCurrentPlayer);
	}
	
	public void switchPlayer() {
		System.out.println("Switch Player");
		String newCurrentPlayer = gameMan.getPlayerManager().switchPlayer();
		
		updatePlayerInfoBox(newCurrentPlayer);
	}
	
	private void setupPuncAlignment() {
		ArrayList<SpaceBox> spaceBoxes = flow.getSpaceBoxes();
		
		
		SpaceBox spaceBox = null;
		Space space = null;
		SpaceType prevType = null;
		SpaceType nextType = null;
		int spaceBoxesSize = spaceBoxes.size();
		int nextSpaceIndex = -1;
		
		for (int x = 0; x < spaceBoxesSize; x++) {
			spaceBox = spaceBoxes.get(x);
			space = spaceBox.getSpace();
			
			if (space.getSpaceType() == SpaceType.PUNC) {
				((PuncBox) spaceBox).setPrevType(prevType);
				nextSpaceIndex = x + 1;
				if (spaceBoxesSize > nextSpaceIndex) {
					nextType = spaceBoxes.get(nextSpaceIndex).getSpace().getSpaceType();
				} else {
					nextType = null;
				}
				((PuncBox) spaceBox).setNextType(nextType);
			}
			
			// Set prevType to be used in the next iteration
			prevType = space.getSpaceType();
		}
		
		// Finally setting alignment
		for (SpaceBox puncBox : spaceBoxes) {
			if (puncBox.getSpace().getSpaceType() != SpaceType.PUNC) {
				puncBox.setAlignment();
			} else {
				((PuncBox) puncBox).setAlignment();
			}
		}
	}
	
	
	
	private void puzzleSolved() {
		System.out.println("Congratulations! You won the game!");
		
		// Clears hilights and selection box
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			spaceBox.setCSS(false, false);
		}
		
		// Disable puzzle so it can't be edited
		gameMan.setPuzzleState(PuzzleState.WON);
		flow.setDisable(true);
	}
	
	private ArrayList<SpaceBox> getIncorrectSpaceBoxes() {
		ArrayList<SpaceBox> incorrectSpaceBoxes = new ArrayList<SpaceBox>();
		
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			Space space = spaceBox.getSpace();
			if (space.getSpaceType() == SpaceType.LETTER) {
				if (!((LetterSpace) space).isCorrect()) {
					incorrectSpaceBoxes.add(spaceBox);
				}
			}
		}
		
		return incorrectSpaceBoxes;
	}
	
	public void checkForSolved() {
		// Check to see if any space still contains a wrong answer
		// if no spaces contain a wrong answer
		// go to win condition
		// Only WIN if does not contain a spacebox
		if (gameMan.getPuzzleState() == PuzzleState.PLAYING) {
			ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
			if (incorrectSpaceBoxes.size() == 0) {
				puzzleSolved();
			}
		}
	}
	
	private void clearHilights() {
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		SpaceBox selectedBox = getCurrentlySelected();
		for (int x = 0; x < letterBoxes.size(); x++) {
			letterBoxes.get(x).setCSS(false,  false);
		}
		
		// Make sure the original selected box remains selected
		flow.clearSelection();
		if (selectedBox != null) {
			selectedBox.toggleSelection();
		}
	}

	public void clearLetter() {
		setAnswer(" ");
	}
	
	public void clearIncorrect() {
		ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
		LetterSpace letterSpace = null;
		for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
			letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
			letterSpace.setCurrentChar(' ');
			incorrectSpaceBoxes.get(x).setAnswerCharLabel(true);
		}
		
		clearHilights();
		showClearIncorrectBtns();
	}
	
	public void hilightIncorrect() {
		// Only needs to run if something has been selected on this puzzle
		// Otherwise, the last SpaceBox would be hilighted by clearHilights()
		SpaceBox selectedBox = getCurrentlySelected();
		if (selectedBox != null) {
			clearHilights();
			
			ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
			LetterSpace letterSpace = null;
			for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
				letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
				if (!letterSpace.isBlank()) {
					incorrectSpaceBoxes.get(x).setCSS(true, true);	
				}	
			}
	
			showClearIncorrectBtns();
		}
	}
	
	public void displayLetter() {
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			if (letterSpace.getHilight()) {
				letterSpace.setCurrentChar(letterSpace.getCorrectChar());
				spaceBox.setAnswerCharLabel(true);
				spaceBox.setCSS(false,  false);
				spaceBox.setDisable(true);
			}
		}
	}
	
	public void displayAllLetters() {
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			if (!spaceBox.isDisabled()) {
				letterSpace.setCurrentChar(letterSpace.getCorrectChar());
				spaceBox.setAnswerCharLabel(true);
				spaceBox.setCSS(false,  false);
				spaceBox.setDisable(true);
			}
		}
		
		gameMan.setPuzzleState(PuzzleState.FAILED);
		flow.setDisable(true);
	}
	
	public void setAnswer(String answer) {
		// Tells each selected SpaceBox to
		// set answer label to letter input
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			if (!spaceBox.isDisabled()) {
				letterSpace = (LetterSpace) spaceBox.getSpace();
				if (letterSpace.getHilight()) {
					letterSpace.setCurrentChar(answer.charAt(0));
					spaceBox.setAnswerCharLabel(true);
				}
			}
		}
	}
	
	public SpaceBox getCurrentlySelected() {
		SpaceBox selected = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			if (spaceBox.getSelected()) {
				selected = spaceBox;
			}
		}
		
		return selected;
	}
	
	
	/* Allows movement between SpaceBoxes with arrow keys
	 * moveHilightVertically for Up (-1) / Down (1)
	 * moveHilightHorizontally for Left (-1) / Right (1)
	 */
	public void moveSelection(KeyCode keyCode) {
		int spacesToAdjust = 0;
		int selectedID = getCurrentlySelected().getSpace().getID();
		
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
		//hilightedSpaceID = id;
		// Tells each SpaceBox to hilight
		// if it's space is selected (set to hilight)
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			spaceBox.setCSS(letterSpace.getHilight(), false);
		}
	}
	
	public void loadRandomPuzzle() {
		int numOfPuzzles = puzzleMan.count();
		int puzzleNum = ThreadLocalRandom.current().nextInt(0, numOfPuzzles);
		puzzleIndex = puzzleNum;
		loadNewPuzzle();
	}
	
	public void clearPuzzle() {
		if (!flow.isDisabled()) {
			for (SpaceBox spaceBox : flow.getLetterBoxes()) {
				if (!spaceBox.isDisabled()) {
					spaceBox.clear();
				}
			}
		}
	}
	
	public void loadNewPuzzle() {
		// Clear game board
		flow.setDisable(false);
		flow.clear();
		gameMan.setPuzzleState(PuzzleState.PLAYING);
		
		// Create new game board
		try {			
			PuzzleData puzzleData = puzzleMan.getPuzzle(puzzleIndex);
			setupPuzzle(puzzleData.getPuzzle());
			lblAuthor.setText(puzzleData.getAuthor());
			lblSubject.setText(puzzleData.getSubject());
			
			// Keep the "next puzzle" updated
			puzzleIndex++;
			
			// Reset alignment for punctuation
			setupPuncAlignment();
		}
		catch (NullPointerException nullEx) {
			System.out.println("Null Pointer: Reseting to start of puzzle file");
			puzzleIndex = 1;
			loadNewPuzzle();
		}
	}
	
	public SpaceBox addSpaceBox(Space space) {
		SpaceBox spaceBox = null;
		if (flow != null) {
			if (space.getSpaceType() == SpaceType.PUNC) {
				PuncBox puncBox = new PuncBox(space, this, flow);
				spaceBox = puncBox;
			} else {
				spaceBox = new SpaceBox(space, this, flow);
			}
		}
		return spaceBox;
	}
	
	private void addWord(Word word) {
		WordBox wordBox = new WordBox();
		for (Space space : word.getWord()) {
			switch (space.getSpaceType()) {
			case BLANK:
				wordBox.addSpaceBox(addSpaceBox(space));
				break;
			case LETTER:
				LetterSpace letter = (LetterSpace) space;
				letter.addObserver(gameMan.getSelectionManager().getSelObserver());
				wordBox.addSpaceBox(addSpaceBox(letter));
				break;
			case PUNC:
				wordBox.addSpaceBox(addSpaceBox(space));
				break;
			default:
				break;
			}
		}
		
		flow.addWordBox(wordBox);
	}
	
	public void setupPuzzle(Puzzle puzzle) {
		for (Word word : puzzle.getPhrase()) {
			addWord(word);
		}
	}
	
	public void createBoard() {
		flow = new FlowBox();
		flow.setSpacesPerLine(15);
		anchor.getChildren().add(flow);

		puzzleIndex = 1;
		sqlLoader.setTarget(Integer.toString(puzzleIndex));
		sqlLoader.load();
	}
	
	public void exitProgram() {
		System.out.println("Exiting Cyrptofriends.");
		Platform.exit();
	}
}
