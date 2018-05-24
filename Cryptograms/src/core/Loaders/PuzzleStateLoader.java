package core.Loaders;

import org.json.simple.JSONObject;

import core.Data.PuzzleState;

public class PuzzleStateLoader implements Loader {
	private String target = null;
	private PuzzleState puzzleState = null;
	private JSONObject jsonObj = null;

	public PuzzleState getPuzzleState() { return puzzleState; }
	
	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
