package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Condition;
import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.externaldata.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.view.GoNoGoSelectView;
import nz.ac.vuw.engr301.group9mcs.view.SelectFileView;
import nz.ac.vuw.engr301.group9mcs.view.SelectSiteView;

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
	private JPanel resultsShow;

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
	private Date time;

	/**
	 * Create the Perspective and construct the Panel.
	 */
	public SelectSitePerspective() {
		this.filename = "";
		this.panel = new JPanel(new BorderLayout());
		this.switchTo(this.fileGet);
	}

	@Override
	public JPanel enable(MenuController menu) {
		this.switchTo(this.fileGet);
		return this.panel;
	}


	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "select";
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
	private void switchTo(JPanel newPanel) {
		this.panel.removeAll();
		if (newPanel == this.resultsShow) {
			// GoNoGo needs dynamic data passed into its constructor
			this.resultsShow = new GoNoGoSelectView(new Object(),this.filename, this.latitude, this.longitude, this, new InternetMapImage());
			this.panel.add(this.resultsShow, BorderLayout.CENTER);
		} else {
			this.panel.add(newPanel, BorderLayout.CENTER);
		}
		this.panel.revalidate();
		this.panel.repaint();
	}

}














