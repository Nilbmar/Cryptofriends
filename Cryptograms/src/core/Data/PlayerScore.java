package core.Data;

public class PlayerScore {
	private String playerKey = null;
	private float puzzleScore = 100;
	private float totalScore = 0;
	
	public PlayerScore(String playerKey) {
		this.playerKey = playerKey;
	}
	
	public String getPlayerKey() { return playerKey; }
	
	public float getScore() { return puzzleScore; }
	public float getTotalScore() { return totalScore; }
	
	public void reduceScore(float percent) {
		puzzleScore -= percent;
		
		if (puzzleScore < 0) {
			puzzleScore = 0;
		}
	}
	
	// Player has used "Answer Puzzle" Reveal
	public void scoreZero() { puzzleScore = 0; }
	
	// Update and set values for next puzzle
	public void changedPuzzle() { 
		totalScore += puzzleScore;
		puzzleScore = 100;
	}

}
