package core.Data;

import java.util.ArrayList;

public class PuzzleState {
	public enum State { PLAYING, FAILED, WON; }
	private State currentState = null;
	private ArrayList<AnswerData> answeredByPlayer = null;
	private String fileName = null;
	private String winner = "NONE";
	
	public PuzzleState(int totalChars, String fileName) {
		this.fileName = fileName;
		answeredByPlayer = new ArrayList<AnswerData>();
		initilizeArrayList(totalChars);
		currentState = State.PLAYING;
		
	}
	
	private void initilizeArrayList(int totalChars) {
		for (int x = 0; x < totalChars; x++) {
			answeredByPlayer.add(new AnswerData("", ""));
		}
	}
	
	public String getFileName() { return fileName; }
	
	public int getPuzzleSize() { return answeredByPlayer.size(); }
	
	public String getWinner() { return winner; }
	public void setWinner(String winner) { 
		this.winner = winner;
	}
	
	public String[] getAnswers() {
		String[] answers = new String[answeredByPlayer.size()];
		for (int x = 0; x < answeredByPlayer.size(); x++) {
			answers[x] = answeredByPlayer.get(x).getAnsweredChar()
					+ " - " + answeredByPlayer.get(x).getPlayerKey();
		}
		return answers;
	}
	
	public AnswerData getAnswerData(int charNum) { return answeredByPlayer.get(charNum); }
	public void answered(int charNum, String answer, String playerKey) {
		answeredByPlayer.get(charNum).setAnswer(answer, playerKey);
	}
	
	public State getState() { return currentState; }
	public void setState(State currentState) {
		this.currentState = currentState;
	}
	public void setState(String currentState) {
		switch (currentState.toUpperCase()) {
		case "PLAYING":
			this.currentState = State.PLAYING;
			break;
		case "FAILED":
			this.currentState = State.FAILED;
			break; 
		case "WON":
			this.currentState = State.WON;
			break;
			
		}
	}
}
