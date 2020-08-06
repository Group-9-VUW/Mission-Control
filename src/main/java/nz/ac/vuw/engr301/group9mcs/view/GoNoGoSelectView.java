package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.externaldata.MapImage;

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
	 * @param parameters
	 * @param o
	 * @param map
	 */
	public GoNoGoSelectView(Object parameters, Observer o, MapImage map) {
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
				paintResults(parameters, map, g);
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
	 * @param parameters
	 * @param g
	 * @param map
	 */
	void paintResults(Object parameters, MapImage map, @Nullable Graphics g) {
		if( g == null ) return;
		// Pass parameters (file name, location, weather) to Simulation
		// Get results from Simulation
		// Find largest lat, long -> latLR, lonLR
		// Find smallest lat, long -> latUL, lonUL
		// Image image = this.mapData.get(latUL, lonUL, latLR, lonLR);
		// g.drawImage(image, 0, 0, width, height, null);
		// for all points, draw as small point => line from point to launchsite?
		// diff colors for safety?
		g.setColor(Color.blue);
		g.fillRect(0, 0, this.simulationResults.getWidth(), this.simulationResults.getHeight());
	}

}
