package nz.ac.vuw.engr301.group9mcs.view.planning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.commons.PythonContext;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.NOAAException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.controller.perspectives.SelectSitePerspective;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteProcessor;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteStatistics;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSitesData;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAA;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAAWeatherData;
import nz.ac.vuw.engr301.group9mcs.montecarlo.MonteCarloBridge;
import nz.ac.vuw.engr301.group9mcs.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group9mcs.view.SimulationDialog;
import nz.ac.vuw.engr301.group9mcs.view.SimulationView;

/**
 * A panel for running and showing the results of simulations
 * 
 * @author Claire
 */
public class SimulationPanel extends JPanel implements ActionListener {
	
	/**
	 */
	private static final long serialVersionUID = -8786447041281812385L;

	/**
	 * This object's parent
	 */
	private final SelectSitePerspective owner;
	
	/**
	 * Run simulation button
	 */
	private final JButton runSimulation = new JButton("Run Simulation");
	
	/**
	 * Return button
	 */
	private final JButton goBack = new JButton("Select Different Site");
	
	/**
	 * Save button
	 */
	private final JButton save = new JButton("Save site");
	
	/**
	 * A text area where the results go
	 */
	private final JTextArea results = new JTextArea("Simulation not yet run");
	
	/**
	 * The main panel, held in reserve for when a simulation is run
	 */
	private @Nullable JPanel mainPanel;
	
	/**
	 * The side panel, held in reserve for when a simulation is run
	 */
	private final JPanel sidePanel;
	
	/**
	 * The simulation results panel
	 */
	private @Nullable SimulationView view;
	
	/**
	 * @param persp The parent perspective
	 */
	public SimulationPanel(SelectSitePerspective persp)
	{
		this.owner = persp;
		
		this.setLayout(new BorderLayout());
		
		this.mainPanel = null;
		
		this.add(this.getPlaceholderPanel(), BorderLayout.CENTER);
		this.add(this.sidePanel = this.getSidePanel(), BorderLayout.WEST);
		
		this.runSimulation.addActionListener(this);
		this.goBack.addActionListener(this);
		this.save.addActionListener(this);
	}
	
	/**
	 * @return A placeholder panel for this object
	 */
	@SuppressWarnings("static-method")
	private JPanel getPlaceholderPanel()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(new JLabel("No simulation run yet."), gbc);
		
		return panel;
	}
	
	/**
	 * @return A main panel for this object
	 */
	private JPanel getMainPanel()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		panel.add(this.view, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * @return The side panel
	 */
	private JPanel getSidePanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		
		this.results.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		this.results.setBackground(Color.LIGHT_GRAY.brighter());
		this.results.setColumns(22);
		
		panel.add(this.results, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		
		panel.add(new JPanel(), gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0.0;
		
		panel.add(this.runSimulation, gbc);
		
		gbc.gridy = 3;
		
		panel.add(this.save, gbc);
		
		gbc.gridy = 4;
		
		panel.add(this.goBack, gbc);
		
		return panel;
	}
	
	/**
	 * Initializes this panel with a position
	 */
	public void initialize()
	{
		this.view = new SimulationView(new Point[0], Null.nonNull(this.owner.getPosition()), new InternetMapImage());
		this.mainPanel = this.getMainPanel();
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) 
	{
		if(e == null) { return; }
		if(this.runSimulation == e.getSource()) {
			this.runSimulation();
		}
	}
	
	/**
	 * Swaps the panels
	 */
	private void swap()
	{
		this.removeAll();
		this.add(this.mainPanel, BorderLayout.CENTER);
		this.add(this.sidePanel, BorderLayout.WEST);
	}
	
	/**
	 * Runs a simulation
	 */
	private void runSimulation()
	{
		PythonContext.hasValidPython();
		
		Point launchSite = this.owner.getPosition();
		LaunchRodData launchRod = this.owner.getLaunchRodData();
		
		assert launchSite != null;
		
		if(launchRod == null) {
			JOptionPane.showMessageDialog(this, "Unable to run simulation without launch rod data.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int i = -1;
		
		do {
			String s = JOptionPane.showInputDialog(this, "Enter number of simulations to run:", "Run Simulation", JOptionPane.PLAIN_MESSAGE);
			if(s == null || s.length() == 0) {
				return;
			} 
			try {
				int i2 = Integer.parseInt(s);
				if(i2 > 0)
					i = i2;
			} catch(NumberFormatException e2) { /**/ }
		} while(i < 0);
		
		try {
			MonteCarloBridge bridge = new MonteCarloBridge();
			
			List<NOAAWeatherData> points;
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				points = NOAA.getWeather(launchSite.getLatitude(), launchSite.getLongitude(), 0, calendar);
			} catch(NOAAException e) {
				JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				throw e;
			}
			
			MonteCarloSimulation sim = bridge.runSimulation(launchSite, points, launchRod, i);
			
			@SuppressWarnings("unused")
			SimulationDialog dialog = new SimulationDialog(this.owner.owner(), sim);
			
			this.swap();
			
			List<Point> landings = sim.getResults();
			Null.nonNull(this.view).addPoints(Null.nonNull(landings.toArray(new Point[landings.size()])));
			List<Point> valid = new LandingSiteProcessor(landings).getValidPoints();
			LandingSitesData data = new LandingSitesData(Null.nonNull(this.owner.getPosition()), landings, valid);
			
			double validPc = LandingSiteStatistics.getPercentageValid(data);
			this.results.setText("Simulation ran. Results: \n\nSafe landing probability: " + validPc + "%\nAvg. Distance from launch site: " + LandingSiteStatistics.getAverageAllDistanceFromLaunchSite(data) + "m\n\nSaftey Assessment: " + (validPc > 90.0 ? "SAFE" : "UNSAFE"));			
			
			this.revalidate();
			this.repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
