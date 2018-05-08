package core.Managers;

import java.util.concurrent.ThreadLocalRandom;

import Cryptofriends.CgramController;
import Cryptofriends.SpaceContainer.SpaceBox;
import core.Data.Player;
import core.Data.PuzzleData;
import core.Data.PuzzleState;
import core.Loaders.PuzzleLoader;

public class GameManager {
	private CgramController controller;
	private SelectionManager selectMan;
	private AnswerManager answerMan;
	private PlayerManager playerMan;
	private ScoreManager scoreMan;
	private TimeManager timeMan;
	private BoardManager boardMan;
	private PuzzleManager puzzleMan;
	private PuzzleLoader sqlLoader;
	private int puzzleIndex = 0;	
	private PuzzleState puzzleState = PuzzleState.PLAYING;
	
	public GameManager(CgramController controller) {
		this.controller = controller;
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
		scoreMan = new ScoreManager();
		timeMan = new TimeManager(controller.getTimeLabel());
		
		answerMan = new AnswerManager(this);
		boardMan = new BoardManager(this);
		puzzleMan = new PuzzleManager();
		sqlLoader = new PuzzleLoader(puzzleMan);
		
		
		// Create default player
		addPlayer();
		playerMan.getCurrentPlayer().setTurn(true);
	}
	
	public void update() {
		playerMan.update();
		selectMan.update();
	}
	
	public PuzzleState getPuzzleState() { return puzzleState; }
	public void setPuzzleState(PuzzleState puzzleState) { 
		this.puzzleState = puzzleState; 
	}
	
	public PlayerManager getPlayerManager() { return playerMan; }
	public AnswerManager getAnswerManager() { return answerMan; }
	public SelectionManager getSelectionManager() { return selectMan; }
	public ScoreManager getScoreManager() { return scoreMan; }
	public TimeManager getTimeManager() { return timeMan; }
	public BoardManager getBoardManager() { return boardMan; }
	
	public void createBoard() {
		boardMan.setupFlowBox();
				
		answerMan.setFlowBox(boardMan.getFlowBox());

		puzzleIndex = 1;
		sqlLoader.setTarget(Integer.toString(puzzleIndex));
		sqlLoader.load();
	}
	
	public void loadNewPuzzle() {
		// Clear game board
		boardMan.getFlowBox().setDisable(false);
		boardMan.getFlowBox().clear();
		setPuzzleState(PuzzleState.PLAYING);
		
		// Create new game board
		try {			
			PuzzleData puzzleData = puzzleMan.getPuzzle(puzzleIndex);
			boardMan.setupPuzzle(puzzleData.getPuzzle());
			
			String author = puzzleData.getAuthor();
			String subject = puzzleData.getSubject();
			controller.updateAuthorLine(author, subject);
			
			// Keep the "next puzzle" updated
			puzzleIndex++;
			
			// Reset alignment for punctuation
			boardMan.setupPuncAlignment();
		}
		catch (NullPointerException nullEx) {
			System.out.println("Null Pointer: Reseting to start of puzzle file");
			puzzleIndex = 1;
			loadNewPuzzle();
		}
	}
	
	public void loadRandomPuzzle() {
		int numOfPuzzles = puzzleMan.count();
		int puzzleNum = ThreadLocalRandom.current().nextInt(0, numOfPuzzles);
		puzzleIndex = puzzleNum;
		loadNewPuzzle();
	}
	
	public void setAnswer(String answer) {
		answerMan.setAnswer(answer);
		
		// Setting time
		Player player = playerMan.getCurrentPlayer();
		String currentPlayerKey = "Player " + player.getPlayerNum();
		if (player.getMovesThisTurn() == 0) {
			timeMan.startTimer(currentPlayerKey);
		} else {
			
			//controller.updatePlayerTime(timeMan.getTimeElapsed());
		}
		
		player.moved();
	}
	
	/* Reveals */
	public void displayLetter() {
		answerMan.displayLetter();
	}
	
	public void displayAllLetters() {
		answerMan.displayAllLetters();
		timeMan.finishedPuzzle();
	}
	
	public void hilightIncorrect() {
		// Only needs to run if something has been selected on this puzzle
		// And there are items that are incorrect
		// TODO: IS CURRENTLY SHOWING EVEN IF NO INCORRECT
		// IS ANSWERMAN ACTUALLY GETTING INCORRECT??
		SpaceBox selectedBox = boardMan.getCurrentlySelected();
		int numFilled = answerMan.getFilledSpaceBoxes().size();
		int numIncorrect = answerMan.getIncorrectSpaceBoxes().size();
		
		if (selectedBox != null && numFilled > 0 && numIncorrect > 0) {
			answerMan.hilightIncorrect();
			controller.showClearIncorrectBtns();
		}
	}
	
	public void clearIncorrect() {
		answerMan.clearIncorrect();
		controller.showClearIncorrectBtns();
	}
	
	/* Player Management */
	public void addPlayer() {
		// Returned so it can be added to menu
		Player player = playerMan.addPlayer();
		controller.addPlayerMenuItem(player);
		String playerKey = "Player " + player.getPlayerNum();
		scoreMan.addPlayer(playerKey);
		timeMan.addPlayer(player);
	}
	
	public void renamePlayer(int numOfPlayer) {
		playerMan.renamePlayer(numOfPlayer);
	}
	
	public void removePlayer(int numOfPlayer) {
		playerMan.removePlayer(numOfPlayer);
	}
	
	public void switchPlayer() {
		playerMan.switchPlayer();
		String newPlayerKey = "Player " + playerMan.getCurrentPlayer().getPlayerNum();
		timeMan.switchedPlayer(newPlayerKey);
		
	}
}
