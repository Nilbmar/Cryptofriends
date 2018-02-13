package core;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

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
	public void setPuzzleTest() {
		Phrase phrase = gameMan.setPuzzle("I love  - 300 Monkeys : bye!!");
		System.out.println("\n\n--setPuzzleTest--\n");
		for (Word word : phrase.getPhrase()) {
			for (Space space : word.getWord()) {
				System.out.print(space.getDisplayChar());
			}
		}
	}

	//@Test
	public void addPlayerTest() {
		//fail("Not yet implemented");
		gameMan.addPlayer("ZebraTwo");
		gameMan.addPlayer();
		gameMan.addPlayer();
		gameMan.addPlayer("Steve");
		gameMan.addPlayer();
		gameMan.addPlayer();
		gameMan.addPlayer("George Anne");
		assertNotNull(gameMan);
		
		// Testing iteration
		System.out.println("GM Players: " + gameMan.getPlayers().size());
		for (Entry<String, Player> player : gameMan.getPlayers().entrySet()) {
			System.out.println("Name: " + player.getValue().getName());
		}
		
		// Testing that iteration is consistent
		// had to use LinkedHashMap because
		// regular HashMap does not return consistent order
		for (Entry<String, Player> player : gameMan.getPlayers().entrySet()) {
			System.out.println("Name: " + player.getValue().getName() 
					+ " : " + player.getValue().getPlayerNum());
		}
	}

}
