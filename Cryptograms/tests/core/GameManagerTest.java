package core;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import core.Data.Player;
import core.Managers.GameManager;

public class GameManagerTest {
	private GameManager gameMan;

	@Before
	public void setUp() throws Exception {
		gameMan = new GameManager();
	}

	//@Test
	public void addPlayerTest() {
		//fail("Not yet implemented");
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer();
		assertNotNull(gameMan);
		
		// Testing iteration
		System.out.println("GM Players: " + gameMan.getPlayerManager().getPlayers().size());
		for (Entry<String, Player> player : gameMan.getPlayerManager().getPlayers().entrySet()) {
			System.out.println("Name: " + player.getValue().getName());
		}
		
		// Testing that iteration is consistent
		// had to use LinkedHashMap because
		// regular HashMap does not return consistent order
		for (Entry<String, Player> player : gameMan.getPlayerManager().getPlayers().entrySet()) {
			System.out.println("Name: " + player.getValue().getName() 
					+ " : " + player.getValue().getPlayerNum());
		}
	}

}
