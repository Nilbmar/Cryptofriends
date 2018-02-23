package core.Managers;

import core.Observers.Observer;
import core.Observers.SelectionObserver;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceList;

public class SelectionManager {
	private SelectionObserver selectObs;
	
	public SelectionManager() {
		selectObs = new SelectionObserver();
	}
	
	public void update() {
		
	}
	
	/* TODO:
	 * WORKING ON HOOKING UP SELECTION OBSERVER
	 * WILL NEED A WAY TO PASS SELECTION INFO
	 * BETWEEN FXML AND THIS
	 */
	public void setupSelectionObserver(SpaceList spaces) {
		// Add LetterSpaces to SelectionObserver's list
		// of Observers it reports to
		int listLen = spaces.getList().size() - 1;
		for (int x = 0; x < listLen; x++) {
			if (spaces.getList().get(x).isUnderlined()) {
				selectObs.addObserver((Observer) spaces.getList().get(x));
			}
		}
		
		for (Space space : spaces.getList()) {
			if (space.isUnderlined()) {
				((LetterSpace) space).addObserver(selectObs);
			}
		}
	}
	public void setHilight(Space spaceToHilight) {
		// If its a LetterSpace
		// Gives SelectionObserver it's display char
		// so SelectionObserver can also tell others that share char
		// they should be hilighted
		// Then notifies SelectionObserver to update
		if (spaceToHilight.isUnderlined()) {
			//((LetterSpace) spaceToHilight).addObserver(selectObs);
			((LetterSpace) spaceToHilight).notifyObserver();
		}
	}

}
