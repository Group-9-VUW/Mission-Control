package nz.ac.vuw.engr301.group9mcs.controller.perspectives;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Condition;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.view.planning.LaunchRodDialog;
import nz.ac.vuw.engr301.group9mcs.view.planning.SelectFileView;
import nz.ac.vuw.engr301.group9mcs.view.planning.SelectSiteView;
import nz.ac.vuw.engr301.group9mcs.view.planning.SimulationPanel;

/**
 * Perspective that holds the Panels for the Selecting a Launch Site.
 *
 * @author Bryony
 *
 */
public class SelectSitePerspective extends Observable implements Perspective, Observer {

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	private JPanel panel;

	/**
	 * The View Panel for getting the filename.
	 */
	private final JPanel fileGet = new SelectFileView(this);
	
	/**
	 * The View Panel for choosing the site and time.
	 */
	private final JPanel siteMap = new SelectSiteView(this, new InternetMapImage());
	
	/**
	 * The View Panel for showing the simulation results.
	 */
	private final JPanel resultsShow = new SimulationPanel(this);
	
	/**
	 * The filename from SelectFileView.
	 */
	private String filename;
	
	/**
	 * Location of launch site, Latitude.
	 */
	private double latitude;
	
	/**
	 * Location of launch site, Longitude.
	 */
	private double longitude;
	
	/**
	 * When the rocket will be flown HH:mm
	 */
	//private Date time; TODO use this
	
	/**
	 * Launch rod data
	 */
	private @Nullable LaunchRodData launchRodData;
	
	/**
	 * Resources instance;
	 */
	private @Nullable Resources resources;

	/**
	 * Create the Perspective and construct the Panel.
	 */
	public SelectSitePerspective() {
		this.filename = "";
		this.panel = new JPanel(new BorderLayout());
		this.switchTo(this.fileGet);
	}

	@Override
	public JPanel enable(MenuController menu, @Nullable Resources resource) {
		this.resources = resource;
		this.switchTo(this.fileGet);
		menu.enableItem("Simulation/Enter Launch Rod Data");
		// TODO: does this perspective actually need resources?
		// What about passing the MapImage class?
		// Parameters from a simulation
		// Access to a simulation
		return this.panel;
	}
	

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
		menu.addMenuItem("Simulation/Enter Launch Rod Data", "Enter Launch Rod Data", this::onLaunchRodEntry);
	}

	/**
	 * For when the user clicks 'enter launch rod data'
	 * @param e 
	 */
	public void onLaunchRodEntry(@Nullable ActionEvent e)
	{
		LaunchRodDialog dialog = new LaunchRodDialog(Null.nonNull(this.resources).getFrame());
		this.launchRodData = dialog.getData();
	}

	
	@Override
	public String name() {
		return "site";
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if(arg instanceof String[])
		{
			String[] args = (String[]) Null.nonNull(arg);
			Condition.PRE.positive("args.length", args.length);

			switch(args[0])
			{
				case "rocket imported":
					this.switchTo(this.siteMap);
					this.filename = Null.nonNull(args[1]);
					return;
				case "site selected":
					this.switchTo(this.resultsShow);
					this.latitude = Double.valueOf(Null.nonNull(args[1])).doubleValue();
					this.longitude = Double.valueOf(Null.nonNull(args[2])).doubleValue();

					// should the simulation be run here?????
					return;
				case "return to rocket import":
					this.switchTo(this.fileGet);
					return;
				case "change parameters":
					this.switchTo(this.siteMap);
					return;
				case "save and quit":
					// save information - what to save????
					System.exit(0);
					return;
				default:
					throw new PreconditionViolationException("Unregonized command sent to SelectSitePerspective");
			}
		}
		throw new PreconditionViolationException("Unregonized command sent to SelectSitePerspective");
	}

	/**
	 * Switch to the indicated View Panel.
	 *
	 * @param newPanel
	 */
	private void switchTo(JPanel newPanel)
	{
		this.panel.removeAll();
		this.panel.add(newPanel, BorderLayout.CENTER);
		this.panel.revalidate();
		this.panel.repaint();
	}

	@Override
	public void releaseResources() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return The launch site
	 */
	public @Nullable Point getPosition()
	{
		return new Point(this.latitude, this.longitude);
	}
	
	/**
	 * @return The launch rod data
	 */
	public @Nullable LaunchRodData getLaunchRodData()
	{
		return this.launchRodData;
	}
	
	/**
	 * @return The root JFrame
	 */
	public JFrame owner()
	{
		return Null.nonNull(this.resources).getFrame();
	}

}
