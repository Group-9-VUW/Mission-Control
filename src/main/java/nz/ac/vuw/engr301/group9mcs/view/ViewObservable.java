package nz.ac.vuw.engr301.group9mcs.view;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple Observable for View Classes to communicate backwards with.
 * 
 * @author Bryony
 *
 */
public class ViewObservable extends Observable{

	/**
	 * Saves the given Observer.
	 * 
	 * @param o
	 */
	public ViewObservable(Observer o) {
		this.addObserver(o);
	}
	
	/**
	 * Sends the given Object to the Observers.
	 * 
	 * @param o
	 */
	public void notify(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}
	
}
