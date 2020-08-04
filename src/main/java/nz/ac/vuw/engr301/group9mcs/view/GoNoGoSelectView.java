package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Class for the Select Launch Perspective.
 * Shows the Data returned from the Simulation about the speculated launch.
 * 
 * @author Bryony
 *
 */
public class GoNoGoSelectView extends JPanel{

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 2716513907535564578L;
	/**
	 * The Observable that speaks to Parent.
	 */
	ViewObservable obs;
	
	/**
	 * Sets the View up, and saves the Observer.
	 * 
	 * @param results 
	 * @param o
	 */
	public GoNoGoSelectView(Object results, Observer o) {
		this.obs = new ViewObservable(o);
		
		this.setPreferredSize(new Dimension(300, 300));
    this.setLayout(new BorderLayout());
    
    this.repaint();
	}
	
}
