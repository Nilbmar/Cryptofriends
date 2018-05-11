package core.Data;

public class PlayerScore {
	private String playerKey = null;
	private float score = 100;
	
	public PlayerScore(String playerKey) {
		this.playerKey = playerKey;
	}
	
	public float getScore() { return score; }
	
	public void reduceScore(float percent) {
		score -= percent;
		
		if (score < 0) {
			score = 0;
		}
	}
	
	public void scoreZero() { score = 0; }

}
