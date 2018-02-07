package core.Processes;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

import core.Observers.Observer;
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
		String testPhrase = "I love you, too!";
		spaces.create(testPhrase);
		selectObs = new SelectionObserver();
	}

	@Test
	public void testSetSelected() {
		// Add LetterSpaces to SelectionObserver's list
		// of Observers it reports to
		int listLen = spaces.getList().size() - 1;
		for (int x = 0; x < listLen; x++) {
			if (spaces.getList().get(x).isUnderlined()) {
				selectObs.addObserver((Observer) spaces.getList().get(x));
			}
		}
		//fail("Not yet implemented");
		// Picks a random space
		//int random = ThreadLocalRandom.current().nextInt(0, spaces.getList().size() - 1);
		int random = 3; // should be a 0
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
