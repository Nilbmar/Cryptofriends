package core.Managers;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.SpaceBox;
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
	
	public void displayLetter() {
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
		
		// TODO: GameManager needs to take care of this
		String playerKey = "Player " + gameMan.getPlayerManager().getCurrentPlayer().getPlayerNum();
		gameMan.getScoreManager().playerRevealedLetter(playerKey, letterOccurances, flow.getLetterBoxes().size());
		
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
		ArrayList<SpaceBox> incorrectSpaceBoxes = getIncorrectSpaceBoxes();
		LetterSpace letterSpace = null;
		for (int x = 0; x < incorrectSpaceBoxes.size(); x++) {
			letterSpace = (LetterSpace) incorrectSpaceBoxes.get(x).getSpace();
			letterSpace.setCurrentChar(' ');
			incorrectSpaceBoxes.get(x).setAnswerCharLabel(true);
		}
		
		clearHilights();
	}
	
	public void hilightIncorrect() {
		// Only needs to run if something has been selected on this puzzle
		// Otherwise, the last SpaceBox would be hilighted by clearHilights()
		SpaceBox selectedBox = gameMan.getBoardManager().getCurrentlySelected();
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
		}
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
