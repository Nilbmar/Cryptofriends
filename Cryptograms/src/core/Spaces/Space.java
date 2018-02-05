package core.Spaces;

public abstract class Space {
	private char displayChar;
	protected boolean underline;
	
	public Space(char display) {
		displayChar = display;
	}
	
	public char getDisplayChar() { return displayChar; }
}
