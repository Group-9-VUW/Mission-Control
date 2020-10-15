package nz.ac.vuw.engr301.group9mcs.controller.perspectives;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.avionics.LORAConfigPanel;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;
import nz.ac.vuw.engr301.group9mcs.controller.SavedLaunch;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteProcessor;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSiteStatistics;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.LandingSitesData;
import nz.ac.vuw.engr301.group9mcs.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group9mcs.view.SimulationDialog;
import nz.ac.vuw.engr301.group9mcs.view.ViewMenuItem;
import nz.ac.vuw.engr301.group9mcs.view.launch.ArmedButtonPanel;
import nz.ac.vuw.engr301.group9mcs.view.launch.WarningPanel;
import nz.ac.vuw.engr301.group9mcs.view.launch.unarmed.GoNoGoView;
import nz.ac.vuw.engr301.group9mcs.view.launch.unarmed.LocalWeatherDialog;

/**
 * Perspective that holds the Panels for the when the rocket is Unarmed.
 *
 * @author Bryony
 * @editor Claire
 */
public class UnarmedPerspective extends Observable implements Perspective, Observer {

	/**
	 * Menu Items to be added and enabled in the Main Menu
	 */
	private HashSet<ViewMenuItem> menuItems;

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	private JPanel panel;

	/**
	 * The go no go panel
	 */
	private GoNoGoView goNoGoView;

	/**
	 * Holds the Warning Panel and Arm Button.
	 */
	private JPanel topPanel;

	/**
	 * ARM BUTTON : pop-up to ask user "are you sure?"
	 * -> Disclaimer: You are responsible for checking the surroundings are really clear before firing.
	 * Don't trust our maps which don't know about people.
	 */
	private JPanel armButton;

	/**
	 * BIG FAT WARNING PANEL : do not launch, we know where you are
	 */
	private JPanel warningPanel;

	// CACHED MAP
	// TODO: What to do with this?

	/**
	 * The resources for this perspective
	 */
	@Nullable private Resources resources;

	/**
	 * Construct the Panel
	 */
	@SuppressWarnings("null")
	public UnarmedPerspective() {
		this.menuItems = new HashSet<>();
		this.menuItems.add(new ViewMenuItem("Simulation/Get Local Data", "Get Local Data", this::getWeatherDetails));
		this.menuItems.add(new ViewMenuItem("Simulation/Run Simuation", "Run Simulation", this::runSimulation));
		this.menuItems.add(new ViewMenuItem("Avionics/Connect to Rocket", "Connect to Rocket", this::configureRocket));

		this.panel = new JPanel(new BorderLayout());

		this.topPanel = new JPanel(new BorderLayout());

		String[] args = {"Do not Launch until Armed", "The Rocket is Still Dangerous"};
		this.warningPanel = new WarningPanel(args);
		this.warningPanel.setPreferredSize(new Dimension(200, 100));

		this.armButton = new ArmedButtonPanel(this, "ARM");
		this.armButton.setPreferredSize(new Dimension(100, 100));

		this.topPanel.add(this.warningPanel, BorderLayout.CENTER);
		this.topPanel.add(this.armButton, BorderLayout.EAST);

		this.viewDetails();
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
		for(ViewMenuItem i : this.menuItems) {
			menu.addMenuItem(i.getPath(), i.getName(), i.getListener());
		}
	}

	@Override
	public String name() {
		return "unarmed";
	}

	/**
	 * Gets the local weather data from the user
	 *
	 * @param e unused
	 */
	public void getWeatherDetails(ActionEvent e)
	{
		if(this.resources != null) {
			LocalWeatherDialog dialog = new LocalWeatherDialog(Null.nonNull(this.resources).getFrame());
			System.out.println(dialog.getData());
		} else {
			throw new PreconditionViolationException("getWeatherDetails() shouldn't be called on an un-enabled UnarmedPerpspective");
		}
	}

	/**
	 * Configures the rocket to use a serial COM port
	 *
	 * @param e unused
	 */
	@SuppressWarnings("unused")
	public void configureRocket(ActionEvent e)
	{
		new LORAConfigPanel(Null.nonNull(this.resources).getFrame(), Null.nonNull(this.resources).getDriver());
	}

