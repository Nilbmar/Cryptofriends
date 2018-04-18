package core.Data;

public class ScoreData {
	private String playerKey = null;
	private float score = 100;
	
	public ScoreData(String playerKey) {
		this.playerKey = playerKey;
	}
	
	public float getScore() { return score; }
	
	public void reduceScore(float percentToReduce) {
		score -= percentToReduce;
	}

}
