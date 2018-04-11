package core.Managers;

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
	private String currentPlayerName;
	
	public PlayerManager() {
		players = new LinkedHashMap<String, Player>();
	}
	
	public void update() {
		
	}
	/*
	public void setCurrentPlayer(String playerName) {
		currentPlayerName = playerName;
	}
	*/
	public void switchPlayer() {
		boolean nextPlayer = false;
		Iterator<Entry<String, Player>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			// First run - set to first player
			if (currentPlayerName == null) {
				currentPlayerName = it.next().getKey();
				break;
			}
			
			// Change the current player
			if (nextPlayer) {
				currentPlayerName = it.next().getKey();
				nextPlayer = false;
				break;
			}
			
			// When find the current player
			// set so next iteratior changes the current player
			if (currentPlayerName.contentEquals(it.next().getKey())) {
				nextPlayer = true;
			}
		}

		// Allow for cycling back to first player
		if (!it.hasNext() && nextPlayer == true) {
			Set<String> set = players.keySet();
			currentPlayerName = set.iterator().next();
		}
		
		System.out.println("switched to player " + currentPlayerName);
	}
	
	public Player getPlayer() {
		Player player = null;
		try {
			player = players.get(currentPlayerName);
		} catch (Exception nullEx) {
			player = players.entrySet().iterator().next().getValue();
		}
		
		return player;
	}
	
	public HashMap<String, Player> getPlayers() { return players; }
	
	// Two different addPlayer methods
	// One used for adding a player with no name
	// will make the player's name
	// "Player #" based on whatever number (#) they are
	public Player addPlayer() {
		int playerNum = players.size() + 1;
		
		// Player key and Player name can be different
		String playerKey = "Player " + playerNum;
		String playerName = playerKey;
		
		// TODO: LIMIT INPUT LENGTH
		// Ask player to input name other than the default
		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Player Name");
		textInput.setHeaderText(playerKey + ", would you like to change your name?");
		
		Optional<String> result = textInput.showAndWait();
		
		if (result.isPresent() && !result.get().isEmpty()) {
			playerName = result.get();
		}
		
		Player player = new Player(playerName, playerNum);
		
		players.put(playerKey, player);
		
		return player;
	}
	
	public void removePlayer() {
		String playerToRemove = currentPlayerName;
		switchPlayer();
		if (players.containsKey(playerToRemove)) {
			players.remove(playerToRemove);
		}
	}
	
	public void removePlayer(String name) {
		if (players.containsKey(name)) {
			players.remove(name);
			System.out.println("Removed player " + name);
			System.out.println("Players left: " + players.keySet().toString());
		} else {
			System.out.println("Can't remove player");
		}
	}
	
	/* Only changes the name in the Player
	 * Does not change the key used to store the Player
	 * in the LinkedHashMap
	 */
	public void renamePlayer() {
		String playerKey = currentPlayerName;
		if (players.containsKey(playerKey)) {
			// Use Text Input Dialog to get new name of player
			TextInputDialog textInput = new TextInputDialog();
			textInput.setTitle("Rename Player");
			textInput.setHeaderText("Rename current player: " + playerKey);
			
			Optional<String> result = textInput.showAndWait();
			
			if (result.isPresent() && !result.get().isEmpty()) {
				players.get(playerKey).setName(result.get());
			}
		}
	}
}
