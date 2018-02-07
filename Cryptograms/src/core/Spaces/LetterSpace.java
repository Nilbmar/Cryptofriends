package core.Spaces;

import java.util.ArrayList;

import core.Observers.Observer;
import core.Observers.SelectionObserver;
import core.Observers.Subject;

public class LetterSpace extends Space implements Subject {
	private char correctChar;
	private char currentChar;
	private boolean filled = false;
	private ArrayList<Observer> observers;

	public LetterSpace(char display, char correct) {
		super(display);
		setUnderlined(true);
		this.correctChar = correct;
		observers = new ArrayList<Observer>();
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



	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
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
			}
			
			obs.update();
		}
	}
}
