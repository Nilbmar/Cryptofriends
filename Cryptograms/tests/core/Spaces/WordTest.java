package core.Spaces;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WordTest {
	private SpaceList spaces;

	@Before
	public void setUp() throws Exception {
		spaces = new SpaceList();
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertNotNull(spaces);
		int blanksSpacesInTestPhrase = 4;
		String testPhrase = "I love you, so much!";
		spaces.create(testPhrase);
		Phrase phrase = spaces.getPhrase();
		
		int countBlankSpaces = 0;
		for (Word word : phrase.getPhrase()) {
			System.out.print("\n" + word.isBlankSpace());
			if (word.isBlankSpace()) {
				countBlankSpaces++;
			}
		}
		assertEquals(blanksSpacesInTestPhrase, countBlankSpaces);
		
	}

}
