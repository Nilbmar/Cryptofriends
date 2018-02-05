package core.Spaces;

public abstract class Space {
	protected char displayChar;
	private boolean underline;
	
	public Space(char display) {
		displayChar = display;
	}

	public void setUnderlined(boolean u) { underline = u; }
	public boolean isUnderlined() { return underline; }
	
	public char getDisplayChar() { return displayChar; }
	// abstract to protect blank and punctuation from being changed
	public abstract void setDisplayChar(char c);
}
