package core.Data;

public class PlayerTime {
	private long puzzleTime = 0;
	private long totalTime = 0;
	
	public PlayerTime() {
	}
	
	public long getPuzzleTime() { return puzzleTime; }
	public void updatePuzzleTime(long time) {
		puzzleTime += time;
	}
	
	public long getTotalTime() { return totalTime; }
		
	public void finishedPuzzle() {
		totalTime += puzzleTime;
	}
}
