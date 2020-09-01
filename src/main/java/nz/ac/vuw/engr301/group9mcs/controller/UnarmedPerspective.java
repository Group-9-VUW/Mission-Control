package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Perspective that holds the Panels for the when the rocket is Unarmed.
 * 
 * @author Bryony
 *
 */
public class UnarmedPerspective  extends Observable implements Perspective, Observer {

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	private JPanel panel;
	
	// GO NO GO PANEL : simulation
	// ARM BUTTON : popup to ask user "are you sure?" -> Disclaimer: You are responsible for checking the surroundings are really clear before firing. Don't trust our maps which don't know about people.
	// BIG FAT WARNING PANEL : do not launch, we know where you are
	// PANEL TO CONNECT TO ROCKET : should be first thing
	// CACHED MAP
	// INPUT FOR WEATHER : User should gather weather data in the field
	// BUTTON TO RUN SIMULATION : sends weather data, displays output
	
	/**
	 * Construct the Panel
	 */
	public UnarmedPerspective() {
		this.panel = new JPanel(new BorderLayout());
	}

	@Override
	public JPanel enable(MenuController menu) {
		return this.panel;
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "armed";
	}
	
	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Switch to the given View Panel.
	 * 
	 * @param newPanel
	 */
	private void switchTo(JPanel newPanel) {
		this.panel.removeAll();
		this.panel.add(newPanel, BorderLayout.CENTER);
		this.panel.revalidate();
		this.panel.repaint();
	}

}
