package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	 *
	 */
	private JPanel sidePanel;

	/**
	 * Sets the View up, and saves the Observer.
	 *
	 * @param parameters
	 * @param o
	 * @param map
	 */
	public GoNoGoSelectView(Object parameters, String fileName, double lat, double lon, Observer o, MapImage map) {
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
		this.sidePanel = new JPanel();
		setupSidePanel(fileName, lat, lon);
		this.simulationResults.setName("Simulation Results");
		this.add(this.simulationResults, BorderLayout.CENTER);
		this.add(this.sidePanel, BorderLayout.WEST);

		this.repaint();
	}

	/**
	 * @param fileName
	 * @param lat
	 * @param lon
	 *
	 */
	private void setupSidePanel(String fileName, double lat, double lon) {
		this.sidePanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.fill = GridBagConstraints.BOTH;

		JLabel para = new JLabel("Parameters", SwingConstants.CENTER);
		para.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel fileNameLabel = new JLabel("Rocket File: " + fileName, SwingConstants.CENTER);
		JLabel latLabel = new JLabel("Latitude: " + lat, SwingConstants.CENTER);
		JLabel longLabel = new JLabel("Longitude: " + lon, SwingConstants.CENTER);

		JLabel percen = new JLabel("50%", SwingConstants.CENTER);
		percen.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel percen2 = new JLabel("Probability of Landing Safely", SwingConstants.CENTER);
		JLabel percen3 = new JLabel("(Not on someone's head)", SwingConstants.CENTER);
		JLabel length = new JLabel("2-20m", SwingConstants.CENTER);
		length.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel length2 = new JLabel("Predicted Landing from Launch Site", SwingConstants.CENTER);

		gbc.weighty = 1;
		this.sidePanel.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.sidePanel.add(para, gbc);
		gbc.gridy++;
		this.sidePanel.add(fileNameLabel, gbc);
		gbc.gridy++;
		this.sidePanel.add(latLabel, gbc);
		gbc.gridy++;
		this.sidePanel.add(longLabel, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.sidePanel.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.sidePanel.add(percen, gbc);
		gbc.gridy++;
		this.sidePanel.add(percen2, gbc);
		gbc.gridy++;
		this.sidePanel.add(percen3, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.sidePanel.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.sidePanel.add(length, gbc);
		gbc.gridy++;
		this.sidePanel.add(length2, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.sidePanel.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		JButton change = new JButton("Change Launch Parameters");
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String[] args = {"change parameters"};
				GoNoGoSelectView.this.obs.notify(args);
			}
		});
		this.sidePanel.add(change, gbc);
		gbc.gridy++;

		JButton save = new JButton("Save and Quit");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				JOptionPane.showMessageDialog(GoNoGoSelectView.this, "WARNING: Please double check the location before launching", "WARNING", JOptionPane.ERROR_MESSAGE);
				String args = "save and quit";
				GoNoGoSelectView.this.obs.notify(args);
			}
		});
		this.sidePanel.add(save, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.sidePanel.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;
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
			// TODO: this isn't important
		}
	}

}
