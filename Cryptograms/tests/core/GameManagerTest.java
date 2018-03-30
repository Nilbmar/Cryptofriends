package core;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import core.Managers.GameManager;
import core.Spaces.Phrase;
import core.Spaces.Space;
import core.Spaces.Word;

public class GameManagerTest {
	private GameManager gameMan;

	@Before
	public void setUp() throws Exception {
		gameMan = new GameManager();
	}
	
	@Test
	public void getPuzzleTest() {
		Phrase phrase = gameMan.getPuzzle("I love  - 300 Monkeys : bye!!");
		System.out.println("\n\n--getPuzzleTest--\n");
		for (Word word : phrase.getPhrase()) {
			for (Space space : word.getWord()) {
				System.out.print(space.getDisplayChar());
			}
		}
	}

	//@Test
	public void addPlayerTest() {
		//fail("Not yet implemented");
		gameMan.getPlayerManager().addPlayer("ZebraTwo");
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer("Steve");
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer();
		gameMan.getPlayerManager().addPlayer("George Anne");
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
