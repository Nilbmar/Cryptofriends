package core.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Processes.SavePlayerTime;

public class PlayerTime {
	private long roundTime = 0;
	private long puzzleTime = 0;
	private long totalTime = 0;
	private Player player = null;
	private HashMap<String, Long> puzzleTimes = new HashMap<String, Long>();
	
	public PlayerTime(Player player) {
		this.player = player;
	}
	
	public long getRoundTime() { return roundTime; }
	public void updateRoundTime(long time) {
		roundTime += time;
	}
	
	public long getPuzzleTime() { return puzzleTime; }
	public void updatePuzzleTime(long time) {
		puzzleTime += time;
		if (player.getCurrentPuzzleName() != null) {
			puzzleTimes.put(player.getCurrentPuzzleName(), puzzleTime);
		}
	}
		
	public long getTotalTime() { return totalTime; }
	
	public void savePlayerTime() {
		SavePlayerTime saveTime = new SavePlayerTime(
				this, player.getName());
		saveTime.save();
	}
	
	public void loadTimeForCurPuzzle() {
		if (puzzleTimes.containsKey(player.getCurrentPuzzleName())) {
			puzzleTime = puzzleTimes.get(player.getCurrentPuzzleName());
		} else {
			roundTime = 0;
			puzzleTime = 0;
		}
	}
		
	public void finishedPuzzle() {
		totalTime += puzzleTime;
	}
	
	public void loadPrevTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public void loadPrevPuzzleTimes(String puzzleName, long time) {
		puzzleTimes.put(puzzleName, time);
	}
	
	public String[] getPuzzleTimes() {
		ArrayList<String> puzzleTimesList = new ArrayList<String>();
		for (Map.Entry<String, Long> entry : puzzleTimes.entrySet()) {
			puzzleTimesList.add(entry.getKey() + ":" + entry.getValue());
		}
		
		String[] times = puzzleTimesList.toArray(new String[puzzleTimesList.size()]);
		return times;
	}
	
	public void printTimes() {
		System.out.println("Total Time: " + totalTime);
		
		for(Map.Entry<String, Long> entry : puzzleTimes.entrySet()) {
			System.out.println("Puzzle: " + entry.getKey() + " - Time: " + entry.getValue());
		}
	}
}
