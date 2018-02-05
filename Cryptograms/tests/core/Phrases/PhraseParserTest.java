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
		
	}
	
	@Test
	public void testIsPunctuation() {
		assertNotNull(parser.getOrigPhrase());
		
		System.out.println("Punctuation Test:\n" 
				+ parser.getOrigPhrase() + "\n");
		
		StringBuilder strBuild = new StringBuilder();
		int len = parser.getOrigPhrase().length();
		for (int x = 0; x < len; x++) {
			if (parser.isPunctuation(parser.getOrigPhrase().substring(x, x + 1))) {
				System.out.print(parser.getOrigPhrase().charAt(x));
				strBuild.append(parser.getOrigPhrase().charAt(x));
			}
		}
		System.out.println("\n" + strBuild.toString());
		
		assertTrue(strBuild.toString().contentEquals(",.:::;;!??,.?'????!"));
	}
	
	
}
