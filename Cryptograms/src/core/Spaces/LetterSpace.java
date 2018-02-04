package core.Spaces;

public class LetterSpace extends Space {
	private char correctLetter;
	private char currentLetter;
	private boolean filled = false;

	public LetterSpace(char display, char correct) {
		super(display);
		this.correctLetter = correct;
	}

	public char getCorrectLetter() { return correctLetter; }
	public boolean isFilled() { return filled; }
	
	public char getCurrentLetter() { return currentLetter; }
	public void setCurrentLetter(char letter) { 
		currentLetter = letter; 
		filled = true;
	}
	
	public boolean isCorrect() { 
		boolean correct = false;
		if (filled) {
			if (currentLetter == correctLetter) {
				correct = true;
			}
		}
		return correct; 
	}
}
