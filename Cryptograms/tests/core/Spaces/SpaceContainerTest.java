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

}