	/**
	 * Run simulation
	 *
	 * @param e unused
	 */
	public void runSimulation(ActionEvent e)
	{
		if(this.resources != null) {
			int i = -1;
			do {
				String s = JOptionPane.showInputDialog(Null.nonNull(this.resources).getFrame(), "Enter number of simulations to run:", "Run Simulation", JOptionPane.PLAIN_MESSAGE);
				if(s == null || s.length() == 0) {
					return;
				} 
				try {
					int i2 = Integer.parseInt(s);
					if(i2 > 0)
						i = i2;
				} catch(NumberFormatException e2) { /**/ }
			} while(i < 0);
			
			SavedLaunch sl = Null.nonNull(this.resources).getSavedLaunch();
			
			try {
				MonteCarloSimulation sim = Null.nonNull(this.resources).getBridge().runSimulation(sl.getLaunchSite(), sl.getData(), sl.getRod(), i);
				
				@SuppressWarnings("unused")
				SimulationDialog dialog = new SimulationDialog(Null.nonNull(this.resources).getFrame(), sim);
				
				List<Point> landings = sim.getResults();
				//Null.nonNull(this.view).addPoints(Null.nonNull(landings.toArray(new Point[landings.size()])));
				List<Point> valid = new LandingSiteProcessor(landings).getValidPoints();
				LandingSitesData data = new LandingSitesData(sl.getLaunchSite(), landings, valid);
				
				double validPc = LandingSiteStatistics.getPercentageValid(data);

				this.goNoGoView.giveData(validPc, LandingSiteStatistics.getAverageAllDistanceFromLaunchSite(data));
				
				Null.nonNull(this.resources).getFrame().revalidate();
				Null.nonNull(this.resources).getFrame().repaint();
			} catch(IOException e2) {
				JOptionPane.showMessageDialog(Null.nonNull(this.resources).getFrame(), e2.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			throw new PreconditionViolationException("getWeatherDetails() shouldn't be called on an un-enabled UnarmedPerpspective");
		}
	}

	/**
	 * Returns a Panel containing the panels for viewing details.
	 * Warning Panel at the top.
	 * GoNoGo Panel to the left (shows simulation results).
	 * Arm Button Panel to the right (button to arm rocket).
	 */
	void viewDetails() {
		JPanel details = new JPanel(new BorderLayout());
		details.add(this.topPanel, BorderLayout.NORTH);
		// Create GoNoGoPanel now to get data from enterDetails. -> parameters (simulation), filename, coordinates, map image
		GoNoGoView go = new GoNoGoView(new Object(), "unknown.txt", 0, 0, this, new InternetMapImage(), this.name());
		go.setPreferredSize(new Dimension(300, 300));
		details.add(go, BorderLayout.CENTER);
		details.setSize(new Dimension(400, 400));
		this.goNoGoView = go;
		this.panel.add(details, BorderLayout.CENTER);
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if (arg instanceof String[]) {
			String[] args = (String[]) arg;
			if(args.length == 1) {
				if(args[0].equals("ARM")) {
					// TODO: Arm Rocket
					String[] newArgs = {"switch view", "armed"};
					notify(newArgs);
				}
			}
		}
	}

	@Override
	public JPanel enable(MenuController menu, Resources resource) {
		try {
			if(!resource.hasSavedLaunch()) throw new PreconditionViolationException("No saved launch to run");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(resource.getFrame(), e.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
			throw new PreconditionViolationException(e);
		}
		
		this.resources = resource;
		String[] a = new String[this.menuItems.size()];
		int i = 0;
		for(ViewMenuItem v : this.menuItems) {
			a[i] = v.getPath();
			i++;
		}
		menu.enableItems(a);
		
		return this.panel;
	}

	/**
	 * Notifies the Observer that there is an Object they can view.
	 * Can be passed any type of Object.
	 *
	 * @param o
	 */
	private void notify(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}

	@Override
	public void releaseResources() {
		// TODO Auto-generated method stub

	}

}
