package core.Managers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import core.Player;

public class PlayerManager {
	private LinkedHashMap<String, Player> players;
	
	public PlayerManager() {
		players = new LinkedHashMap<String, Player>();
	}
	
	public void update() {
		
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
