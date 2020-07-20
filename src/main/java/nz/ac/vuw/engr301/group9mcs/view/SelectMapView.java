package nz.ac.vuw.engr301.group9mcs.view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LongLatHelper;
import nz.ac.vuw.engr301.group9mcs.controller.SelectDemoPanel;
import nz.ac.vuw.engr301.group9mcs.externaldata.MapData;

/**
 * This view displays the Map in a specified window. The user can zoom in/out of the map using a mouse scroll wheel
 * They can also move the map via dragging and if they click on a spot it will highlight that spot and ask if they
 * would like to choose the location as the launch location.
 *
 * @author August
 * @editor Claire
 * @formatter Joshua
 */
public class SelectMapView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	private static final long serialVersionUID = 438543895484881L;

	private int sizeX; //Total size (in x pixels) of the map
	private int sizeY; //Total size (in y pixels) of the map
	private @Nullable Image image; //Current image (current map) being displayed
	private double pixelToLat; //How many latitudes in a pixel
	private double pixelToLon; //How many longitudes in a pixel
	private int initialX; //Initial (first) x pixel of mouse drag
	private int initialY; //Initial (first) y pixel of mouse drag
	private int locationX; //x pixel of highlighted location
	private int locationY; //y pixel of highlighted location
	private int locSize; //Size of highlighted location
	private boolean locationSelected; //Has the user selected a location (launch site)
	private MapData mapData; //Used to get the map images
	private @Nullable LaunchSelectedListener launchListener;

	private double lat = -41.291257;
	private double lon = 174.776879;

	/**
	 * As the viewport won't be a perfect square, this is the 'radius' for the Y axis,
	 * i.e. the distance from the screen's center to the top and bottom of the screen.
	 */
	private double radY = 0.5;

	/**
	 * This sets up fields, draws the initial map and determines longitude/latitude to pixel ratios
	 */
	public SelectMapView(final MapData data) {
		this.mapData = data;
		this.locationSelected = false;
		this.locSize = 20;
		this.sizeX = this.getWidth();
		this.sizeY = this.getHeight();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
	}

	protected double getRadY()
	{
		return this.radY;
	}

	protected double getRadX()
	{
		return (this.radY / this.sizeY) * this.sizeX;
	}

	protected double getUpperLat()
	{
		return LongLatHelper.latitudeNKilometersNorth(this.lat, this.getRadY());
	}

	protected double getLowerLat()
	{
		return LongLatHelper.latitudeNKilometersSouth(this.lat, this.getRadY());
	}

	protected double getLeftLon()
	{
		return LongLatHelper.longditudeNKilometersWest(this.lat, this.lon, this.getRadX());
	}

	protected double getRightLon()
	{
		return LongLatHelper.longditudeNKilometersEast(this.lat, this.lon, this.getRadX());
	}

	/**
	 * Calculates how many longitudes/latitudes are in a pixel
	 */
	private void pixelToLongLat()
	{
		this.pixelToLon = (2 * (this.getRadX() / LongLatHelper.kilometeresPerDegreeOfLongitude(this.lat))) / this.sizeX;
		this.pixelToLat = (2 * (this.getRadY() / LongLatHelper.kilometersPerDegreeOfLatitude(this.lat))) / this.sizeY;
	}

	@Override
	public void paintComponent(@Nullable Graphics g) {
		if (g == null) { return; }
		this.image = this.mapData.get(this.getUpperLat(), this.getLeftLon(), this.getLowerLat(), this.getRightLon()); //Gets the image
		//g.drawImage(image, 0, 0, this); //Draws it
		g.drawImage(this.image, 0, 0, this.sizeX, this.sizeY, this);
		if (this.locationSelected) { //If location has been selected then still draw it
			this.getGraphics().fillOval(this.locationX-(this.locSize/2), this.locationY-(this.locSize/2), this.locSize, this.locSize);
		}
	}

	@Override
	public void mouseDragged(@Nullable MouseEvent arg0) {
		if (arg0 == null) {return;}
		int currentX = arg0.getX();
		int currentY = arg0.getY();
		int diffX = currentX - this.initialX;
		int diffY = currentY - this.initialY;
		/* Converting pixel difference to longitude/latitude difference and adding/subtracting that from current longitude/latitude */
		this.lon = this.lon - diffX * this.pixelToLon;
		this.lat = this.lat + diffY * this.pixelToLat;

		repaint(); //Draw the new map

		/* Moving the highlighted location */
		this.locationX = this.locationX + diffX;
		this.locationY = this.locationY + diffY;

		this.initialX = currentX;
		this.initialY = currentY;
	}

	@Override
	public void mouseMoved(@Nullable MouseEvent arg0) {
		//This method must be overridden, but is not used.
	}

	@Override
	public void mouseClicked(@Nullable MouseEvent e) {
		/* Asks user if they want to select the location as their launch site in a pop-up window */
		if (e == null || this.launchListener == null) {return;}
		int reply = JOptionPane.showConfirmDialog(this, "Would you like to choose this location as your launch location?"); //Asking if they would like to confirm it
		if (reply == JOptionPane.YES_OPTION) { //If they do
			this.locationX = e.getX();
			this.locationY = e.getY();
			this.getGraphics().setColor(Color.RED); //Then location will be marked red
			this.getGraphics().fillOval(this.locationX-(this.locSize/2), this.locationY-(this.locSize/2), this.locSize, this.locSize); //Marking the location
			this.locationSelected = !this.locationSelected;
			this.launchListener.onLaunchSelected((this.getUpperLat() - (this.locationY * this.pixelToLat)), (this.getLeftLon() + (this.locationX * this.pixelToLon)));
		}
	}

	@Override
	public void mouseEntered(@Nullable MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(@Nullable MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(@Nullable MouseEvent e) {
		//TODO add condition for only certain mouse press events
		if (e == null) {return;}
		this.initialX = e.getX();
		this.initialY = e.getY();
	}

	@Override
	public void mouseReleased(@Nullable MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(@Nullable MouseWheelEvent arg0) {
		if (arg0 == null) {return;}
		int notches = arg0.getWheelRotation(); //Gets how much the wheel has rotated
		this.radY = this.radY * (1.0 + (notches * 0.1));
		this.pixelToLongLat();
		repaint(); //Repaint (show new) map
	}

	public void addListener(LaunchSelectedListener listener) {
		this.launchListener = listener;
	}

	@Override
	public void componentResized(@Nullable ComponentEvent e) {
		this.sizeX = this.getSize().width;
		this.sizeY = this.getSize().height;
		this.pixelToLongLat();
		this.repaint();
	}

	@Override
	public void componentMoved(@Nullable ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(@Nullable ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentHidden(@Nullable ComponentEvent e) {
		// TODO Auto-generated method stub
	}
}

