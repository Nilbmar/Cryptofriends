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
	//private Timer timer;
	
	public TimeManager(Label lblTime) {
		this.lblTime = lblTime;
		players = new HashMap<String, Player>();
		timers = new HashMap<String, CgramTimer>();
		//timer = new Timer(true);
	}
	
	public void addPlayer(Player player) {
		String playerKey = "Player " + player.getPlayerNum();
		if (!players.containsKey(playerKey)) {
			players.put(playerKey, player);
			//addTimeTaskToPlayer(playerKey);
			CgramTimer cTimer = new CgramTimer(this, player, lblTime);
			timers.put(playerKey, cTimer);
			
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
		long elapsedTime = getTimeElapsed();
		//System.out.println(currentPlayerKey + " elapsedTime: " + elapsedTime);
		players.get(currentPlayerKey).getPlayerTime().updateRoundTime(elapsedTime);
	}
	
	private void stopTimer() {
		updateTimer();
		System.out.print("\nstopTimer: puzzleTime - ");
		System.out.print(getTimeElapsed() + "\n\n");
		players.get(currentPlayerKey).getPlayerTime().updatePuzzleTime(getTimeElapsed());
		System.out.print("\nstopTimer: puzzleTime - ");
		System.out.print(getTimeElapsed() + "\n\n");
		startingTime = 0;
		
		// Remove old timer for player being switched from
		timers.get(currentPlayerKey).cancel();
		timers.remove(currentPlayerKey);
		
		// Create new timer for player being switched from
		CgramTimer cTimer = new CgramTimer(this, players.get(currentPlayerKey), lblTime);
		timers.put(currentPlayerKey, cTimer);
		//currentPlayerKey = null;
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
		return elapsedTime;
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
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		
		Player player = null;
		String playerKey = null;
		while (it.hasNext()) {
			player = it.next().getValue();
			playerKey = "Player " + player.getPlayerNum();
			if (currentPlayerKey == playerKey) {
				player.getPlayerTime().updateRoundTime(getTimeElapsed());
			}
			
			player.getPlayerTime().finishedPuzzle();
		}
	}
}
