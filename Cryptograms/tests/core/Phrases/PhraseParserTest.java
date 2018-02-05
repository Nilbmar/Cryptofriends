package core.Phrases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhraseParserTest {
	private PhraseParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new PhraseParser(",I. l:::o;;ve!? m?o,.nkeys? Don't you????!");
	}
	
	@Test
	public void testIsBlank() {
		assertNotNull(parser.getOrigPhrase());
		//assertTrue(parser.isBlankSpace("Monkey"));
		assertFalse(parser.isBlankSpace("M"));
		assertTrue(parser.isBlankSpace(" "));
		//assertFalse(parser.isBlankSpace(" "));
		String testPhrase = " I love donkey chops with potato locks";
		int blanksCount = 0;
		int len = testPhrase.length();
		for (int x = 0; x < len; x++) {
			if (parser.isBlankSpace(testPhrase.substring(x, x+1))) {
				blanksCount++;
			} else {
				//System.out.print(testPhrase.substring(x, x+1));
			}
		}
		
		assertEquals(7, blanksCount);
	}
	
	@Test
	public void testIsPunctuation() {
		assertNotNull(parser.getOrigPhrase());
		
		//System.out.println("Punctuation Test:\n" 
		//		+ parser.getOrigPhrase() + "\n");
		
		StringBuilder strBuild = new StringBuilder();
		int len = parser.getOrigPhrase().length();
		for (int x = 0; x < len; x++) {
			if (parser.isPunctuation(parser.getOrigPhrase().substring(x, x+1))) {
				//System.out.print(parser.getOrigPhrase().charAt(x));
				strBuild.append(parser.getOrigPhrase().charAt(x));
			}
		}
		//System.out.println("\n" + strBuild.toString());
		
		assertTrue(strBuild.toString().contentEquals(",.:::;;!??,.?'????!"));
	}
	
	@Test
	public void printOnlyLetters() {
		//fail("Not done anything yet!");
		assertNotNull(parser.getOrigPhrase());
		String testPhrase = "Howdy! I like bananas; I like bananas a lot. "
				+ "Don't you like bananas?"
				+ "Would you: eat bananas, not eat bananas, or unsure?";
		
		int len = testPhrase.length();
		for (int x = 0; x < len; x++) {
			if (!parser.isPunctuation(testPhrase.substring(x, x+1))
			&& !parser.isBlankSpace(testPhrase.substring(x, x+1))) {
				System.out.print(testPhrase.substring(x, x+1));
			}
		}
	}
	
}
