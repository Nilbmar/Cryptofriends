package core.Managers;

import core.Score.ScoreManager;

public class GameManager {
	private SelectionManager selectMan;
	private PlayerManager playerMan;
	private ScoreManager scoreMan;
	
	public GameManager() {
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
		scoreMan = new ScoreManager();
	}
	
	public void update() {
		playerMan.update();
		selectMan.update();
	}
	
	public PlayerManager getPlayerManager() { return playerMan; }
	public SelectionManager getSelectionManager() { return selectMan; }
	public ScoreManager getScoreManager() { return scoreMan; }
}
