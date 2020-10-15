package nz.ac.vuw.engr301.group9mcs.view.launch.unarmed;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.MapImage;
import nz.ac.vuw.engr301.group9mcs.view.SimulationResultsPanel;
import nz.ac.vuw.engr301.group9mcs.view.ViewObservable;

/**
 * Class for the Select Launch Perspective.
 * Shows the Data returned from the Simulation about the speculated launch.
 *
 * @author Bryony
 *
 */
public class GoNoGoView extends JPanel implements Observer{

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
	private SimulationResultsPanel simulationResults;
	
	/**
	 *
	 */
	private GoNoGoSidePanelAtSite sidePanel;

	/**
	 * Sets the View up, and saves the Observer. Uses the data from previous views.
	 * 
	 * @param resources 
	 * @param parameters From the Simulation (should be run before this panel?)
	 * @param fileName Of the Rocket file
	 * @param lat Of the Launch site
	 * @param lon Of the Launch site
	 * @param o Parent
	 * @param map To display on
	 * @param sidePanel Name of Parent (site or unarmed)
	 */
	public GoNoGoView(Resources resources, Object parameters, String fileName, double lat, double lon, Observer o, MapImage map, String sidePanel) {
		this.obs = new ViewObservable(o);

		this.setPreferredSize(new Dimension(200, 300));
		this.setLayout(new BorderLayout());
		this.simulationResults = new SimulationResultsPanel(new Point[0], new Point(lat, lon), resources.getSavedLaunch().getImage());
//		if (sidePanel.equals("site")) {
//			this.sidePanel = new GoNoGoSidePanelSelectSite(fileName, lat, lon, this);
//		} else {
//			this.sidePanel = new GoNoGoSidePanelAtSite(this);
//		}
		this.sidePanel = new GoNoGoSidePanelAtSite(this);
		this.sidePanel.setMinimumSize(new Dimension(200, 300));
		this.sidePanel.setPreferredSize(new Dimension(200, 300));
		this.simulationResults.setName("Simulation Results");
		
		this.add(this.simulationResults, BorderLayout.CENTER);
		this.add(this.sidePanel, BorderLayout.WEST);

		this.repaint();
	}

	/**
	 * Paints the Simulation Results Panel with the given results.
	 *
	 * @param parameters
	 * @param g
	 * @param map
	 */
	public void paintResults(Object parameters, MapImage map, @Nullable Graphics g) {
		if( g == null ) return;
		int width = this.getWidth() / 3;
		if (width <= 200) {
			this.sidePanel.setPreferredSize(new Dimension(width, this.getHeight()));
		} else {
			this.sidePanel.setPreferredSize(new Dimension(200, this.getHeight()));
		}
		// Pass parameters (file name, location, weather) to Simulation
		// Get results from Simulation
		// Find largest lat, long -> latLR, lonLR
		// Find smallest lat, long -> latUL, lonUL
		// Image image = this.mapData.get(latUL, lonUL, latLR, lonLR);
		// g.drawImage(image, 0, 0, width, height, null);
		// for all points, draw as small point => line from point to launchsite?
		// diff colors for safety?
		try {
		    File pathToFile = new File("./src/main/resources/view/MapImageOpenStreetMap.png");
		    Image img = ImageIO.read(pathToFile);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException ex) {
			System.out.println("Failed to load spike image. " + ex.getMessage());
		}
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if (arg instanceof String[]) {
			String[] args = (String[])arg;
			if (args[0].equals("save and quit")) {
				JOptionPane.showMessageDialog(this, "WARNING: Please double check the location before launching", "WARNING", JOptionPane.ERROR_MESSAGE);
			}
			this.obs.notify(args);
		}
	}

	/**
	 * Informs this view that a simulation has been run so that it can display the results.
	 * 
	 * @param nSafeProbability The probability that we'll land safely
	 * @param nPredictedDist The estimated average distance from the launch site
	 */
	public void giveData(double nSafeProbability, double nPredictedDist)
	{
		this.sidePanel.giveData(nSafeProbability, nPredictedDist);
	}
	
	/**
	 * Adds points to this panel for display
	 * 
	 * @param points
	 */
	public void givePoints(Point[] points)
	{
		this.simulationResults.replacePoints(points);
	}
}
