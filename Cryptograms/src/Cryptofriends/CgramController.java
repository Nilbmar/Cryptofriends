package Cryptofriends;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.PuncBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
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
	private int hilightedSpaceID = 0;
	
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
	private MenuItem dislplayLetter;
	
	@FXML
	private MenuItem displayMistakes;
	
	@FXML
	private MenuItem displayAllLetters;
	
	@FXML
	private Button btnClearIncorrectYes;

	@FXML
	private Button btnClearIncorrectNo;
	
	@FXML
	private AnchorPane anchorDisplayItems;
	
	@FXML
	private VBox vboxDisplayItems;
	
	@FXML
	private HBox hboxClearIncorrect;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
		hboxClearIncorrect.setManaged(true);
		showClearIncorrectBtns();
	}
	
	public void showClearIncorrectBtns() {
		if (hboxClearIncorrect.isVisible()) {
			hboxClearIncorrect.setVisible(false);
			
			vboxDisplayItems.setPrefHeight(55);
		} else {
			hboxClearIncorrect.setVisible(true);
			vboxDisplayItems.setPrefHeight(90);
		}
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
				//((PuncBox) spaceBox).setAlignment();
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
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			spaceBox.setCSS(false, false);
		}
		
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
		for (int x = 0; x < letterBoxes.size(); x++) {
			letterBoxes.get(x).setCSS(false,  false);
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
		
		//checkForSolved();
	}
	
	public void moveSelection(KeyCode keyCode) {
		int spacesToAdjust = 0;
		
		switch (keyCode) {
		case UP:
			spacesToAdjust = -1;
			moveHilightVertically(spacesToAdjust);
			break;
		case DOWN:
			spacesToAdjust = 1;
			moveHilightVertically(spacesToAdjust);
			break;
		case LEFT:
			spacesToAdjust = -1;
			moveHilightHorizontally(hilightedSpaceID, spacesToAdjust);
			break;
		case RIGHT:
			spacesToAdjust = 1;
			moveHilightHorizontally(hilightedSpaceID, spacesToAdjust);
			break;
		default:
			break;
		}
		
		
	}
	
	private void moveHilightVertically(int spacesToAdjust) {
		int nextIndex = -1;
		int origLine = flow.lineOfSpaceBox(hilightedSpaceID);
		int newLine = origLine + spacesToAdjust;
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		
		if (newLine >= 0 && newLine < flow.lines()) {
			int origPos = flow.positionOnLine(origLine, hilightedSpaceID);
			try {
				SpaceBox newSpaceBox = flow.spaceInPosOnLine(newLine, origPos);
				if (newSpaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
					nextIndex = letterBoxes.indexOf(newSpaceBox);
					
					if (nextIndex >= 0 && nextIndex < letterBoxes.size()) {
						letterBoxes.get(nextIndex).setSelected();
					}
				} else {

					boolean moveForward = true;
					newSpaceBox = getNextLetterBox(newSpaceBox.getSpace().getID(), moveForward);
					// TODO: RETURNING NULL HOW TO SET IT INSTEAD?
					
					nextIndex = letterBoxes.indexOf(newSpaceBox);
					
					
					// GET THE INDEX IN LETTERBOXES OF SPACEBOX WITH THE RETURNED ID NUMBER
					if (nextIndex >= 0 && nextIndex < letterBoxes.size()) {
						letterBoxes.get(nextIndex).setSelected();
					} else {
						System.out.println("Can't move there");
					}
				}
			} catch (NullPointerException npe) {
				System.out.println("No space found while trying to move vertically");
			}
		}
	}
	
	private SpaceBox getNextLetterBox(int idToMoveFrom, boolean forward) {
		// Loop through SpaceBox's to find the space to move from
		// Then the next SpaceBox that contains a lettr is returned
		SpaceBox nextLetterBox = null;
		ArrayList<SpaceBox> spaceBoxes = flow.getSpaceBoxes();
		int lastIndex = spaceBoxes.size();
		int x; // allow movement forward or back
		boolean loop = true; // Used to short circuit the loop after finding next letter
		boolean setAtNextLetter = false;
		
		// Using a while loop so I can control whether to move forward or backward
		// forward - x is set to zero and goes up to the size of the ArrayList
		// backward - x is set to the size of the ArrayList and it counts down to zero
		if (forward) { x = 0; } else { x = lastIndex; lastIndex = 0; }
		
		while (x != lastIndex && loop) {
			SpaceBox spaceBox = spaceBoxes.get(x);
			if (idToMoveFrom == spaceBox.getSpace().getID()) {
				// Set next iterations looking for a Letter
				setAtNextLetter = true;
			}
			
			if (setAtNextLetter && spaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
					nextLetterBox = spaceBox;
					loop = false;
			}
			// control movement forward or back
			if (forward) { x++; } else { x--; }
		}

		return nextLetterBox;
	}
	
	private void moveHilightHorizontally(int spaceToMoveFrom, int spacesToAdjust) {
		int nextIndex = -1;
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		
		for (SpaceBox spaceBox : letterBoxes) {
			if (spaceToMoveFrom == spaceBox.getSpace().getID()) {
				nextIndex = letterBoxes.indexOf(spaceBox) + spacesToAdjust;
			}
		}
		
		if (nextIndex >= 0 && nextIndex < letterBoxes.size()) {
			letterBoxes.get(nextIndex).setSelected();
		}
	}
	
	public void updateHilights(int id) {
		hilightedSpaceID = id;
		// Tells each SpaceBox to hilight
		// if it's space is selected (set to hilight)
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			spaceBox.setCSS(letterSpace.getHilight(), false);
		}
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
				PuncBox puncBox = new PuncBox(space, this);
				spaceBox = puncBox;
			} else {
				spaceBox = new SpaceBox(space, this);
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
}
