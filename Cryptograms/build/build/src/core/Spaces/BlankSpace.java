package core.Spaces;

public class BlankSpace extends Space {

	public BlankSpace(char display) {
		super(display);
		setUnderlined(false);
	}
	
	@Override
	public void setDisplayChar(char c) { 
		// Abstract - but
		// Don't want to change this
	}
	
	@Override 
	protected void setSpaceType() {
		spaceType = SpaceType.BLANK;
	}

}
