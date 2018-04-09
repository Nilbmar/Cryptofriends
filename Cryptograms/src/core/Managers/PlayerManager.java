package core.Managers;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
		String currentName = null;
		while (players.entrySet().iterator().hasNext()) {
			currentName = players.entrySet().iterator().next().getKey();
			
			if (currentPlayerName != null && currentName.contentEquals(currentPlayerName)) {
				System.out.println("not null and contents equal");
				currentPlayerName = players.entrySet().iterator().next().getKey();
				break;
			}
			
			if (currentPlayerName == null) {
				System.out.println("player name null");
				currentPlayerName = players.entrySet().iterator().next().getKey();
				break;
			}
		}
	}
	
	public Player getPlayer() {
		Player player = null;
		try {
			System.out.println("Trying to get currentPlayerName");
			player = players.get(currentPlayerName);
			System.out.println("Got currentPlayerName");
		} catch (Exception nullEx) {
			player = players.entrySet().iterator().next().getValue();
			//player = players.get(players.entrySet()
			System.out.println("Current player not set.");
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
