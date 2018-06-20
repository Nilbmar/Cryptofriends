package core.Data;

import java.util.HashMap;

public class PlayerTime {
	private long roundTime = 0;
	private long puzzleTime = 0;
	private long totalTime = 0;
	private HashMap<String, Long> puzzleTimes = new HashMap<String, Long>();
	
	public PlayerTime() {
	}
	
	public long getRoundTime() { return roundTime; }
	public void updateRoundTime(long time) {
		roundTime += time;
	}
	
	public long getPuzzleTime() { return puzzleTime; }
	public void updatePuzzleTime(long time) {
		puzzleTime += time;
	}
		
	public long getTotalTime() { return totalTime; }
	
	public void changedPuzzle(String oldPuzzle) {
		// TODO: KEEP DOING THIS - THIS ISN'T IMPLEMENTED AT ALL YET
		// USE IT IN GAMEMAN LOADNEWPUZZLE
		// WILL HAVE TO LOAD PREVIOU TIMES FOR NEXT PUZZLE
		puzzleTimes.put(oldPuzzle, puzzleTime);
		roundTime = 0;
		puzzleTime = 0;
	}
		
	public void finishedPuzzle() {
		totalTime += puzzleTime;
	}
}
