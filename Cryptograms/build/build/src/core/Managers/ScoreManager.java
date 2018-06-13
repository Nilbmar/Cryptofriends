package core.Managers;

import java.util.HashMap;

import core.Data.PlayerScore;

public class ScoreManager {
	private HashMap<String, PlayerScore> playerScores = null;
	
	public ScoreManager() {
		playerScores = new HashMap<String, PlayerScore>();
	}
	
	public void addPlayer(String playerKey) {
		if (!playerScores.containsKey(playerKey)) {
			PlayerScore playerScore = new PlayerScore(playerKey);
			playerScores.put(playerKey, playerScore);
		}
	}
	
	public PlayerScore getPlayerScoreData(String playerKey) {
		PlayerScore playerScore = null;
		if (playerScores.containsKey(playerKey)) {
			playerScore = playerScores.get(playerKey);
		}
		
		return playerScore;
	}

	public void playerRevealedLetter(String playerKey, int letterOccurances, int totalLetters) {
		if (playerScores.containsKey(playerKey)) {
			float occurances = letterOccurances;
			float letters = totalLetters;
			//double rightPercent = occurances * 100 / letters;
			// The 10 and 10d is to set it to one decimal place
			float percent = (float) (Math.ceil(((occurances * 100) / letters) * 10) / 10d);
			
			getPlayerScoreData(playerKey).reduceScore(percent);
		}
	}
	
	public void playerRevealedPuzzle(String playerKey) {
		if (playerScores.containsKey(playerKey)) {
			getPlayerScoreData(playerKey).scoreZero();
		}
	}
	
	public void playerHilightedIncorrect(String playerKey, int numWrongAnswers) {
		float percent = 10;
		if (playerScores.containsKey(playerKey)) {
			getPlayerScoreData(playerKey).reduceScore(percent);
		}
	}
	
	public void updateForNewPuzzle() {
		// Lamba expression - v (value) are PlayerScore's
		// For each playeScore in HashMap, tell it to update for changed puzzle
		playerScores.forEach((k, v) -> v.changedPuzzle());
	}
}
