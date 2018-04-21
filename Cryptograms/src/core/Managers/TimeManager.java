package core.Managers;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TimeManager {
	private HashMap<String, Integer> playerClocks = null;
	private String currentPlayerKey;
	long startingTime;
	
	public TimeManager() {
		playerClocks = new HashMap<String, Integer>();
	}
	
	public void addPlayer(String playerKey) {
		if (!playerClocks.containsKey(playerKey)) {
			playerClocks.put(playerKey, 0);
		}
	}
	
	public void startTimer(String playerKey) {
		currentPlayerKey = playerKey;
		startingTime = System.nanoTime();
	}
	
	private long calculateElapsedTime() {
		long elapsedTime;
		long currentTime = System.nanoTime();
		
		elapsedTime = startingTime - currentTime;
		
		return TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
	}
	
	public long getTimeElapsed() {
		long elapsedTime;
		
		elapsedTime = calculateElapsedTime();
		
		return elapsedTime;
	}
}
