package core.Data;

import java.util.ArrayList;

public class PuzzleState {
	public enum State { PLAYING, FAILED, WON; }
	private State currentState = null;
	private ArrayList<AnswerData> answeredByPlayer = null;
	private String fileName = null;
	
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
	
	public AnswerData getAnswerData(int charNum) { return answeredByPlayer.get(charNum); }
	public void answered(int charNum, String answer, String playerKey) {
		answeredByPlayer.get(charNum).setAnswer(answer, playerKey);
	}
	
	public State getState() { return currentState; }
	public void setState(State currentState) {
		this.currentState = currentState;
	}
}
