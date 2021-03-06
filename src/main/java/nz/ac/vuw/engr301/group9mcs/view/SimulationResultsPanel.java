package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.map.LongLatHelper;
import nz.ac.vuw.engr301.group9mcs.commons.map.PlanetaryArea;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.MapImage;

/**
 * Display map view is for showing the simulation results.
 * It should automatically resize to fit all sites.
 * It's purely output, the user shouldn't be able to engage with it at all.
 * There will be a MapData interface that allows you to get a map
 * image for a certain area specified in longitude and latitude
 * (I'm thinking upper-left, lower-right corner notation, as it
 * allows the caller to choose aspect ratio in only two arguments).
 *
 * @author Bryony Gatehouse
 * @editor Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class SimulationResultsPanel extends JPanel{

	/*public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		Point[] points = {new Point(-41.287567, 174.769923), new Point(-41.287900, 174.772271)};//, new Point(-41.305569,174.739941)};
		SimulationView c = new SimulationView(points, new Point(-41.290373, 174.768260), new InternetMapImage());
		frame.add(c);
		frame.setPreferredSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		Point[] newPoints = {new Point(-41.305569,174.739941)};
		c.replacePoints(newPoints);
	}*/

	/**
	 * Color of Landing Sites
	 */
	private Color land = new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 127);

	/**
	 * The drawing panel.
	 */
	private JComponent panel;

	/**
	 * The dot size.
	 */
	private int dotsize = 15;

	/**
	 * List of points returned from Simulation
	 */
	private Point[] points;

	/**
	 * Launch Site Point
	 */
	private Point launchsite;

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 3810711176953194652L;

	/**
	 * Provides the map image.
	 */
	private MapImage mapData;

	/**
	 * Set up JPanel for Displaying Map of Rocket's Progress.
	 * @param points All the possible Landing Sites provided by the simulation
	 * @param launchsite The Launch site
	 * @param mapData The MapData class, provides the map image
	 */
	public SimulationResultsPanel(Point[] points, Point launchsite, MapImage mapData) {
		this.points = points;
		this.mapData = mapData;
		this.launchsite = launchsite;

		this.setPreferredSize(new Dimension(300, 300));

		this.setLayout(new BorderLayout());

		this.panel = new JComponent() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(@Nullable Graphics g) {
				paintInsideComponent(g);
			}
		};
		//this.panel.setPreferredSize(new Dimension(300, 300));
		this.add(this.panel, BorderLayout.CENTER);
		drawOSMLicense();
		this.repaint();
	}

	/**
	 * Draws the OSM License on the Panel.
	 */
	private void drawOSMLicense() {
		String start = "© ";
		String end = " Contributors";
		JLabel hyperlink = new JLabel(start + "OpenStreetMap" + end);
		hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hyperlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(@Nullable MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.openstreetmap.org/copyright"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
			@Override public void mouseEntered(@Nullable MouseEvent e) {/**/}
			@Override public void mouseExited(@Nullable MouseEvent e) {/**/}
		});

		SpringLayout springLayout = new SpringLayout();
		this.panel.setLayout(springLayout);

		springLayout.putConstraint(SpringLayout.EAST, hyperlink, 0, SpringLayout.EAST, this.panel);
		springLayout.putConstraint(SpringLayout.SOUTH, hyperlink, 0, SpringLayout.SOUTH, this.panel);
		this.panel.add(hyperlink);
	}

	/**
	 * Paint the Component inside the Panel.
	 *
	 * @param g Graphics drawer
	 */
	public void paintInsideComponent(@Nullable Graphics g) {
		if (g == null) {
			return;
		}

		double latDiff = this.largestLat() - this.smallestLat();
		double longDiff = this.largestLong() - this.smallestLong();

		double radLat, radLon;

		if(latDiff > longDiff) {
			// Gets the size of the screen (lat)
			// by the latDiff ( * 1.2 so it's not on the edges)
			// OR
			// 500 meters (if larger)
			radLat = Math.max(0.5 / LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude()), latDiff * 1.2);
			radLon = (radLat * LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude())) / LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude());
		} else {
			radLon = Math.max(0.5 / LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude()), longDiff * 1.2);
			radLat = (radLon * LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude())) / LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude());
		}

		int width = this.panel.getSize().width;
		int height = this.panel.getSize().height;

		if(width > height) {
			//If width is greater than height, expand longditude such that it's proportional
			radLon *= width;
			radLon /= height;
		} else {
			//Otherwise, do the opposite
			radLat *= height;
			radLat /= width;
		}

		// work out the Upper Left and Lower Right corners
		double latUL = this.launchsite.getLatitude() + radLat;
		double lonUL = this.launchsite.getLongitude() - radLon;
		double latLR = this.launchsite.getLatitude() - radLat;
		double lonLR = this.launchsite.getLongitude() + radLon;

		double xRatio = (radLon * 2) / width;
		double yRatio = (radLat * 2) / height;

		int xlaunch = (int) ((this.launchsite.getLongitude() - lonUL) / xRatio);
		int ylaunch = (int) ((latUL - this.launchsite.getLatitude()) / yRatio);

		Image image = this.mapData.get(latUL, lonUL, latLR, lonLR);
		g.drawImage(image, 0, 0, width, height, null);

		// draw all points on the map
		for(Point p : this.points) {
			int x = (int) ((p.getLongitude() - lonUL) / xRatio);
			int y = (int) ((latUL - p.getLatitude()) / yRatio);
			drawRocket(x, y, g);
		}

		// draw the launch site on the map
		drawLaunchSite(width - xlaunch, height - ylaunch, g);
	}

	/**
	 * Combine the list of points with the launchsite point
	 *
	 * @return A slightly longer array
	 */
	private Point[] pointsPlus() {
		Point[] pointsPlus = Arrays.copyOf(this.points, this.points.length + 1);
		pointsPlus[this.points.length] = this.launchsite;
		return pointsPlus;
	}

	/**
	 * @return The largest Latitude to be displayed on screen
	 */
	private double largestLat() {
		double maxLat = -90;
		Point[] points1 = pointsPlus();
		for(Point p : points1) {
			if(p.getLatitude() > maxLat) {
				maxLat = p.getLatitude();
			}
		}
		return maxLat;
	}

	/**
	 * @return The largest Longitude to be displayed on screen
	 */
	private double largestLong() {
		double maxLon = 0;
		Point[] points1 = pointsPlus();
		for(Point p : points1) {
			if(p.getLongitude() > maxLon) {
				maxLon = p.getLongitude();
			}
		}
		return maxLon;
	}

	/**
	 * @return The smallest Latitude to be displayed on screen
	 */
	private double smallestLat() {
		double minLat = 90;
		Point[] points1 = pointsPlus();
		for(Point p : points1) {
			if(p.getLatitude() < minLat) {
				minLat = p.getLatitude();
			}
		}
		return minLat;
	}

	/**
	 * @return The smallest Longitude to be displayed on screen
	 */
	private double smallestLong() {
		double minLon = 180;
		Point[] points1 = pointsPlus();
		for(Point p : points1) {
			if(p.getLongitude() < minLon) {
				minLon = p.getLongitude();
			}
		}
		return minLon;
	}

	/**
	 * Draw the launch site at a given point on the screen.
	 *
	 * @param xlocation X coordinate of the site location
	 * @param ylocation Y coordinate of the site location
	 * @param g The Graphics drawer
	 */
	private void drawLaunchSite(int xlocation, int ylocation, Graphics g) {
		g.setColor(Color.red);
		int size = this.dotsize;
		g.fillOval(xlocation - size / 2, ylocation - size / 2, size, size);
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(1.5f));
		g2.drawOval(xlocation - size / 2, ylocation - size / 2, size, size);
	}

	/**
	 * Draw a rocket in the direction given at a point on the screen.
	 * The direction could be 1 (NorthEast), 2 (NorthWest), 3 (SouthEast), 4 (SouthWest)
	 *
	 * @param xlocation X coordinate of the rocket Location
	 * @param ylocation Y coordinate of the rocket Location
	 * @param g The graphic drawer
	 */
	private void drawRocket(int xlocation, int ylocation, Graphics g) {
		g.setColor(this.land);
		int size = this.dotsize;
		g.fillOval(xlocation - size / 2, ylocation - size / 2, size, size);
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(1.5f));
		g2.drawOval(xlocation - size / 2, ylocation - size / 2, size, size);
	}

	/**
	 * Center the coordinates below the site images on a white rectangle.
	 *
	 * @param text The text to display (coordinates of site)
	 * @param g The Graphics drawer
	 * @param x The x position of the site image
	 * @param y The y position of the site image
	 * @param width The site image's width
	 * @param height The site image's height
	 */
	public void centerText(String text, Graphics2D g, int x, int y, int width, int height) {
		g.setFont(new Font("Serif", Font.PLAIN, this.panel.getSize().height / 30));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		Rectangle2D rect = fm.getStringBounds(text, g);
		int xstring = (int)(x - (rect.getWidth() / 2) + (width / 2));
		int ystring = (int)(y + height + rect.getHeight());
		g.setColor(Color.white);
		g.fillRect(xstring - (int)rect.getWidth() / text.length() / 2, ystring - fm.getAscent(),
				(int)rect.getWidth(), (int)rect.getHeight());
		g.setColor(Color.black);
		g.drawString(text, xstring, ystring);
	}

	/**
	 * @param newPoints Replace list of points with new list.
	 */
	public void replacePoints(Point[] newPoints) {
		this.points = newPoints;
		this.repaint();
	}

	/**
	 * @param newPoints Add list of points to current list of points.
	 */
	public void addPoints(Point[] newPoints) {
		Point[] temp = new Point[this.points.length + newPoints.length];
		System.arraycopy(this.points, 0, temp, 0, this.points.length);
		System.arraycopy(newPoints, 0, temp, this.points.length, newPoints.length);
		this.points = temp;
		this.repaint();
	}

	/**
	 * @return An area large enough to display all the likely launches
	 */
	public PlanetaryArea getNecessaryArea()
	{
		double latDiff = this.largestLat() - this.smallestLat();
		double longDiff = this.largestLong() - this.smallestLong();

		double radLat, radLon;

		if(latDiff > longDiff) {
			radLat = Math.max(0.5 / LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude()), latDiff * 1.2);
			radLon = (radLat * LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude())) / LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude());
		} else {
			radLon = Math.max(0.5 / LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude()), longDiff * 1.2);
			radLat = (radLon * LongLatHelper.kilometeresPerDegreeOfLongitude(this.launchsite.getLatitude())) / LongLatHelper.kilometersPerDegreeOfLatitude(this.launchsite.getLatitude());
		}

		int width = this.panel.getSize().width;
		int height = this.panel.getSize().height;

		if(width > height) {
			//If width is greater than height, expand longditude such that it's proportional
			radLon *= width;
			radLon /= height;
		} else {
			//Otherwise, do the opposite
			radLat *= height;
			radLat /= width;
		}

		// work out the Upper Left and Lower Right corners
		double latUL = this.launchsite.getLatitude() + radLat;
		double lonUL = this.launchsite.getLongitude() - radLon;
		double latLR = this.launchsite.getLatitude() - radLat;
		double lonLR = this.launchsite.getLongitude() + radLon;
		return PlanetaryArea.fromCorners(latUL, lonUL, latLR, lonLR).scale(1.5);
	}

	/**
	 * @return the launchsite
	 */
	public Point getLaunchsite()
	{
		return this.launchsite;
	}

}
