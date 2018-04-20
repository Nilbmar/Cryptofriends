package core.Managers;

import Cryptofriends.CgramController;
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
	private CgramController controller;
	private GameManager gameMan;
	private FlowBox flow;
	
	public BoardManager(GameManager gameMan, CgramController controller) {
		this.controller = controller;
		this.gameMan = gameMan;
	}
	
	public FlowBox getFlowBox() { return flow; }
	public void setupFlowBox() {
		if (flow == null) {
			flow = new FlowBox();
			flow.setSpacesPerLine(15);
		}
	}
	
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
				PuncBox puncBox = new PuncBox(space, controller, flow);
				spaceBox = puncBox;
			} else {
				spaceBox = new SpaceBox(space, controller, flow);
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
}
