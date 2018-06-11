package core.Managers;

import java.util.concurrent.ThreadLocalRandom;

import Cryptofriends.CgramController;
import Cryptofriends.GUI.PopInBox;
import Cryptofriends.SpaceContainer.SpaceBox;
import Enums.MoveDirections;
import core.Data.AnswerData;
import core.Data.Player;
import core.Data.PuzzleData;
import core.Data.PuzzleState;
import core.Loaders.PuzzleLoader;
import core.Loaders.PuzzleStateLoader;
import core.Processes.SavePuzzleState;

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
	private PuzzleState puzzleState = null;
	
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
	public void setPuzzleState(PuzzleState.State state) {
		puzzleState.setState(state);
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
	
	/* Allows movement between SpaceBoxes with arrow keys
	 * moveHilightVertically for Up (-1) / Down (1)
	 * moveHilightHorizontally for Left (-1) / Right (1)
	 */
	public void moveSelection(MoveDirections dir) {
		if (!boardMan.getFlowBox().isDisabled()) {
			int directionToMove = 0;
			
			switch (dir) {
			case UP:
				directionToMove = -1;
				boardMan.moveHilightVertically(directionToMove);
				break;
			case DOWN:
				directionToMove = 1;
				boardMan.moveHilightVertically(directionToMove);
				break;
			case LEFT:
				directionToMove = -1;
				boardMan.moveHilightHorizontally(directionToMove);
				break;
			case RIGHT:
				directionToMove = 1;
				boardMan.moveHilightHorizontally(directionToMove);
				break;
			default:
				break;
			}
		}
	}
	
	public void alertWinner() {
		PopInBox gameWon = new PopInBox(controller.getStackPane());
		gameWon.winnerBox(this, puzzleState.getWinner());
	}
	
	public void gameWon() {
		setPuzzleState(PuzzleState.State.WON);
		String winner = playerMan.getCurrentPlayer().getName();
		savePuzzleState();
		alertWinner();
	}
	
	private void savePuzzleState() {
		SavePuzzleState savePuzzleState = new SavePuzzleState(puzzleState);
		savePuzzleState.save();
	}
	
	public void loadNewPuzzle() {		
		boolean stateLoadedFromFile = false;
		// Don't update for skipping puzzles
		// only when a puzzle is Failed or Won
		if (puzzleState != null) {
			// Saves puzzle state before moving on
			savePuzzleState();
			
			AnswerData answerData = null;
			switch(puzzleState.getState()) {
			case PLAYING:
				// TODO: Remove this testing clock
				/*
				System.out.println("GameMan - PuzzleState:");
				for (int x = 0; x < boardMan.getTotalLetters(); x++) {
					answerData = puzzleState.getAnswerData(x);
					System.out.println(answerData.getAnsweredChar() + " by " + answerData.getPlayerKey());
				}*/
				
				// Save information about answered letters
				break;
			case FAILED:
				// TODO: Should players that didn't Reveal get any points??
				// Certainly shouldn't be 100%, which this currently does
				scoreMan.updateForNewPuzzle();
				break;
			case WON:
				// TODO: Remove this testing clock
				/*
				System.out.println("GameMan - PuzzleState:");
				for (int x = 0; x < boardMan.getTotalLetters(); x++) {
					answerData = puzzleState.getAnswerData(x);
					System.out.println(answerData.getAnsweredChar() + " by " + answerData.getPlayerKey());
				}*/
				
				scoreMan.updateForNewPuzzle();
				break;
			}
		}
		
		// Clear game board
		boardMan.getFlowBox().setDisable(false);
		boardMan.getFlowBox().clear();
		
		
		// Create new game board
		try {			
			PuzzleData puzzleData = puzzleMan.getPuzzle(puzzleIndex);
			boardMan.setupPuzzle(puzzleData.getPuzzle());
			
			String fileName = puzzleData.getAuthor() 
					+ "_" + puzzleData.getSubject() 
					+ "_" + puzzleData.getNumber();
			
			PuzzleStateLoader stateLoader = new PuzzleStateLoader();
			stateLoader.setTarget(fileName);
			stateLoader.load();
			
			// Load PuzzleState if a file exists
			// otherwise create one
			if (stateLoader.getPuzzleState() != null) {
				puzzleState = stateLoader.getPuzzleState();
				stateLoadedFromFile = true;
			} else {
				int puzzleSize = boardMan.getTotalLetters();
				puzzleState = new PuzzleState(puzzleSize, fileName);
				setPuzzleState(PuzzleState.State.PLAYING);
			}
			
			String author = puzzleData.getAuthor();
			String subject = puzzleData.getSubject();
			controller.updateAuthorLine(author, subject);
			
			// Keep the "next puzzle" updated
			puzzleIndex++;
			
			// Reset alignment for punctuation
			boardMan.setupPuncAlignment();
			
			if (stateLoadedFromFile) {
				answerMan.loadAnswers();
			}
			answerMan.setHints(puzzleData.getHints());
			
			
			updatePlayerInfoBox();
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
	
	public void clearPuzzle() {
		boardMan.clearPuzzle();
	}
	
	public void setAnswer(String answer) {
		// Setting time
		Player player = playerMan.getCurrentPlayer();
		String currentPlayerKey = "Player " + player.getPlayerNum();
		if (player.getMovesThisTurn() == 0) {
			timeMan.startTimer(currentPlayerKey);
		} else {
			//controller.updatePlayerTime(timeMan.getTimeElapsed());
		}
		
		
		answerMan.setAnswer(answer, currentPlayerKey);
		player.moved();		
	}
	
	/* Reveals */
	public void displayLetter() {
		int letterOccurances = answerMan.displayLetter();
		int boardSize = boardMan.getTotalLetters();

		// Update Score
		String playerKey = "Player " + playerMan.getCurrentPlayer().getPlayerNum();
		scoreMan.playerRevealedLetter(playerKey, letterOccurances, boardSize);
		updatePlayerInfoBox();
	}
	
	// Puzzle Will Be Lost - Resulting in a Zero for the score
	public void displayAllLetters() {
		answerMan.displayAllLetters();
		
		// Update Score
		String playerKey = "Player " + playerMan.getCurrentPlayer().getPlayerNum();
		scoreMan.playerRevealedPuzzle(playerKey);
		// TODO: IF MULTIPLAYER
		// OTHER PLAYERS SHOULD GET PERCENTAGE THAT THEY ANSWERED CORRECTLY STILL
		
		// Set board as failed
		setPuzzleState(PuzzleState.State.FAILED);
		boardMan.getFlowBox().setDisable(true);
		
		timeMan.finishedPuzzle();
		updatePlayerInfoBox();
	}
	
	public void hilightIncorrect() {
		SpaceBox selectedBox = boardMan.getCurrentlySelected();
		int numFilled = answerMan.getFilledSpaceBoxes().size();
		
		// Only needs to run if something has been Selected and Filled
		if (selectedBox != null && numFilled > 0) {
			int numOfWrongAnswers = answerMan.hilightIncorrect(selectedBox);
			
			// Only needs to run if there is a Wrong answer, empty spaces don't count
			if (numOfWrongAnswers > 0) {
				String playerKey = "Player " + playerMan.getCurrentPlayer().getPlayerNum();
				scoreMan.playerHilightedIncorrect(playerKey, numOfWrongAnswers);
				controller.showClearIncorrectBtns();
			}
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
		updatePlayerInfoBox();
	}
	
	public void removePlayer(int numOfPlayer) {
		playerMan.removePlayer(numOfPlayer);
		updatePlayerInfoBox();
	}
	
	public void switchPlayer() {
		playerMan.switchPlayer();
		String newPlayerKey = "Player " + playerMan.getCurrentPlayer().getPlayerNum();
		timeMan.switchedPlayer(newPlayerKey);
		
		updatePlayerInfoBox();
		System.out.println("Player scores: " 
			+ scoreMan.getPlayerScoreData(newPlayerKey).getScore()
			+ " totalScore: "
			+ scoreMan.getPlayerScoreData(newPlayerKey).getTotalScore());
	}
	
	public void updatePlayerInfoBox() {
		Player player = playerMan.getCurrentPlayer();
		
		if (player != null) {
			String playerKey = "Player " + player.getPlayerNum();
			int score = (int) scoreMan.getPlayerScoreData(playerKey).getScore();
			controller.updatePlayerInfoBox(player.getName(), score);
		}
	}
}
