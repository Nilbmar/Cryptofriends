package core.Spaces;

public class LetterSpace extends Space {
	private char correctChar;
	private char currentChar;
	private boolean filled = false;

	public LetterSpace(char display, char correct) {
		super(display);
		setUnderlined(true);
		this.correctChar = correct;
	}
	
	@Override
	public void setDisplayChar(char c) { displayChar = c; }

	public char getCorrectChar() { return correctChar; }
	public boolean isFilled() { return filled; }
	
	public char getCurrentChar() { return currentChar; }
	public void setCurrentChar(char letter) { 
		currentChar = letter; 
		filled = true;
	}
	
	public boolean isCorrect() { 
		boolean correct = false;
		if (filled) {
			if (currentChar == correctChar) {
				correct = true;
			}
		}
		return correct; 
	}
}
