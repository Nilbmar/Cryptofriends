package core.Data;

public class Player {
	private String name;
	private PlayerTime playerTime;
	private int playerNum;
	private int score;
	private int movesThisTurn;
	private int totalMoves;
	private boolean turn;
	private boolean removed;
	private String currentPuzzleName = null;
	
	public Player(String name, int num) {
		this.name = name;
		playerNum = num;
		playerTime = new PlayerTime(this);
	}
	
	public PlayerTime getPlayerTime() { return playerTime; }
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }

	public void setNum(int num) { playerNum = num; }
	public int getPlayerNum() { return playerNum; }
	
	public void addToScore(int add) { score = score + add; }
	public int getScore() { return score; }
	
	public boolean isPlayersTurn() { return turn; }
	public void setTurn(boolean turn) { 
		this.turn = turn;
		
		if (turn) { 
			movesThisTurn = 0;
		}
	}
	
	public void setRemoved(boolean removed) { this.removed = removed; }
	public boolean getRemoved() { return removed; }
	
	public int getTotalMoves() { return totalMoves; }
	public int getMovesThisTurn() { return movesThisTurn; }
	public void moved() {
		if (turn) {
			movesThisTurn++;
			totalMoves++;
		}
	}
	
	public String getCurrentPuzzleName() { return currentPuzzleName; }
	public void setCurrentPuzzleName(String puzzleName) {
		currentPuzzleName = puzzleName;
	}
	
	public void switchedPuzzle(String newPuzzleName) {
		if (currentPuzzleName != null) {
			playerTime.savePlayerTime();
		}
		currentPuzzleName = newPuzzleName;
		playerTime.loadTimeForCurPuzzle();
		setTurn(true);
	}
	
	public void exitProgram() {
		if (currentPuzzleName != null) {
			playerTime.savePlayerTime();
		}
	}
}
