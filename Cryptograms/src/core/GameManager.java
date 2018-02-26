package core;

import core.Managers.PlayerManager;
import core.Managers.SelectionManager;
import core.Spaces.Phrase;
import core.Spaces.SpaceList;

public class GameManager {
	private SelectionManager selectMan;
	private PlayerManager playerMan;
	
	public GameManager() {
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
	}
	
	public void update() {
		playerMan.update();
		selectMan.update();
	}
	
	public PlayerManager getPlayerManager() { return playerMan; }
	public SelectionManager getSelectionManager() { return selectMan; }
	
	// TODO: SEPERATE THIS TO A PUZZLEMANAGER
	public Phrase getPuzzle(String puzzlePhrase) {
		Phrase phrase = null;
		SpaceList spaces = new SpaceList();
		spaces.create(puzzlePhrase);
		phrase = spaces.getPhrase();
		
		return phrase;
	}
}
