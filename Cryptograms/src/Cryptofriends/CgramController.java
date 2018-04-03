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
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
	private VBox vboxBottomPanel;
	
	@FXML
	private HBox hboxBottomPanel;
	
	@FXML
	private HBox hboxClearIncorrect;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
		hboxBottomPanel.setManaged(true);
		hboxClearIncorrect.setManaged(true);
		showClearIncorrectBtns();
	}
	
	public void showClearIncorrectBtns() {
		if (hboxClearIncorrect.isVisible()) {
			vboxDisplayItems.setStyle("-fx-background-color: #4abdac;");
			vboxBottomPanel.setStyle("-fx-background-color: #4abdac;");
			hboxBottomPanel.setVisible(true);
			hboxClearIncorrect.setVisible(false);
		} else {
			vboxDisplayItems.setStyle("-fx-background-color: #fc4a1a;");
			vboxBottomPanel.setStyle("-fx-background-color: #fc4a1a;");
			hboxClearIncorrect.setVisible(true);
			hboxBottomPanel.setVisible(false);
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

	private SpaceBox getNextLetterBox(int idToMoveFrom, boolean forward) {
		SpaceBox nextLetterBox = null;
		
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
		
		flow.clearSelection();
		
		if (nextIndex >= 0 && nextIndex < letterBoxes.size()) {
			letterBoxes.get(nextIndex).toggleSelection();
		}
	}
	
	private void moveHilightVertically(int spacesToAdjust) {
		int origLineNum = flow.lineOfSpaceBox(hilightedSpaceID);
		int newLineNum = origLineNum + spacesToAdjust;
		ArrayList<SpaceBox> newLine = null;
		ArrayList<SpaceBox> letterBoxesOnNewLine = new ArrayList<SpaceBox>();
		
		if (newLineNum >= 0 && newLineNum < flow.lines()) {
			newLine = flow.spaceBoxesOnLine(newLineNum);
						
			int origPos = flow.positionOnLine(origLineNum, hilightedSpaceID);

			// If that number exists on the line, select it
			if (origPos < newLine.size()) {
				SpaceBox spaceBox = newLine.get(origPos);
				if (spaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
					flow.clearSelection();
					newLine.get(origPos).toggleSelection();
				} else {
					// The same space on new line is not a letter
					// Find a nearby letter on the new line
					int spaceBoxID = spaceBox.getSpace().getID();					
					
					// Try to go backward first
					// More likely able to go back than forward
					Space space = null;
					if (origPos >= 1) {
						for (SpaceBox currentSpaceBox : newLine) {
							space = currentSpaceBox.getSpace();
							if (space.getSpaceType() == SpaceType.LETTER && space.getID() < spaceBoxID) {
								letterBoxesOnNewLine.add(currentSpaceBox);
							}
						}
						
						spaceBox = letterBoxesOnNewLine.get(letterBoxesOnNewLine.size() - 1);
					} else if(origPos < newLine.size()) {
						for (SpaceBox currentSpaceBox : newLine) {
							space = currentSpaceBox.getSpace();
							if (space.getSpaceType() == SpaceType.LETTER && space.getID() > spaceBoxID) {
								letterBoxesOnNewLine.add(currentSpaceBox);
							}
						}
						
						spaceBox = letterBoxesOnNewLine.get(0);
					}
					
					flow.clearSelection();
					spaceBox.toggleSelection();
				}
			}
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
		/*
		Pane takeUpSpaceAtBottom = new Pane();
		takeUpSpaceAtBottom.setManaged(false);
		takeUpSpaceAtBottom.setMinWidth(200);
		takeUpSpaceAtBottom.setMinHeight(50);
		anchor.getChildren().add(takeUpSpaceAtBottom);
		*/

		puzzleIndex = 1;
		sqlLoader.setTarget(Integer.toString(puzzleIndex));
		sqlLoader.load();
	}
}
