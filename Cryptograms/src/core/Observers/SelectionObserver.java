package core.Observers;

public class SelectionObserver implements Observer {
	private char hilightedChar = '|';
	
	public SelectionObserver() {
		
	}
	
	public void setCharToHilight(char c) {
		hilightedChar = c;
	}

	@Override
	public void update() {
		System.out.println("\n\nSelection Observer: " + hilightedChar);
		
	}

	@Override
	public String getType() {
		return "selection";
	}

}
