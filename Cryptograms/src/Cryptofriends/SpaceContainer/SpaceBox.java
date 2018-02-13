package Cryptofriends.SpaceContainer;

import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SpaceBox extends VBox {
	private Parent parent;
	private Scene scene;
	private VBox vbox;
	private Space space;
	
	private boolean selected = false;
	private boolean underlined = false;
	
	private Label answerChar = new Label();
	private Label displayChar = new Label();
	
	public SpaceBox(Space space) {
		this.space = space;
		//this.scene = scene;
		vbox = this; //new vBox(); // vbox might need to be inner vbox around answerLabel
		
		/* Selects a Space to change
		 * Should:
		 *     Hilight answerChar label
		 *     Set itself as the space to Observe for changes
		 */
		vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getButton() != null && event.getButton() == MouseButton.PRIMARY) {
					// Toggle between Hilighted when clicking on a space
					if (selected == false) {
						selected = true;
						setCSS(selected);
					} else {
						selected = false;
						setCSS(selected);
					}
				}
			}
		});
		answerChar.setFont(Font.font("Fira Mono", 20));
		answerChar.setPadding(new Insets(0, 5, 0, 5));
		
		displayChar.setFont(Font.font("Fira Mono", 20));
		displayChar.setPadding(new Insets(-5, 5, 0, 5));
		
		answerChar.setAlignment(Pos.BOTTOM_CENTER);
		displayChar.setAlignment(Pos.TOP_CENTER);
		
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().add(answerChar);
		vbox.getChildren().add(displayChar);
		vbox.setStyle("-fx-border-color: transparent");

		setDisplayCharLabel();
		setUnderlined();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public void setParent(FlowPane fp) {
		parent = fp;
	}
	
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
	
	public void setAnswerCharLabel(char c) {
		// Allow changing the LetterSpace's answer char
		// but don't allow changing the others
		switch (space.getSpaceType()) {
		case BLANK:
			answerChar.setText(" ");
			break;
		case PUNC:
			answerChar.setText(String.valueOf(space.getDisplayChar()));
			break;
		case LETTER:
			answerChar.setText(String.valueOf(c));
			break;
		default:
			break;
		}
		
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setSelected(boolean s) {
		selected = s;
		setCSS(selected);
	}
	
	/* Highlight Folder and Label when label is clicked on
	 * Or set it back to default style when unselected 
	 * TODO: MOVE THIS INTO A CSS FILE
	 */
	private void setCSS(boolean highlight) {
		//vbox.setStyle("-fx-border-color: black");
		if (highlight) {
			vbox.setStyle("-fx-border-color: black");
		} else {
			vbox.setStyle("-fx-border-color: transparent");
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
