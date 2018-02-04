package core.Spaces;

public abstract class Space {
	private char displayChar;
	
	public Space(char display) {
		displayChar = display;
	}
	
	public char getDisplayChar() { return displayChar; }
}
