package core.Loaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PuzzleLoaderTest {
	private PuzzleLoader pLoader;

	@Before
	public void setUp() throws Exception {
		pLoader = new PuzzleLoader();
	}

	@Test
	public void testGetPhrase() {
		//fail("Not yet implemented");
		pLoader.setTarget("1");
		assertNotNull(pLoader.getPhrase());
		System.out.println(pLoader.getPhrase());
	}

}
