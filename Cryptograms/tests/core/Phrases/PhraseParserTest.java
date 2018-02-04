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
	public void testGetWords() {
		//fail("Not yet implemented");
		

		assertNotNull(parser.getWords());
		for (String s : parser.getWords()) {
			System.out.print(s + " ");
		}
		System.out.print("\n\n\n");
		
		System.out.println("Blank Words:");
		for (String s : parser.getWords()) {
			System.out.println(s + " " + parser.isWordBlank(s));
		}
	}

}
