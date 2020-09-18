package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.avionics.LORAConfigPanel;
import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.externaldata.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.view.ArmedButtonPanel;
import nz.ac.vuw.engr301.group9mcs.view.GoNoGoView;
import nz.ac.vuw.engr301.group9mcs.view.LocalWeatherDialog;
import nz.ac.vuw.engr301.group9mcs.view.ViewMenuItem;
import nz.ac.vuw.engr301.group9mcs.view.WarningPanel;

/**
 * Perspective that holds the Panels for the when the rocket is Unarmed.
 * 
 * @author Bryony
 *
 */
public class UnarmedPerspective  extends Observable implements Perspective, Observer {

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
	@Nullable private GoNoGoView goNoGoView;

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

	/**
	 *  PANEL TO CONNECT TO ROCKET : should be first thing
	 */
	private JPanel rocketDetailsPanel;

	// CACHED MAP
	// TODO: What to do with this?

	/** 
	 * INPUT FOR WEATHER : User should gather weather data in the field
	 * BUTTON TO RUN SIMULATION : sends weather data, displays output
	 */
	private JPanel weatherDetailsPanel;
	
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

		// TODO: real rocket details panel
		this.rocketDetailsPanel = new JPanel() {
			/***/
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(@Nullable Graphics g) {
				g.setColor(Color.orange);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		this.rocketDetailsPanel.setPreferredSize(new Dimension(200, 300));

		// TODO: real weather details panel
		this.weatherDetailsPanel = new JPanel() {
			/***/
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(@Nullable Graphics g) {
				g.setColor(Color.blue);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		JButton run = new JButton("Run Simulation");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				switchTo(viewDetails());
			}
		});
		this.weatherDetailsPanel.add(run);
		this.weatherDetailsPanel.setPreferredSize(new Dimension(200, 300));

		switchTo(viewDetails());
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
			LocalWeatherDialog dialog = new LocalWeatherDialog(this.resources.getFrame());
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
		if(this.resources != null) {
			new LORAConfigPanel(this.resources.getFrame(), Null.nonNull(this.resources).getDriver());
		} else {
			throw new PreconditionViolationException("getWeatherDetails() shouldn't be called on an un-enabled UnarmedPerpspective");
		}
	}
	
	/**
	 * Run simulation
	 * 
	 * @param e unused
	 */
	public void runSimulation(ActionEvent e)
	{
		if(this.resources != null && this.goNoGoView != null) {
			this.goNoGoView.giveData(80.0, 125.55);
		} else {
			throw new PreconditionViolationException("getWeatherDetails() shouldn't be called on an un-enabled UnarmedPerpspective");
		}
	}

	/**
	 * Returns a Panel containing the panels for entering details.
	 * Warning Panel at the top.
	 * Rocket Details panel to the left (for connecting to the rocket).
	 * Weather Details panel to the right (for entering weather and starting simulation).
	 * 
	 * @return A Panel
	 */
	JPanel enterDetails() {
		JPanel details = new JPanel(new BorderLayout());
		details.add(this.warningPanel, BorderLayout.NORTH);
		details.add(this.rocketDetailsPanel, BorderLayout.WEST);
		details.add(this.weatherDetailsPanel, BorderLayout.CENTER);
		details.setSize(new Dimension(400, 400));
		return details;
	}

	/**
	 * Returns a Panel containing the panels for viewing details.
	 * Warning Panel at the top.
	 * GoNoGo Panel to the left (shows simulation results).
	 * Arm Button Panel to the right (button to arm rocket).
	 * @return A Panel
	 */
	JPanel viewDetails() {
		JPanel details = new JPanel(new BorderLayout());
		details.add(this.topPanel, BorderLayout.NORTH);
		// Create GoNoGoPanel now to get data from enterDetails. -> parameters (simulation), filename, coordinates, map image
		GoNoGoView go = new GoNoGoView(new Object(), "unknown.txt", 0, 0, this, new InternetMapImage(), this.name());
		go.setPreferredSize(new Dimension(300, 300));
		details.add(go, BorderLayout.CENTER);
		details.setSize(new Dimension(400, 400));
		this.goNoGoView = go;
		return details;
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if (arg instanceof String[]) {
			String[] args = (String[]) arg;
			if (args.length == 2) {
				if (args[0].equals("switch")) {
					if (args[1].equals("enterDetails")) {
						switchTo(enterDetails());
					}
				}
			} else if (args.length == 1) {
				if (args[0].equals("ARM")) {
					// TODO: Arm Rocket
					String[] newArgs = {"switch view", "armed"};
					notify(newArgs);
					switchTo(enterDetails());
				}
			}
		}
	}

	/**
	 * Switch to the given View Panel.
	 * 
	 * @param newPanel
	 */
	void switchTo(JPanel newPanel) {
		this.panel.removeAll();
		this.panel.add(newPanel, BorderLayout.CENTER);
		this.panel.revalidate();
		this.panel.repaint();
	}

	@Override
	public JPanel enable(MenuController menu, @Nullable Resources resource) {
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

}
