package core.Data;

public class AnswerData {
	private String answeredChar = null;
	private String playerKey = null;
	/* TODO: May need to change playerKey to playerName in case a player doesn't return
	* but also don't want changing name in middle of game to affect score
	* must stop changing name in middle of game before changing this
	*/
	
	public AnswerData(String answeredChar, String playerKey) {
		setAnswer(answeredChar, playerKey);
	}
	
	public String getAnsweredChar() { return answeredChar; }
	public String getPlayerKey() { return playerKey; } 
	public void setAnswer(String answeredChar, String playerKey) {
		this.answeredChar = answeredChar;
		this.playerKey = playerKey;
	}
}
