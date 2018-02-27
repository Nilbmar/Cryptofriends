package core.Spaces;

import java.util.ArrayList;

import core.Observers.Observer;
import core.Observers.SelectionObserver;
import core.Observers.Subject;

public class LetterSpace extends Space implements Subject, Observer {
	private char correctChar;
	private char currentChar;
	private boolean filled = false;
	private ArrayList<Observer> observers;
	private boolean hilight = false;

	public LetterSpace(char display, char correct) {
		super(display);
		setUnderlined(true);
		this.correctChar = correct;
		observers = new ArrayList<Observer>();
	}
	
	@Override 
	protected void setSpaceType() {
		spaceType = SpaceType.LETTER;
	}

	@Override
	public void setDisplayChar(char c) { displayChar = c; }

	public char getCorrectChar() { return correctChar; }
	public boolean isFilled() { return filled; }
	
	public char getCurrentChar() { return currentChar; }
	public void setCurrentChar(char letter) { 
		currentChar = letter; 
		filled = true;
	}
	
	public boolean isCorrect() { 
		boolean correct = false;
		if (filled) {
			if (currentChar == correctChar) {
				correct = true;
			}
		}
		return correct; 
	}

	public boolean getHilight() { return hilight; }
	public void setHilight(boolean hilight) {
		this.hilight = hilight;
	}

	// FOR SUBJECT
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
		((SelectionObserver) observer).addObserver(this);
	}



	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver() {
		for (Observer obs : observers) {
			if (obs.getType().contains("selection")) {
				((SelectionObserver) obs).setCharToHilight(getDisplayChar());

				System.out.println("Letterspace notifyObserver");
			}
			obs.update();
		}
	}

	// FOR OBSERVER
	@Override
	public void update() {
		if (hilight) {
			/*
			System.out.println("ID: " + getID() 
				+ " - Correct: " + getCorrectChar()
				+ " - Hilight: " + hilight);
			*/
		}
	}


	@Override
	public String getType() {
		return "letter";
	}
}
