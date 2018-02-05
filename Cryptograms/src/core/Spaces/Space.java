package core.Spaces;

public abstract class Space {
	private char displayChar;
	private boolean underline;
	
	public Space(char display) {
		displayChar = display;
	}
	
	public char getDisplayChar() { return displayChar; }
	public void setUnderlined(boolean u) { underline = u; }
	public boolean isUnderlined() { return underline; }
}
