package core.Managers;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import core.Data.AnswerData;
import core.Data.PuzzleState;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;

public class AnswerManager {
	private GameManager gameMan;
	private FlowBox flow;

	public AnswerManager(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	public void setFlowBox(FlowBox flow) {
		this.flow = flow;
	}
	
	public void clearAnswers() {
		int currentSpaceBoxNum = -1;
		PuzzleState puzzleState = gameMan.getPuzzleState();
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			currentSpaceBoxNum++;
			
			if (!spaceBox.isDisabled()) {
				spaceBox.clear();
				spaceBox.setAnswerCharLabel(true);
				puzzleState.answered(currentSpaceBoxNum, "CLEAR", "CLEAR");
			}
		}
	}
	
	public void setAnswer(String answer, String currentPlayerKey) {
		int currentSpaceBoxNum = -1;
		PuzzleState puzzleState = gameMan.getPuzzleState();
		// Tells each selected SpaceBox to
		// set answer label to letter input
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			currentSpaceBoxNum++;
			
			if (!spaceBox.isDisabled()) {
				letterSpace = (LetterSpace) spaceBox.getSpace();
				if (letterSpace.getHilight()) {
					letterSpace.setCurrentChar(answer.charAt(0));
					spaceBox.setAnswerCharLabel(true);
					
					// Update PuzzleState for keeping score
					puzzleState.answered(currentSpaceBoxNum, answer, currentPlayerKey);
				}
			}
		}
		
		checkForSolved();
	}
	
	// Tells each selected SpaceBox to
	// set answer label to input saved to a PuzzleState JSON
	public void loadAnswers() {
		int currentSpaceBoxNum = -1;
		PuzzleState puzzleState = gameMan.getPuzzleState();
		AnswerData answerData = null;
		
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			currentSpaceBoxNum++;
			
			if (!spaceBox.isDisabled()) {
				letterSpace = (LetterSpace) spaceBox.getSpace();
				answerData = puzzleState.getAnswerData(currentSpaceBoxNum);
				
				// Is it answered/blank, or a hint?
				// Set display character appropriately
				if (answerData.getAnsweredChar().contentEquals("HINT")) {
					letterSpace.setCurrentChar(letterSpace.getCorrectChar());
				} else if (answerData.getAnsweredChar().contentEquals("CLEAR")) { 
					letterSpace.setCurrentChar(' ');
				} else {
					letterSpace.setCurrentChar(answerData.getAnsweredChar().charAt(0));
				}
				spaceBox.setAnswerCharLabel(true);
			}
		}

		checkForSolved();
	}
	
	public void setHints(String hints) {
		int currentSpaceBoxNum = -1;
		PuzzleState puzzleState = gameMan.getPuzzleState();
		LetterSpace letterSpace = null;
		int numOfHints = hints.length();
		for (int x = 0; x < numOfHints; x++) {
			for (SpaceBox spaceBox : flow.getLetterBoxes()) {
				currentSpaceBoxNum++;
				
				if (!spaceBox.isDisable()) {
					letterSpace = (LetterSpace) spaceBox.getSpace();
					if (hints.indexOf(letterSpace.getCorrectChar()) >= 0) {
						letterSpace.setCurrentChar(letterSpace.getCorrectChar());
						spaceBox.setAnswerCharLabel(true);
						spaceBox.setDisable(true);
						
						// Update PuzzleState for keeping score
						puzzleState.answered(currentSpaceBoxNum, "HINT", "HINT");
					}
				}
			}
		}
	}
	
	public int displayLetter() {
		int letterOccurances = 0;
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			if (!spaceBox.isDisabled()) {
				letterSpace = (LetterSpace) spaceBox.getSpace();
				if (letterSpace.getHilight()) {
					letterOccurances++;
					letterSpace.setCurrentChar(letterSpace.getCorrectChar());
					spaceBox.setAnswerCharLabel(true);
					spaceBox.setCSS(false,  false);
					spaceBox.setDisable(true);
				}
			}
		}
		
		checkForSolved();
		return letterOccurances;
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
	}
	
	private void puzzleSolved() {
		// Clears hilights and selection box
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			spaceBox.setCSS(false, false);
		}

		// Disable puzzle so it can't be edited
		flow.setDisable(true);
		
		if (gameMan.getPuzzleState().getState() != PuzzleState.State.WON) {
			// Only run gameWon if its the first time
			gameMan.gameWon();
		} else {
			// When returning to a solved puzzle, only alert winner
			// Don't set to win again
			gameMan.alertWinner();
		}
	}
	
	public void checkForSolved() {
		// Check to see if any space still contains a wrong answer
		// if no spaces contain a wrong answer
		// go to win condition
		// Only WIN if does not contain a spacebox
		if (gameMan.getPuzzleState().getState() != PuzzleState.State.FAILED) {
			ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
			if (incorrectSpaceBoxes.size() == 0) {
				puzzleSolved();
			}
		}
	}
	
	public ArrayList<SpaceBox> getFilledSpaceBoxes() {
		ArrayList<SpaceBox> filledSpaceBoxs = new ArrayList<SpaceBox>();
		
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			Space space = spaceBox.getSpace();
			if (space.getSpaceType() == SpaceType.LETTER) {
				LetterSpace letterSpace = (LetterSpace) space;
				if (!letterSpace.isBlank()) {
					filledSpaceBoxs.add(spaceBox);
				}
			}
		}
		
		return filledSpaceBoxs;
	}
	
	/* Returns ArrayList of SpaceBoxes that are:
	 * Answered wrong OR Left blank
	 * because blank spaces are still "incorrect"
	 */
	public ArrayList<SpaceBox> getIncorrectSpaceBoxes() {
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
	
	public void clearIncorrect() {
		int letterNum = -1;
		PuzzleState puzzleState = gameMan.getPuzzleState();
		
		ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
		LetterSpace letterSpace = null;
		for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
			letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
			
			// Don't do anything if it's a blank space
			if (letterSpace.getCurrentChar() != ' ') {
				letterSpace.setCurrentChar(' ');
				incorrectSpaceBoxes.get(x).setAnswerCharLabel(true);
				
				
				// Update PuzzleState for keeping score
				letterNum = flow.getLetterBoxes().indexOf(incorrectSpaceBoxes.get(x));
				puzzleState.answered(letterNum, "", "");
			}
		}
		
		clearHilights();
	}
	
	public int hilightIncorrect(SpaceBox selectedBox) {
		int numOfWrongAnswers = 0;
		// Only needs to run if something has been selected on this puzzle
		// Otherwise, the last SpaceBox would be hilighted by clearHilights()
		if (selectedBox != null) {
			clearHilights();
			
			// Incorrect are Wrong and Unanswered
			// increments local variable to keep track of Wrong answers
			ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
			LetterSpace letterSpace = null;
			for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
				letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
				if (!letterSpace.isBlank()) {
					incorrectSpaceBoxes.get(x).setCSS(true, true);	
					numOfWrongAnswers++;
				}	
			}
		}
		
		
		return numOfWrongAnswers;
	}
	
	public void clearHilights() {
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		SpaceBox selectedBox = gameMan.getBoardManager().getCurrentlySelected();
		for (int x = 0; x < letterBoxes.size(); x++) {
			letterBoxes.get(x).setCSS(false,  false);
		}
		
		// Make sure the original selected box remains selected
		gameMan.getBoardManager().clearSelection();
		if (selectedBox != null) {
			selectedBox.toggleSelection();
		}
	}
	

	
	
}
