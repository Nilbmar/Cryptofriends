package core;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameManager {
	private LinkedHashMap<String, Player> players;
	
	public GameManager() {
		players = new LinkedHashMap<String, Player>();
	}
	
	public HashMap<String, Player> getPlayers() { return players; }
	public void addPlayer() {
		int playerNum = players.size() + 1;
		String playerName = "Player " + playerNum;
		Player player = new Player(playerName, playerNum);
		
		players.put(playerName, player);
	}
	public void addPlayer(String name) {
		int playerNum = players.size() + 1;
		Player player = new Player(name, playerNum);
		
		players.put(name, player);
	}

}
