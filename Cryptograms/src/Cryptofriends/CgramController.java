package Cryptofriends;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.GameManager;
import core.Loaders.PuzzleLoader;
import core.Spaces.LetterSpace;
import core.Spaces.Phrase;
import core.Spaces.PunctuationSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import core.Spaces.Word;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class CgramController {
	private GameManager gameMan;
	private int puzzleIndex = 0;
	private ArrayList<Boolean> incorrectSpaces = new ArrayList<Boolean>();
	
	@FXML
	private FlowPane flow;
	
	@FXML
	private MenuItem newPuzzle;
	
	@FXML
	private MenuItem clearPuzzle;
	
	public void setGameManager(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	private void puzzleSolved() {
		System.out.println("Congratulations! You won the game!");
	}
	private void checkForSolved() {
		// Check to see if any space still contains a wrong answer
		// if no spaces contain a wrong answer
		// go to win condition
		
		// important to clear array list every check
		incorrectSpaces.clear();
		
		int wordCount = flow.getChildren().size();
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getChildren().get(x);
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
		int wordCount = flow.getChildren().size();
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getChildren().get(x);
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				Space space = spaceBox.getSpace();
				
				if (space.getSpaceType() == SpaceType.LETTER) {
					if (((LetterSpace) space).getHilight()) {
						((LetterSpace) space).setCurrentChar(answer.charAt(0));
						spaceBox.setAnswerCharLabel(true);
					}
				}
			}
		}
		
		checkForSolved();
	}
	
	public void updateHilights() {
		// Tells each SpaceBox to hilight
		// if it's space is selected (set to hilight)
		int wordCount = flow.getChildren().size();
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getChildren().get(x);
			for (SpaceBox spaceBox : wordBox.getAllSpaceBoxes()) {
				Space space = spaceBox.getSpace();
				
				if (space.getSpaceType() == SpaceType.LETTER) {
					spaceBox.setCSS(((LetterSpace) space).getHilight());
				}
			}
		}
	}
	
	public void clearPuzzle() {
		// TODO: THIS IS NOT WHAT I WANT IT TO DO
		// THIS SHOULD KEEP THE PUZZLE, JUST CLEAR ALL ANSWER LABELS
		//flow.getChildren().clear();
		int wordCount = flow.getChildren().size();
		System.out.println("word count" + wordCount);
		for (int x = 0; x < wordCount; x++) {
			WordBox wordBox = (WordBox) flow.getChildren().get(x);
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
				PunctuationSpace punc = (PunctuationSpace) space;
				wordBox.addSpaceBox(addSpaceBox(space));
				break;
			default:
				break;
			}
		}
		
		flow.getChildren().add(wordBox);
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
