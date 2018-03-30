package core.Score;

public class ScoreData {
	private String playerName = null;
	private int score = 0;
	
	public ScoreData(String playerName) {
		this.playerName = playerName;
	}
	
	public int getScore() { return score; }
	public void addToScore(int amtToAdd) {
		score += amtToAdd;
	}
	
	public void reduceScore(int amtToReduce) {
		score -= amtToReduce;
	}

}
