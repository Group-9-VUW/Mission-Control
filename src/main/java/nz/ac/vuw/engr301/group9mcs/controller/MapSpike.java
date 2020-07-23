/**
 *
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.DisplayHelper;
import nz.ac.vuw.engr301.group9mcs.commons.LongLatHelper;
import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapData;
import nz.ac.vuw.engr301.group9mcs.externaldata.InternetMapData;

/**
 * A runner class for the map demo. Executable.
 * <br><br>
 * Implements the main JFrame
 *
 * @author Claire
 */
public class MapSpike extends JFrame implements WindowListener, SimpleEventListener {

	private static final long serialVersionUID = 8869451586408924734L;

	protected static final int WINDOW_WIDTH = 900;
	protected static final int WINDOW_HEIGHT = 600;

	protected final InternetMapData mapdata = new InternetMapData();
	protected final SelectDemoPanel selectdemo = new SelectDemoPanel(this.mapdata, this);
	protected final ViewDemoPanel viewdemo = new ViewDemoPanel();
	protected final WeatherDemoPanel weatherdemo = new WeatherDemoPanel();

	/**
	 * @throws HeadlessException
	 */
	public MapSpike() throws HeadlessException {
		super("Map Demo");
		this.setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Select Demo", this.selectdemo);
		tabs.addTab("Launch Demo", this.viewdemo);
		tabs.addTab("Weather Demo", this.weatherdemo);
		this.add(tabs, BorderLayout.CENTER);
		this.addWindowListener(this);
		this.setSize(900, 600);
		DisplayHelper.center(this);
	}

	@Override
	public void event(String type) {
		switch(type) {
			case "save":
				double lat = this.selectdemo.getLatN();
				double lon = this.selectdemo.getLonN();
				double latUL = LongLatHelper.latitudeNKilometersNorth(lat, 2);
				double latLR = LongLatHelper.latitudeNKilometersSouth(lat, 2);
				double lonUL = LongLatHelper.longditudeNKilometersWest(lat, lon, 2);
				double lonLR = LongLatHelper.longditudeNKilometersEast(lat, lon, 2);
				System.out.println(LongLatHelper.kilometeresPerDegreeOfLongitude(lat));
				System.out.println(latUL + " down " + latLR);
				System.out.println(lonUL + " across " + lonLR);
				CachedMapData cmd = new CachedMapData(this.mapdata, latUL, lonUL, latLR, lonLR);
				this.viewdemo.setSave(cmd.getFile());
				break;
		default:
			break;
		}
	}

	@Override
	public void windowClosing(@Nullable WindowEvent e)
	{
		this.setVisible(false);
		this.dispose();
	}

	@Override
	public void windowOpened(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}
	@Override
	public void windowClosed(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}
	@Override
	public void windowIconified(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}
	@Override
	public void windowDeiconified(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}
	@Override
	public void windowActivated(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}
	@Override
	public void windowDeactivated(@Nullable WindowEvent e) {
		//The WindowListener interface requires that these methods be overridden, but they are not used.
	}

	public static final void main(String[] args) {
		new MapSpike().setVisible(true);
	}

}
