package core.Spaces;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SpaceContainerTest {
	private SpaceContainer spaces;
	
	@Before
	public void setUp() throws Exception {
		spaces = new SpaceContainer();
	}

	@Test
	public void testCreate() {
		//fail("Not yet implemented");
		assertNotNull(spaces.getList());
		
		// Should error - nothing added yet
		//assertEquals(1, spaces.getList().size());
		
		String testPhrase = "I'm a donkey; I'm a lovely donkey. Do you hate me?";
		spaces.create(testPhrase);
		// Assert that a space is created for each character
		assertEquals(testPhrase.length(), spaces.getList().size());
	}
	
	@Test
	public void testPrint() {
		//fail("You suck, failure!");
		assertNotNull(spaces.getList());
		
		// Should error - nothing added yet
		//assertEquals(1, spaces.getList().size());
		
		String testPhrase = "I'm a donkey; I'm a lovely donkey. Do you hate me?";
		spaces.create(testPhrase);
		
		/*
		int sizeList = spaces.getList().size();
		for (int x = 0; x < sizeList; x++) {
			
		}*/
		
		for (Space space : spaces.getList()) {
			if (!space.isUnderlined()) {
				// Not underlined means space or punctuation
				// Display as is
				System.out.print(space.getDisplayChar());
			} else {
				// Underlined means a Letter, only display an underline
				// Will display a Code letter later
				System.out.print("_");
			}
		}
		
	}

}
