package core.Score;

import java.util.HashMap;

public class ScoreManager {
	private HashMap<String, ScoreData> playerScores = null;
	
	public ScoreManager() {
		playerScores = new HashMap<String, ScoreData>();
	}
	
	public void addPlayer(String playerName) {
		if (!playerScores.containsKey(playerName)) {
			ScoreData scoreData = new ScoreData(playerName);
			playerScores.put(playerName, scoreData);
		}
	}
	
	public ScoreData getPlayerScoreData(String playerName) {
		ScoreData scoreData = null;
		if (playerScores.containsKey(playerName)) {
			scoreData = playerScores.get(playerName);
		}
		
		return scoreData;
	}

}
