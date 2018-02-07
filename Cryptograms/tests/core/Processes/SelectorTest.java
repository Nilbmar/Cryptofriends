package core.Processes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Observers.SelectionObserver;
import core.Spaces.LetterSpace;
import core.Spaces.Space;
import core.Spaces.SpaceContainer;

public class SelectorTest {
	private SpaceContainer spaces;
	private SelectionObserver selectObs;
	
	@Before
	public void setUp() throws Exception {
		spaces = new SpaceContainer();
		String testPhrase = "I'm a donkey; I'm a lovely donkey. Do you hate me?";
		spaces.create(testPhrase);
		selectObs = new SelectionObserver();
	}

	@Test
	public void testSetSelected() {
		//fail("Not yet implemented");
		int random = 2;
		Space spaceToHilight = spaces.getList().get(random);
		
		if (spaceToHilight.isUnderlined()) {
			((LetterSpace) spaceToHilight).addObserver(selectObs);
			((LetterSpace) spaceToHilight).notifyObserver();
		}
		
		
	}

}
