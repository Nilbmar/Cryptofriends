package Cryptofriends.SpaceContainer;

import Cryptofriends.CgramController;
import core.Spaces.Space;
import core.Spaces.SpaceType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PuncBox extends SpaceBox {
	private SpaceType prevSpaceType;
	private SpaceType nextSpaceType;

	public PuncBox(Space space, CgramController controller) {
		super(space, controller);
	}

	public void setPrevType(SpaceType prevSpaceType) {
		this.prevSpaceType = prevSpaceType;
	}
	
	public void setNextType(SpaceType nextSpaceType) {
		this.nextSpaceType = nextSpaceType;
	}
	
	public String getSurroundingTypes() {
		String types = null;
		if (prevSpaceType != null) {
			types = prevSpaceType.name() + ":";
		} else {
			types = "NULL:";
		}
		if (nextSpaceType != null){
			types = types + nextSpaceType.name();
		} else {
			types = types + "NULL";
		}
		return types;
	}
	
	@Override
	public void setAlignment() {
		double offset = -2;
		Insets answerInsets = new Insets(0, offset, 0, offset);
		Insets displayInsets = new Insets(-5, offset, 0, offset);
		

		// Alignment is set based on the SpaceTypes
		// of the spaces surrounding the PuncBox
		String comboTypes = getSurroundingTypes();
		switch (comboTypes.toUpperCase()) {
		case "NULL:LETTER":
			this.setAlignment(Pos.TOP_RIGHT);
			answerChar.setAlignment(Pos.BOTTOM_RIGHT);
			displayChar.setAlignment(Pos.TOP_RIGHT);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "LETTER:NULL":
			this.setAlignment(Pos.TOP_LEFT);
			answerChar.setAlignment(Pos.BOTTOM_LEFT);
			displayChar.setAlignment(Pos.TOP_LEFT);
			answerInsets = new Insets(0, offset, 0, 0);
			displayInsets = new Insets(-5, offset, 0, 0);
			break;
		case "PUNC:NULL":
			this.setAlignment(Pos.TOP_LEFT);
			answerChar.setAlignment(Pos.BOTTOM_LEFT);
			displayChar.setAlignment(Pos.TOP_LEFT);
			answerInsets = new Insets(0, offset, 0, 0);
			displayInsets = new Insets(-5, offset, 0, 0);
			break;
		case "BLANK:LETTER":
			this.setAlignment(Pos.TOP_RIGHT);
			answerChar.setAlignment(Pos.BOTTOM_RIGHT);
			displayChar.setAlignment(Pos.TOP_RIGHT);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "LETTER:BLANK":
			this.setAlignment(Pos.TOP_LEFT);
			answerChar.setAlignment(Pos.BOTTOM_LEFT);
			displayChar.setAlignment(Pos.TOP_LEFT);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "LETTER:LETTER":
			this.setAlignment(Pos.TOP_CENTER);
			answerChar.setAlignment(Pos.BOTTOM_CENTER);
			displayChar.setAlignment(Pos.TOP_CENTER);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "PUNC:PUNC":
			this.setAlignment(Pos.TOP_CENTER);
			answerChar.setAlignment(Pos.BOTTOM_CENTER);
			displayChar.setAlignment(Pos.TOP_CENTER);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "LETTER:PUNC":
			this.setAlignment(Pos.TOP_CENTER);
			answerChar.setAlignment(Pos.BOTTOM_CENTER);
			displayChar.setAlignment(Pos.TOP_CENTER);
			answerInsets = new Insets(0, offset, 0, offset);
			displayInsets = new Insets(-5, offset, 0, offset);
			break;
		case "PUNC:BLANK":
			this.setAlignment(Pos.TOP_LEFT);
			answerChar.setAlignment(Pos.BOTTOM_LEFT);
			displayChar.setAlignment(Pos.TOP_LEFT);
			answerInsets = new Insets(0, 5, 0, offset);
			displayInsets = new Insets(-5, 5, 0, offset);
			break;
		}
		
		
		// Finish adding the children
		answerChar.setFont(Font.font("Fira Mono", 20));
		answerChar.setPadding(answerInsets);
		
		displayChar.setFont(Font.font("Fira Mono", 20));
		displayChar.setPadding(displayInsets);
		
		this.getChildren().add(answerChar);
		this.getChildren().add(displayChar);
		this.setStyle("-fx-border-color: transparent");

		setDisplayCharLabel();
		setUnderlined();

		// false sets the letter as " " so only displays an underline
		setAnswerCharLabel(false);
		//answerChar.setAlignment(Pos.BOTTOM_CENTER);
	}
}
