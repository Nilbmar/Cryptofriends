package core.Processes;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

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
		// Picks a random space
		int random = ThreadLocalRandom.current().nextInt(0, spaces.getList().size() - 1);
		Space spaceToHilight = spaces.getList().get(random);
		
		// If its a LetterSpace
		// Gives SelectionObserver it's display char
		// so SelectionObserver can also tell others that share char
		// they should be hilighted
		// Then notifies SelectionObserver to update
		if (spaceToHilight.isUnderlined()) {
			((LetterSpace) spaceToHilight).addObserver(selectObs);
			((LetterSpace) spaceToHilight).notifyObserver();
		}
		
		
	}

}
