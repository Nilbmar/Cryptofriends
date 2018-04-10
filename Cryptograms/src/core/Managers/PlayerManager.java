package core.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import core.Data.Player;

public class PlayerManager {
	private LinkedHashMap<String, Player> players;
	private String currentPlayerName;
	
	public PlayerManager() {
		players = new LinkedHashMap<String, Player>();
	}
	
	public void update() {
		
	}
	
	public void setCurrentPlayer(String playerName) {
		currentPlayerName = playerName;
	}
	
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
	public void addPlayer() {
		int playerNum = players.size() + 1;
		String playerName = "Player " + playerNum;
		Player player = new Player(playerName, playerNum);
		
		players.put(playerName, player);
	}
	
	// Add player with a specific name
	public void addPlayer(String name) {
		int playerNum = players.size() + 1;
		Player player = new Player(name, playerNum);
		
		players.put(name, player);
	}
}
