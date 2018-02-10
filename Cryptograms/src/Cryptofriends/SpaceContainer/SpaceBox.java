package Cryptofriends.SpaceContainer;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class SpaceBox extends VBox {
	private Parent parent;
	private Scene scene;
	private VBox vbox;
	
	private boolean selected = false;
	
	private Label underlineLabel = new Label();
	private boolean underlined = false;
	
	private Label answerChar = new Label();
	private Label displayChar = new Label();
	
	public SpaceBox() {
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
		
		answerChar.setAlignment(Pos.BOTTOM_CENTER);
		displayChar.setAlignment(Pos.TOP_CENTER);
		
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().add(answerChar);
		vbox.getChildren().add(underlineLabel);
		vbox.getChildren().add(displayChar);
		
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public void setParent(FlowPane fp) {
		parent = fp;
	}
	
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
		if (underlined) {
			underlineLabel.setText(String.valueOf('_'));
		}
	}
	public void setDisplayCharLabel(char c) {
		displayChar.setText(String.valueOf(c));
	}
	
	public void setAnswerCharLabel(char c) {
		answerChar.setText(String.valueOf(c));
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
	}

}
