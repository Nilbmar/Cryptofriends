package core.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import core.Data.Player;
import core.Processes.CgramTimeTask;

public class TimeManager {
	private HashMap<String, Player> players = null;
	private String currentPlayerKey;
	private long startingTime;
	private CgramTimeTask timeTask;
	private Timer timer;
	
	public TimeManager(CgramTimeTask timeTask) {
		players = new HashMap<String, Player>();
		
		this.timeTask = timeTask;
		this.timeTask.setTimeManager(this);
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this.timeTask, 0, 10*100);
	}
	
	public void addPlayer(Player player) {
		String playerKey = "Player " + player.getPlayerNum();
		if (!players.containsKey(playerKey)) {
			players.put(playerKey, player);
		}
		
		if (playerKey.contentEquals("Player 1")) {
			timeTask.setPlayer(player);
		}
	}
	
	public void startTimer(String playerKey) {
		currentPlayerKey = playerKey;
		startingTime = System.nanoTime();
	}
	
	public void updateTimer() {
		long elapsedTime = getTimeElapsed();
		players.get(currentPlayerKey).getPlayerTime().updatePuzzleTime(elapsedTime);
	}
	
	private void stopTimer() {
		updateTimer();
		currentPlayerKey = null;
	}
	
	private long calculateElapsedTime() {
		long elapsedTime = 0;
		if (startingTime > 0) {
			elapsedTime = System.nanoTime() - startingTime;
			elapsedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		}
		
		return elapsedTime;
	}
	
	public long getTimeElapsed() {
		long elapsedTime;
		
		elapsedTime = calculateElapsedTime();
		System.out.println("elapsedTime in seconds: " + elapsedTime);
		return elapsedTime;
	}
	
	public void switchedPlayer(String newPlayerKey) {
		stopTimer();
		
		currentPlayerKey = newPlayerKey;
		timeTask.setPlayer(players.get(currentPlayerKey));
	}
	
	/* Sets the last elapsed time for the current player
	 * and then tells all players to add to their total
	 */
	public void finishedPuzzle() {
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		
		Player player = null;
		String playerKey = null;
		while (it.hasNext()) {
			player = it.next().getValue();
			playerKey = "Player " + player.getPlayerNum();
			if (currentPlayerKey == playerKey) {
				player.getPlayerTime().updatePuzzleTime(getTimeElapsed());
			}
			
			player.getPlayerTime().finishedPuzzle();
		}
	}
}
