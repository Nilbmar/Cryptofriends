package core.Processes;

import core.Spaces.Space;

public class Selector {
	private Space selectedSpace;
	private boolean selectionMade;
	
	public Selector() {
		
	}
	
	public boolean isSelectionMade() { return selectionMade; }
	
	public Space getSelected() { return selectedSpace; }
	public void setSelected(Space space) {
		selectedSpace = space;
	}

}
