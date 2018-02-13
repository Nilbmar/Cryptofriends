package Cryptofriends;

import Cryptofriends.SpaceContainer.SpaceBox;
import Cryptofriends.SpaceContainer.WordBox;
import core.Spaces.LetterSpace;
import core.Spaces.Phrase;
import core.Spaces.PunctuationSpace;
import core.Spaces.Space;
import core.Spaces.Word;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

public class CgramController {
	@FXML
	private FlowPane flow;
	
	public SpaceBox addSpaceBox(char answer, char display, boolean underline) {
		SpaceBox spaceBox = null;
		if (flow != null) {
			spaceBox = new SpaceBox();
			spaceBox.setAnswerCharLabel(answer);
			spaceBox.setDisplayCharLabel(display);
			spaceBox.setUnderlined(underline);
		}
		return spaceBox;
	}
	
	private void addWord(Word word) {
		WordBox wordBox = new WordBox();
		for (Space space : word.getWord()) {
			switch (space.getSpaceType()) {
			case BLANK:
				wordBox.addSpaceBox(addSpaceBox(' ', ' ', false));
				break;
			case LETTER:
				LetterSpace letter = (LetterSpace) space;
				wordBox.addSpaceBox(addSpaceBox(
						letter.getCorrectChar(), letter.getDisplayChar(), true));
				break;
			case PUNC:
				PunctuationSpace punc = (PunctuationSpace) space;
				wordBox.addSpaceBox(addSpaceBox(punc.getDisplayChar(), ' ', false));
				break;
			default:
				break;
			}
		}
		
		flow.getChildren().add(wordBox.getHBox());
		System.out.println("flowpane children" + flow.getChildren().size());
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
