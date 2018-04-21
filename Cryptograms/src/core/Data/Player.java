package core.Data;

public class Player {
	private String name;
	private int playerNum;
	private int score;
	private int movesThisTurn;
	private int totalMoves;
	private boolean turn;
	private boolean removed;
	
	public Player(String name, int num) {
		this.name = name;
		playerNum = num;
	}
	
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
}
