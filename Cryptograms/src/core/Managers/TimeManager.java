package core.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import Cryptofriends.GUI.CgramTimeTask;
import Cryptofriends.GUI.CgramTimer;
import core.Data.Player;
import javafx.scene.control.Label;

public class TimeManager {
	private HashMap<String, Player> players = null;
	private HashMap<String, CgramTimer> timers = null;
	private String currentPlayerKey;
	private Label lblTime;
	private long startingTime;
	
	public TimeManager(Label lblTime) {
		this.lblTime = lblTime;
		players = new HashMap<String, Player>();
		timers = new HashMap<String, CgramTimer>();
	}
	
	public void addPlayer(Player player) {
		String playerKey = "Player " + player.getPlayerNum();
		if (!players.containsKey(playerKey)) {
			players.put(playerKey, player);
			
			// Create timer for the new player
			CgramTimer cTimer = new CgramTimer(this, player, lblTime);
			timers.put(playerKey, cTimer);
			
			// When first player added
			// Set it as the current player and start its timer
			if (playerKey.contentEquals("Player 1")) {
				currentPlayerKey = playerKey;
				timers.get(currentPlayerKey).schedule();
			}
		}
	}
	
	public void startTimer(String playerKey) {
		currentPlayerKey = playerKey;
		startingTime = System.nanoTime();
	}
	
	public void updateTimer() {
		long elapsedTime = getElapsedTime();
		players.get(currentPlayerKey).getPlayerTime().updateRoundTime(elapsedTime);
	}
	
	private void stopTimer() {
		updateTimer();
		
		// Updates player's time taken
		players.get(currentPlayerKey).getPlayerTime().updatePuzzleTime(getElapsedTime());
		startingTime = 0;
		
		// Remove old timer for player being switched from
		timers.get(currentPlayerKey).cancel();
		timers.remove(currentPlayerKey);
		
		// Create new timer for player being switched from
		// so it's ready to be used on their next turn
		CgramTimer cTimer = new CgramTimer(this, players.get(currentPlayerKey), lblTime);
		timers.put(currentPlayerKey, cTimer);
	}
	
	public long getElapsedTime() {
		long elapsedTime = 0;
		
		if (startingTime > 0) {
			elapsedTime = System.nanoTime() - startingTime;
			elapsedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		}
		
		return elapsedTime;
	}
	
	public void switchedPuzzle() {
		stopTimer();
		timers.get(currentPlayerKey).schedule();
	}
	
	public void switchedPlayer(String newPlayerKey) {
		stopTimer();
		currentPlayerKey = newPlayerKey;
		timers.get(currentPlayerKey).schedule();
	}
	
	public CgramTimeTask getCurrentTimeTask() {
		return timers.get(currentPlayerKey).getTimeTask();
	}
	
	/* Sets the last elapsed time for the current player
	 * and then tells all players to add to their total
	 */
	public void finishedPuzzle() {
		// TODO: Can I just stop Timer here?
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		
		Player player = null;
		String playerKey = null;
		while (it.hasNext()) {
			player = it.next().getValue();
			playerKey = "Player " + player.getPlayerNum();
			if (currentPlayerKey == playerKey) {
				player.getPlayerTime().updateRoundTime(getElapsedTime());
			}
			
			player.getPlayerTime().finishedPuzzle();
		}
		
		stopTimer();
	}
}
