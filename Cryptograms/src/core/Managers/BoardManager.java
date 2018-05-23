package core.Managers;

import java.util.ArrayList;

import Cryptofriends.SpaceContainer.FlowBox;
import Cryptofriends.SpaceContainer.PuncBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.Data.Puzzle;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import core.Spaces.Word;

public class BoardManager {
	private GameManager gameMan;
	private FlowBox flow;
	
	public BoardManager(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	public FlowBox getFlowBox() { return flow; }
	public void setupFlowBox() {
		if (flow == null) {
			flow = new FlowBox();
			flow.setSpacesPerLine(15);
		}
	}
	
	public int getTotalLetters() { return flow.getLetterBoxes().size(); }
	
	public void setupPuzzle(Puzzle puzzle) {
		for (Word word : puzzle.getPhrase()) {
			addWord(word);
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
	
	public void setupPuncAlignment() {
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
	
	public SpaceBox getCurrentlySelected() {
		SpaceBox selected = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			if (spaceBox.getSelected()) {
				selected = spaceBox;
			}
		}
		
		return selected;
	}
	
	public void clearSelection() {
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			// Set to true first so it's toggled to false
			// then deselected
			spaceBox.setSelected(true);
			spaceBox.toggleSelection();
		}
	}
	
	public void updateHilights(int id) {
		// Tells each SpaceBox to hilight
		// if it's space is selected (set to hilight)
		LetterSpace letterSpace = null;
		for (SpaceBox spaceBox : flow.getLetterBoxes()) {
			letterSpace = (LetterSpace) spaceBox.getSpace();
			spaceBox.setCSS(letterSpace.getHilight(), false);
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
	
	public void moveHilightVertically(int directionToMove) {
		int selectedID = getCurrentlySelected().getSpace().getID();
		int origLineNum = flow.lineOfSpaceBox(selectedID);
		int newLineNum = origLineNum + directionToMove;
		ArrayList<SpaceBox> newLine = null;
		
		if (newLineNum >= 0 && newLineNum < flow.lines()) {
			newLine = flow.spaceBoxesOnLine(newLineNum);
			int origPos = flow.positionOnLine(origLineNum, selectedID);

			// If origPos exists on the newLine, and is a LetterSpace, select it
			// Else get closest letter to that position on the newLine
			if (origPos < newLine.size()) {
				SpaceBox spaceBox = newLine.get(origPos);
				if (spaceBox.getSpace().getSpaceType() == SpaceType.LETTER) {
					clearSelection();
					newLine.get(origPos).toggleSelection();
				} else {
					spaceBox = getClosestLetter(newLine, spaceBox, origPos);
					clearSelection();
					spaceBox.toggleSelection();
				}
			} else {
				// Can't get a SpaceBox on the new line at origPos
				// so it gets the last SpaceBox on the newLine
				SpaceBox spaceBox = newLine.get(newLine.size() - 1);
				spaceBox = getClosestLetter(newLine, spaceBox, origPos);
				clearSelection();
				spaceBox.toggleSelection();
			}
		}
	}
	
	/* Move between LetterSpaces with LEFT and RIGHT arrow keys
	 * If at the beginning of a line, and used LEFT, will go up
	 * to previous line and select the last LetterSpace on it
	 * If at the end of a line, and used RIGHT, will go down
	 * to next line and select the first LetterSpace on it
	 */
	public void moveHilightHorizontally(int directionToMove) {
		int selectedID = getCurrentlySelected().getSpace().getID();
		int nextIndex = -1;
		ArrayList<SpaceBox> letterBoxes = flow.getLetterBoxes();
		
		// Finds the index of the original space in the LetterBox array
		// and moves to previous or next index to select the new LetterBox
		// spacesToAdjust is positive or negative based on if you need
		// to move forward or backward
		for (SpaceBox spaceBox : letterBoxes) {
			if (selectedID == spaceBox.getSpace().getID()) {
				nextIndex = letterBoxes.indexOf(spaceBox) + directionToMove;
			}
		}
		
		// Make sure index does exist and toggle the selection on for that SpaceBox
		if (nextIndex >= 0) {
			if (nextIndex < letterBoxes.size()) {
				// Within range handle normally
				clearSelection();
				letterBoxes.get(nextIndex).toggleSelection();
			} else if (nextIndex >= letterBoxes.size()) {
				// If moving right from the last LetterSpace
				// go to the first LetterSpace on the first line
				clearSelection();
				letterBoxes.get(0).toggleSelection();
			}
		} else if (nextIndex < 0) {
			// Allow cycling backward from first LetterSpace on first line
			// to last LetterSpace on last line
			clearSelection();
			letterBoxes.get(letterBoxes.size() - 1).toggleSelection();
		}
	}
}
