package core.Spaces;

public abstract class Space {
	protected char displayChar;
	private boolean underline;
	private int id;
	protected SpaceType spaceType;
	
	public Space(char display) {
		displayChar = display;
		setSpaceType();
	}
	
	protected abstract void setSpaceType();
	public SpaceType getSpaceType() { return spaceType; }

	public int getID() { return id; }			// zero-indexed
	public void setID(int id) { this.id = id; }
	
	public void setUnderlined(boolean u) { underline = u; }
	public boolean isUnderlined() { return underline; }
	
	public char getDisplayChar() { return displayChar; }
	// abstract to protect blank and punctuation from being changed
	public abstract void setDisplayChar(char c);
}
