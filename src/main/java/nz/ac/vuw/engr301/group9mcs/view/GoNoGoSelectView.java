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
	 * The Panel that shows the results of the simulation.
	 */
	private JPanel simulationResults;
	/**
	 * The Panel that holds the buttons.
	 */
	private JPanel buttons;
	
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
    this.simulationResults = new JPanel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
      protected void paintComponent(@Nullable Graphics g) {
        paintResults(results, g);
      }
    };
    this.simulationResults.setName("Simulation Results");
    this.buttons = new JPanel();
    this.buttons.setName("Buttons");
    
    JButton change = new JButton("Change Launch Parameters");
    change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String[] args = {"Change View", "Launch Parameters"};
				GoNoGoSelectView.this.obs.notify(args);
			}
    });
    this.buttons.add(change);
    
    JButton save = new JButton("Save and Quit");
    save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String[] args = {"Save", "Quit"};
				GoNoGoSelectView.this.obs.notify(args);
			}
    });
    this.buttons.add(save);
    
    this.add(this.buttons, BorderLayout.NORTH);
    this.add(this.simulationResults, BorderLayout.CENTER);
    
    this.repaint();
	}
	
	/**
	 * Paints the Simulation Results Panel with the given results.
	 * 
	 * @param results
	 * @param g
	 */
	void paintResults(Object results, @Nullable Graphics g) {
		if( g == null ) return;
		g.setColor(Color.blue);
		g.fillRect(0, 0, this.simulationResults.getWidth(), this.simulationResults.getHeight());
	}
	
}
