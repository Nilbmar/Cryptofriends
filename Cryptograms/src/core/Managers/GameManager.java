package core.Managers;

import Cryptofriends.CgramController;
import core.Data.Player;
import core.Data.PuzzleState;

public class GameManager {
	private CgramController controller;
	private SelectionManager selectMan;
	private PlayerManager playerMan;
	private ScoreManager scoreMan;
	private PuzzleState puzzleState = PuzzleState.PLAYING;
	
	public GameManager(CgramController controller) {
		this.controller = controller;
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
		scoreMan = new ScoreManager();
		
		// Create default player
		// and switch to them so player is set
		// for player info box
		Player firstPlayer = playerMan.addPlayer();
		playerMan.switchPlayer();
		controller.addPlayerMenuItem(firstPlayer);
	}
	
	public void update() {
		playerMan.update();
		selectMan.update();
	}
	
	public PuzzleState getPuzzleState() { return puzzleState; }
	public void setPuzzleState(PuzzleState puzzleState) { 
		this.puzzleState = puzzleState; 
	}
	
	public PlayerManager getPlayerManager() { return playerMan; }
	public SelectionManager getSelectionManager() { return selectMan; }
	public ScoreManager getScoreManager() { return scoreMan; }
}
