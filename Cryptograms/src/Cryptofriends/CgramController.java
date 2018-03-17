package Cryptofriends;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.PuncBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.GameManager;
import core.Loaders.PuzzleLoader;
import core.Spaces.LetterSpace;
import core.Spaces.Phrase;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import core.Spaces.Word;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class CgramController {
	private GameManager gameMan;
	private int puzzleIndex = 0;
	private int hilightedSpaceID = 0;
	//private ArrayList<SpaceBox> letterBoxes = new ArrayList<SpaceBox>();
	
	@FXML
	private FlowBox flow; //FlowPane flow;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private MenuItem newPuzzle;
	
	@FXML
	private MenuItem clearPuzzle;
	
	@FXML
	private MenuItem mistakesMenu;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
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
		flow.setDisable(true);
	}
	
	private ArrayList<Boolean> getIncorrectSpaces() {
		ArrayList<Boolean> incorrectSpaces = new ArrayList<Boolean>();
		
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			Space space = spaceBox.getSpace();
			if (space.getSpaceType() == SpaceType.LETTER) {
				incorrectSpaces.add(!((LetterSpace) space).isCorrect());
			}
		}
		
		return incorrectSpaces;
	}
	
	private void checkForSolved() {
		// Check to see if any space still contains a wrong answer
		// if no spaces contain a wrong answer
		// go to win condition
		// Only WIN if does not contain true
		ArrayList<Boolean> incorrectSpaces = getIncorrectSpaces();
		if (!incorrectSpaces.contains(true)) {
			puzzleSolved();
		}
	}

	public void hilightIncorrect() {
		ArrayList<Boolean> incorrectSpaces = getIncorrectSpaces();
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		
		for (int x = 0; x < incorrectSpaces.size(); x++) {
			
			if (incorrectSpaces.get(x)) {
				letterBoxes.get(x).setCSS(true, true);
			}
		}
		System.out.println("incorrectSpaces size: " + incorrectSpaces.size()
				+ " all spaces size: " + letterBoxes.size());
	}
	
	public void setAnswer(String answer) {
		// Tells each selected SpaceBox to
		// set answer label to letter input
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			if (letterSpace.getHilight()) {
				letterSpace.setCurrentChar(answer.charAt(0));
				spaceBox.setAnswerCharLabel(true);
			}
		}
		
		checkForSolved();
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
				spaceBox.clear();
			}
		}
	}
	
	public void loadNewPuzzle() {
		// Clear game board
		flow.setDisable(false);
		flow.clear();
		
		// Create new game board
		String puzzlePhrase = null;
		PuzzleLoader pLoader = new PuzzleLoader();
		pLoader.setTarget(Integer.toString(puzzleIndex));
		
		try {
			puzzlePhrase = pLoader.getPhrase();
			setupPuzzle(gameMan.getPuzzle(puzzlePhrase));
			
			// Keep the "next puzzle" updated
			puzzleIndex++;
			
			// Reset alignment for punctuation
			setupPuncAlignment();
			System.out.println("new puzzle letterBoxes size: " + flow.getLetterBoxes().size());
		}
		catch (NullPointerException nullEx) {
			System.out.println("Null Pointer: Reseting to start of puzzle file");
			puzzleIndex = 0;
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
	
	public void setupPuzzle(Phrase phrase) {
		for (Word word : phrase.getPhrase()) {
			addWord(word);
		}
	}
	
	public void createBoard() {
		flow = new FlowBox();
		flow.setSpacesPerLine(15);
		//flow.setMinSize(355, 448);
		//flow.setPrefSize(355, 448);
		anchor.getChildren().add(flow);
	}
}
