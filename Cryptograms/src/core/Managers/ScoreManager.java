package core.Managers;

import java.util.HashMap;

import core.Data.ScoreData;

public class ScoreManager {
	private HashMap<String, ScoreData> playerScores = null;
	
	public ScoreManager() {
		playerScores = new HashMap<String, ScoreData>();
	}
	
	public void addPlayer(String playerKey) {
		if (!playerScores.containsKey(playerKey)) {
			ScoreData scoreData = new ScoreData(playerKey);
			playerScores.put(playerKey, scoreData);
		}
	}
	
	public ScoreData getPlayerScoreData(String playerKey) {
		ScoreData scoreData = null;
		if (playerScores.containsKey(playerKey)) {
			scoreData = playerScores.get(playerKey);
		}
		
		return scoreData;
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
}
