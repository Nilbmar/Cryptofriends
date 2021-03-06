package Cryptofriends.SpaceContainer;

import core.Managers.BoardManager;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SpaceBox extends VBox {
	private Space space;
	private BoardManager boardMan;
	
	private boolean selected = false;
	
	protected Label answerChar = new Label();
	protected Label displayChar = new Label();
	
	public SpaceBox(Space space, BoardManager boardMan, FlowBox flow) {
		this.space = space;
		this.boardMan = boardMan;

		
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
						boardMan.clearSelection();
						toggleSelection();
					}
				}
			});

		}
		

		this.setAlignment(Pos.TOP_CENTER);
		answerChar.setAlignment(Pos.BOTTOM_CENTER);
		displayChar.setAlignment(Pos.TOP_CENTER);
	}
	
	public void setAlignment() {		
		answerChar.setFont(Font.font("Fira Mono", 20));
		answerChar.setPadding(new Insets(0, 5, 0, 5));
		
		displayChar.setFont(Font.font("Fira Mono", 20));
		displayChar.setPadding(new Insets(-5, 5, 0, 5));
		
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
		if (space.getSpaceType() == SpaceType.LETTER) {
			answerChar.setUnderline(true);
		} else {
			// Move punctuation closer to previous character
			// also moves blank spaces but shouldn't be able to tell
			//answerChar.setAlignment(Pos.BASELINE_LEFT);
			//answerChar.setPadding(new Insets(0, 5, 0, -10));
		}
	}
	public void setDisplayCharLabel() {
		switch (space.getSpaceType()) {
		case BLANK:
			displayChar.setText(" ");
			break;
		case PUNC:
			displayChar.setText("");
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
	
	public void clear() {
		if (space.getSpaceType() == SpaceType.LETTER) {
			((LetterSpace) space).setCurrentChar(' ');
			setAnswerCharLabel(true);
		}
	}
	
	public boolean getSelected() { return selected; }
	public void setSelected(boolean select) { selected = select; }
	
	public void toggleSelection() {
		selected = !selected;
		
		// Only wrap the single selected item in a border
		// Rest only have background colored
		if (selected) {
			this.setStyle("-fx-border-color: black");
		}
		
		// Notify observer to hilight all sharing this char
		if (space.getSpaceType() == SpaceType.LETTER) {
			((LetterSpace) space).notifyObserver();
		}
		
		boardMan.updateHilights(space.getID());
	}
	
	/* Highlight Folder and Label when label is clicked on
	 * Or set it back to default style when unselected 
	 * TODO: MOVE THIS INTO A CSS FILE
	 */
	public void setCSS(boolean hilight, boolean hilightIncorrect) {
		String transBack = "-fx-background-color: transparent";
		String transBorder = "-fx-border-color: transparent";
		String hilightColor = null;
		
		if (!hilightIncorrect) {
			hilightColor = "-fx-background-color: #4abdac";
		} else {
			hilightColor = "-fx-background-color: #fc4a1a";
		}
		
		if (hilight) {
			if (space.getSpaceType() == SpaceType.LETTER) {
				answerChar.setStyle(hilightColor);
			}
		} else {
			this.setStyle(transBorder);
			answerChar.setStyle(transBack);
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
