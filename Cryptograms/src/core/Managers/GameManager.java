package core.Managers;

import core.Data.PuzzleState;

public class GameManager {
	private SelectionManager selectMan;
	private PlayerManager playerMan;
	private ScoreManager scoreMan;
	private PuzzleState puzzleState = PuzzleState.PLAYING;
	
	public GameManager() {
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
		scoreMan = new ScoreManager();
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
