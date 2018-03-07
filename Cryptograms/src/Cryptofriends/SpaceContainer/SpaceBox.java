package Cryptofriends.SpaceContainer;

import Cryptofriends.CgramController;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SpaceBox extends VBox {
	private Space space;
	private CgramController controller;
	
	private boolean selected = false;
	private boolean underlined = false;
	
	private Label answerChar = new Label();
	private Label displayChar = new Label();
	
	public SpaceBox(Space space, CgramController controller) {
		this.space = space;
		this.controller = controller;

		
		/* Selects a Space to change
		 * Should:
		 *     Hilight answerChar label
		 *     Set itself as the space to Observe for changes
		 */
		// Only allow selection of LetterSpaces
		if (space.getSpaceType() == SpaceType.LETTER) {
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// Select a LetterSpace
					// and hilight all LetterSpaces
					// with same character
					if (event.getButton() != null && event.getButton() == MouseButton.PRIMARY) {
						setSelected();
					}
				}
			});

		}
		answerChar.setFont(Font.font("Fira Mono", 20));
		answerChar.setPadding(new Insets(0, 5, 0, 5));
		
		displayChar.setFont(Font.font("Fira Mono", 20));
		displayChar.setPadding(new Insets(-5, 5, 0, 5));
		
		answerChar.setAlignment(Pos.BOTTOM_CENTER);
		displayChar.setAlignment(Pos.TOP_CENTER);
		
		this.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(answerChar);
		this.getChildren().add(displayChar);
		this.setStyle("-fx-border-color: transparent");

		setDisplayCharLabel();
		setUnderlined();

		// false sets the letter as " " so only displays an underline
		setAnswerCharLabel(false);
	}
	
	public Space getSpace() { return space; }
	
	public void setUnderlined() {
		underlined = space.isUnderlined();
		if (underlined) {
			answerChar.setUnderline(true);
		} else {
			// Move punctuation closer to previous character
			// also moves blank spaces but shouldn't be able to tell
			answerChar.setAlignment(Pos.BASELINE_LEFT);
			answerChar.setPadding(new Insets(0, 5, 0, -10));
		}
	}
	public void setDisplayCharLabel() {
		switch (space.getSpaceType()) {
		case BLANK:
			displayChar.setText(" ");
			break;
		case PUNC:
			displayChar.setText(" ");
			break;
		case LETTER:
			displayChar.setText(String.valueOf(space.getDisplayChar()));
			break;
		default:
			break;
		}
		
	}
	
	public void setAnswerCharLabel(boolean showAnswer) {
		// Allow changing the LetterSpace's answer char
		// but don't allow changing the others
		switch (space.getSpaceType()) {
		case BLANK:
			answerChar.setText(" ");
			break;
		case PUNC:
			// Needs to show its display char on same line as Letter's answer
			answerChar.setText(String.valueOf(space.getDisplayChar()));
			break;
		case LETTER:
			String answer = null;
			if (showAnswer) {
				// Display last character inserted into a space
				answer = String.valueOf(((LetterSpace) space).getCurrentChar());
			} else {
				// Don't show answer label
				// Used at the start so they're blank
				answer = " ";
			}
			answerChar.setText(String.valueOf(answer));
			break;
		default:
			break;
		}
		
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setSelected() {
		selected = !selected;
		
		// Notify observer to hilight all sharing this char
		if (space.getSpaceType() == SpaceType.LETTER) {
			//((LetterSpace) space).setHilight(selected);
			((LetterSpace) space).notifyObserver();
		}
		
		controller.updateHilights(space.getID());
	}
	
	/* Highlight Folder and Label when label is clicked on
	 * Or set it back to default style when unselected 
	 * TODO: MOVE THIS INTO A CSS FILE
	 */
	public void setCSS(boolean highlight) {
		//vbox.setStyle("-fx-border-color: black");
		if (highlight) {
			this.setStyle("-fx-border-color: black");
		} else {
			this.setStyle("-fx-border-color: transparent");
		}
		// Pale Red Color - CC6666
		// TODO: THIS IS CAUSING VBOX TO JERK TO ONE SIDE
		/*
		if (highlight) {
			vbox.setStyle("-fx-effect: innershadow(gaussian, #039ed3, 10, 1.0, 0, 0);" +
					"-fx-padding: 2, 0, 2, 0;" + 
	                "-fx-border-style: dotted inside;" + 
	                "-fx-border-width: 0;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;"
					);
		} else {
			vbox.setStyle("-fx-effect: none;" +
					"-fx-padding: 2, 0, 2, 0;" + 
	                "-fx-border-style: dotted inside;" + 
	                "-fx-border-width: 0;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;"
	                );
		}
		//*/
	}
}
