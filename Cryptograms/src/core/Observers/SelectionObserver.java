package core.Observers;

import java.util.ArrayList;

import core.Spaces.LetterSpace;

public class SelectionObserver implements Observer, Subject {
	private char hilightedChar = '|';
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private LetterSpace currentLetterSpace = null;
	
	public SelectionObserver() {
		
	}
	
	// FOR OBSERVER
	@Override
	public void update() {
		notifyObserver();
		System.out.println("\n\nSelection Observer: " + hilightedChar);
		for (Observer obs : observers) {
			obs.update();
		}
	}

	@Override
	public String getType() {
		return "selection";
	}
	

	public void setCharToHilight(char c) {
		hilightedChar = c;
	}
	
	
	// FOR SUBJECT
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
		System.out.println("\n\nSelectionObserver.notifyObserver:");
		for (Observer obs : observers) {
			try {
				currentLetterSpace = (LetterSpace) obs;
				if (currentLetterSpace.getDisplayChar() == hilightedChar) {
					currentLetterSpace.setHilight(true);
					
				}
			} catch (ClassCastException cce) {
				System.out.println("\n\nSelectionObserver - ClassCastException:\n" + cce);
			}
		}
	}

}
