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
	public void testGetDisplayCharacters() {
		// Commented out after testing to make sure they don't interfere
		//fail("Not yet implemented");
		/*
		String displayChars = null;
		assertNotNull(spaces.getList());
		assertNull(displayChars);
		spaces.setDisplayCharacters();
		displayChars = spaces.getDisplayCharacters();
		assertNotNull(displayChars);
		System.out.println("\n\n" + displayChars);
		*/
	}
	
	@Test
	public void testGetCharToDisplay() {
		// Commented out after testing to make sure they don't interfere
		/*
		// First make sure alphabet has been randomized
		testGetDisplayCharacters();
		
		System.out.println("\nGetChartoDisplay:");
		// Now show getting individual characters one by one
		System.out.println(spaces.getDisplayCharacters());
		System.out.print(spaces.getCharToDisplay());
		System.out.print(spaces.getCharToDisplay());
		System.out.print(spaces.getCharToDisplay());
		System.out.print(spaces.getCharToDisplay());
		System.out.print(spaces.getCharToDisplay());
		System.out.print("\n\n\n");
		assertNotSame('-', spaces.getCharToDisplay());
		*/
	}
	
	@Test
	public void testPrint() {
		//fail("You suck, failure!");
		assertNotNull(spaces.getList());
		
		// Should error - nothing added yet
		//assertEquals(1, spaces.getList().size());
		
		String testPhrase = ". a, b' c! d? e; f: g";
		spaces.create(testPhrase);
		
		// Determine what to print based on info from each Space
		StringBuilder codeLetters = new StringBuilder();
		for (Space space : spaces.getList()) {
			if (!space.isUnderlined()) {
				// Not underlined means space or punctuation
				// Display as is
				System.out.print(space.getDisplayChar());
				codeLetters.append(" ");
			} else {
				// Underlined means a Letter, only display an underline
				System.out.print("_");
				codeLetters.append(space.getDisplayChar());
				//codeLetters.append(((LetterSpace) space).getCorrectLetter());
			}
		}
		System.out.println("\n" + codeLetters);
	}

}
