package core.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import core.Data.Player;
import javafx.scene.control.TextInputDialog;

public class PlayerManager {
	private LinkedHashMap<String, Player> players;
	private String currentPlayerName = null;
	private int playerCount = 0;
	
	
	public PlayerManager() {
		players = new LinkedHashMap<String, Player>();
	}
	
	public void update() {
		
	}
	
	public String switchPlayer() {
		boolean setNextPlayerToCurrent = false;
		players.get(currentPlayerName).setTurn(false);
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			// First run - set to first player
			if (currentPlayerName == null) {
				currentPlayerName = it.next().getKey();
				break;
			}
			
			// Change the current player
			// This is run in an iteration after
			// the if that sets nextPlayer
			if (setNextPlayerToCurrent) {
				currentPlayerName = it.next().getKey();
				setNextPlayerToCurrent = false;
				break;
			}
			
			// When find the current player
			// set so next iteratior changes the current player
			if (currentPlayerName.contentEquals(it.next().getKey())) {
				setNextPlayerToCurrent = true;
			}
		}

		// Allow for cycling back to first player
		if (!it.hasNext() && setNextPlayerToCurrent == true) {
			Set<String> set = players.keySet();
			currentPlayerName = set.iterator().next();
		}
		
		System.out.println("switched to player " + currentPlayerName);
		players.get(currentPlayerName).setTurn(true);
		return currentPlayerName;
	}
	
	public HashMap<String, Player> getPlayers() { return players; }
	public ArrayList<Player> getPlayersArrayList() { 
		ArrayList<Player> playersArray = new ArrayList<Player>();
		
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			playersArray.add(it.next().getValue());
		}
		
		return playersArray;
	}
	public Player getCurrentPlayer() {
		Player player = null;
		
		if (currentPlayerName == null && players.size() > 0) {
			currentPlayerName = players.get("Player 1").getName();
		}
		
		try {
			player = players.get(currentPlayerName);
		} catch (Exception nullEx) {
			player = players.entrySet().iterator().next().getValue();
		}
		
		return player;
	}
	
	// Two different addPlayer methods
	// One used for adding a player with no name
	// will make the player's name
	// "Player #" based on whatever number (#) they are
	public Player addPlayer() {
		Player player = null;
		
		playerCount++;
		int playerNum = playerCount;
		
		// Player key and Player name can be different
		String playerKey = "Player " + playerNum;
		String playerName = playerKey;
		
		/*
		// TODO: LIMIT INPUT LENGTH
		// Ask player to input name other than the default
		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Player Name");
		textInput.setHeaderText(playerKey + ", would you like to change your name?");
		
		Optional<String> result = textInput.showAndWait();
		
		if (result.isPresent() && !result.get().isEmpty()) {
			playerName = result.get();
		}
		*/
		player = new Player(playerName, playerNum);
		
		players.put(playerKey, player);
		System.out.println("Creating player" + player.getName());
		
		return player;
	}
	
	public void removePlayer(int numOfPlayer) {
		String playerToRemove = "Player " + numOfPlayer;
		if (players.containsKey(playerToRemove) && players.size() > 1) {
			String nameOfPlayerToRemove = players.get(playerToRemove).getName();
			
			if (currentPlayerName.contentEquals(nameOfPlayerToRemove)) {
				switchPlayer();
			}
			
			players.get(playerToRemove).setRemoved(true);
			
			players.remove(playerToRemove);
		} else {
			System.out.println("Could not remove player.");
		}
	}
	
	/* Only changes the name in the Player
	 * Does not change the key used to store the Player
	 * in the LinkedHashMap
	 */
	public String renamePlayer(int numOfPlayer) {
		String playerKey = "Player " + numOfPlayer;
		String nameToReturn = null;
		
		if (players.containsKey(playerKey)) {
			// Use Text Input Dialog to get new name of player
			TextInputDialog textInput = new TextInputDialog();
			textInput.setTitle("Rename Player");
			textInput.setHeaderText("Rename current player: " + playerKey);
			
			Optional<String> result = textInput.showAndWait();
			
			if (result.isPresent() && !result.get().isEmpty()) {
				nameToReturn = result.get();
				players.get(playerKey).setName(nameToReturn);
			}
		} else {
			System.out.println(playerKey + " doesn't exist.");
		}
		
		return nameToReturn;		
	}
}
