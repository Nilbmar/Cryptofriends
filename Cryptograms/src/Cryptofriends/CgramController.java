package Cryptofriends;

import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.GameManager;
import core.Loaders.PuzzleLoader;
import core.Spaces.LetterSpace;
import core.Spaces.Phrase;
import core.Spaces.PunctuationSpace;
import core.Spaces.Space;
import core.Spaces.Word;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class CgramController {
	private GameManager gameMan;
	private int puzzleIndex = 0;
	
	@FXML
	private FlowPane flow;
	
	@FXML
	private MenuItem newPuzzle;
	
	@FXML
	private MenuItem clearPuzzle;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	public void clearPuzzle() {
		// TODO: THIS IS NOT WHAT I WANT IT TO DO
		// THIS SHOULD KEEP THE PUZZLE, JUST CLEAR ALL ANSWER LABELS
		//flow.getChildren().clear();
		int wordCount = flow.getChildren().size();
		System.out.println("word count" + wordCount);
		for (int x = 0; x < wordCount; x++) {
			// Stupid hack because adding WordBox to FlowPane doesn't work
			// only adding HBox works, even though WordBox is a subclass
			WordBox wordBox = new WordBox((HBox) flow.getChildren().get(x));
			for (SpaceBox space : wordBox.getAllSpaceBoxes()) {
				space.setAnswerCharLabel(false);
			}
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
			spaceBox = new SpaceBox(space);
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
				wordBox.addSpaceBox(addSpaceBox(space));
				break;
			case PUNC:
				PunctuationSpace punc = (PunctuationSpace) space;
				wordBox.addSpaceBox(addSpaceBox(space));
				break;
			default:
				break;
			}
		}
		
		flow.getChildren().add(wordBox.getHBox());
	}
	
	public void setupPuzzle(Phrase phrase) {
		// Controls Word Wrap
		int spacesPerLine = 14;
		int currentSpaces = 0;
		for (Word word : phrase.getPhrase()) {
			currentSpaces += word.getWord().size();
			// If under designated width add the word
			if (currentSpaces <= spacesPerLine) {
				addWord(word);
			} else {
				// Don't reset space count if dropping to new line
				currentSpaces = 0;
				if (!word.isBlankSpace()) {
					currentSpaces = word.size();
					addWord(word);
				}
			}
		}
	}
}
