package core.Spaces;

import java.util.ArrayList;

public class Word {
	private ArrayList<Space> word;
	
	public Word() {
		word = new ArrayList<Space>();
	}
	
	public ArrayList<Space> getWord() { return word; }
	public void addSpace(Space space) {
		word.add(space);
	}
	
	public int size() { return word.size(); }
	
	public boolean isBlankSpace() {
		boolean blank = false;
		if (word.get(0).getSpaceType() == SpaceType.BLANK) {
			blank = true;
		}
		
		return blank;
	}
	
}
