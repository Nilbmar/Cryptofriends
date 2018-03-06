package Cryptofriends;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
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
import javafx.scene.layout.AnchorPane;

public class CgramController {
	private GameManager gameMan;
	private int puzzleIndex = 0;
	private ArrayList<Boolean> incorrectSpaces = new ArrayList<Boolean>();
	private ArrayList<SpaceBox> letterBoxes = new ArrayList<SpaceBox>();
	
	@FXML
	private FlowBox flow; //FlowPane flow;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private MenuItem newPuzzle;
	
	@FXML
	private MenuItem clearPuzzle;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	private void setupLetterBoxArray() {
		//int wordCount = flow.getChildren().size();
		int wordCount = flow.getWordBoxes().size();
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getWordBoxes().get(x);
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				Space space = spaceBox.getSpace();
				if (space.getSpaceType() == SpaceType.LETTER) {
					letterBoxes.add(spaceBox);
				}
			}
		}
	}
	
	private void puzzleSolved() {
		System.out.println("Congratulations! You won the game!");
		flow.setDisable(true);
	}
	
	private void checkForSolved() {
		// Check to see if any space still contains a wrong answer
		// if no spaces contain a wrong answer
		// go to win condition
		
		// important to clear array list every check
		incorrectSpaces.clear();
		
		int wordCount = flow.getWordBoxes().size();
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getWordBoxes().get(x);
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				Space space = spaceBox.getSpace();
				if (space.getSpaceType() == SpaceType.LETTER) {
					incorrectSpaces.add(!((LetterSpace) space).isCorrect());
				}
			}
		}
		
		// true = incorrect answer
		// only solved if list contains no incorrect answers
		if (!incorrectSpaces.contains(true)) {
			puzzleSolved();
		}
	}
	
	public void setAnswer(String answer) {
		// Tells each selected SpaceBox to
		// set answer label to letter input
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : letterBoxes) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			if (letterSpace.getHilight()) {
				letterSpace.setCurrentChar(answer.charAt(0));
				spaceBox.setAnswerCharLabel(true);
			}
		}
		
		checkForSolved();
	}
	
	public void updateHilights() {
		// Tells each SpaceBox to hilight
		// if it's space is selected (set to hilight)
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : letterBoxes) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			spaceBox.setCSS(letterSpace.getHilight());
		}
	}
	
	public void clearPuzzle() {
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : letterBoxes) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			letterSpace.setCurrentChar(' ');
			spaceBox.setAnswerCharLabel(true);
		}
	}
	
	public void loadNewPuzzle() {
		// Clear game board
		flow.getChildren().clear();
		
		// Create new game board
		String puzzlePhrase = null;
		PuzzleLoader pLoader = new PuzzleLoader();
		pLoader.setTarget(Integer.toString(puzzleIndex));
		
		try {
			puzzlePhrase = pLoader.getPhrase();
			setupPuzzle(gameMan.getPuzzle(puzzlePhrase));
			
			// Used for updating only letter spaces
			setupLetterBoxArray();
			// Keep the "next puzzle" updated
			puzzleIndex++;
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
			spaceBox = new SpaceBox(space, this);
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
