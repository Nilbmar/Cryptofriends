package core.Managers;

import java.util.concurrent.ThreadLocalRandom;

import Cryptofriends.CgramController;
import Cryptofriends.SpaceContainer.FlowBox;
import core.Builders.BoardBuilder;
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
	private BoardBuilder boardBuilder;
	private PuzzleManager puzzleMan = new PuzzleManager();
	private PuzzleLoader sqlLoader = new PuzzleLoader(puzzleMan);
	private int puzzleIndex = 0;
	
	
	//private PuzzleLoader sqlLoader = new PuzzleLoader(puzzleMan);
	private PuzzleState puzzleState = PuzzleState.PLAYING;
	
	public GameManager(CgramController controller) {
		this.controller = controller;
		playerMan = new PlayerManager();
		selectMan = new SelectionManager();
		scoreMan = new ScoreManager();
		answerMan = new AnswerManager(this);
		boardBuilder = new BoardBuilder(this, controller);
		
		
		// Create default player
		addPlayer();
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
	public BoardBuilder getBoardBuilder() { return boardBuilder; }
	
	public void createBoard() {
		//flow = new FlowBox();
		//flow.setSpacesPerLine(15);
		boardBuilder.setupFlowBox();
		
		//anchor.getChildren().add(flow);
		
		answerMan.setFlowBox(boardBuilder.getFlowBox());

		puzzleIndex = 1;
		sqlLoader.setTarget(Integer.toString(puzzleIndex));
		sqlLoader.load();
	}
	public void loadNewPuzzle() {
		// Clear game board
		boardBuilder.getFlowBox().setDisable(false);
		boardBuilder.getFlowBox().clear();
		setPuzzleState(PuzzleState.PLAYING);
		
		// Create new game board
		try {			
			PuzzleData puzzleData = puzzleMan.getPuzzle(puzzleIndex);
			boardBuilder.setupPuzzle(puzzleData.getPuzzle());
			
			String author = puzzleData.getAuthor();
			String subject = puzzleData.getSubject();
			controller.updateAuthorLine(author, subject);
			
			// Keep the "next puzzle" updated
			puzzleIndex++;
			
			// Reset alignment for punctuation
			boardBuilder.getFlowBox().setupPuncAlignment();
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
	
	public void addPlayer() {
		// Returned so it can be added to menu
		Player player = playerMan.addPlayer();
		controller.addPlayerMenuItem(player);
		String playerKey = "Player " + player.getPlayerNum();
		scoreMan.addPlayer(playerKey);
	}
	
	public void renamePlayer(int numOfPlayer) {
		playerMan.renamePlayer(numOfPlayer);
	}
	
	public void removePlayer(int numOfPlayer) {
		playerMan.removePlayer(numOfPlayer);
	}
	
	public void switchPlayer() {
		playerMan.switchPlayer();
	}
}
