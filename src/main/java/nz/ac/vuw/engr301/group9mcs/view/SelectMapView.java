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
import nz.ac.vuw.engr301.group9mcs.externaldata.MapData;

/** 
 * This view displays the Map in a specified window. The user can zoom in/out of the map using a mouse scroll wheel
 * They can also move the map via dragging and if they click on a spot it will highlight that spot and ask if they 
 * would like to choose the location as the launch location.
 * 
 * @author August
 * @editor Claire
 */
public class SelectMapView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	private static final long serialVersionUID = 438543895484881L;

	private int sizeX; //Total size (in x pixels) of the map
	private int sizeY; //Total size (in y pixels) of the map
	private Image image; //Current image (current map) being displayed
	private double pixelToLat; //How many latitudes in a pixel
	private double pixelToLon; //How many longitudes in a pixel
	private int initialX; //Initial (first) x pixel of mouse drag
	private int initialY; //Initial (first) y pixel of mouse drag
	private int locationX; //x pixel of highlighted location
	private int locationY; //y pixel of highlighted location
	private int locSize; //Size of highlighted location
	private boolean locationSelected; //Has the user selected a location (launch site)
	private MapData mapData; //Used to get the map images
	private LaunchSelectedListener launchListener;
	
	private double lat = -41.291257;
	private double lon = 174.776879;
	
	/**
	 * As the viewport won't be a perfect square, this is the 'radius' for the Y axis,
	 * ie the distance from the screen's center to the top and bottom of the screen
	 */
	private double radY = 0.5;
	
	/** This sets up fields, draws the initial map and determines longitude/latitude to pixel ratios */
	public SelectMapView(MapData data) {
		mapData = data;
		locationSelected = false;
		locSize = 20;
		sizeX = this.getWidth();
		sizeY = this.getHeight();
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
		return (radY / sizeY) * sizeX;
	}
	
	protected double getUpperLat()
	{
		return LongLatHelper.latitudeNKilometersNorth(lat, this.getRadY());
	}
	
	protected double getLowerLat()
	{
		return LongLatHelper.latitudeNKilometersSouth(lat, this.getRadY());
	}
	
	protected double getLeftLon()
	{
		return LongLatHelper.longditudeNKilometersWest(lat, lon, this.getRadX());
	}
	
	protected double getRightLon()
	{
		return LongLatHelper.longditudeNKilometersEast(lat, lon, this.getRadX());
	}
	
	/** 
	 * Calculates how many longitudes/latitudes are in a pixel 
	 */
	private void pixelToLongLat() 
	{
		pixelToLon = (2 * (this.getRadX() / LongLatHelper.kilometeresPerDegreeOfLongitude(lat))) / sizeX;
		pixelToLat = (2 * (this.getRadY() / LongLatHelper.kilometersPerDegreeOfLatitude(lat))) / sizeY;
	}
	
	@Override
	public void paintComponent(@Nullable Graphics g) {
		if (g == null) { return; }
		image = mapData.get(this.getUpperLat(), this.getLeftLon(), this.getLowerLat(), this.getRightLon()); //Gets the image
		//g.drawImage(image, 0, 0, this); //Draws it
		g.drawImage(image, 0, 0, sizeX, sizeY, this);
		if (locationSelected) { //If location has been selected then still draw it
			this.getGraphics().fillOval(locationX-(locSize/2), locationY-(locSize/2), locSize, locSize);
		}		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		int currentX = arg0.getX(); 
		int currentY = arg0.getY();
		int diffX = currentX - initialX;
		int diffY = currentY - initialY;
		/* Converting pixel difference to longitude/latitude difference and adding/subtracting that from current longitude/latitude */
		lon = lon - diffX * pixelToLon;
		lat = lat + diffY * pixelToLat;
		
		repaint(); //Draw the new map
		
		/* Moving the highlighted location */
		locationX = locationX + diffX;
		locationY = locationY + diffY;
		
		initialX = currentX;
		initialY = currentY;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* Asks user if they want to select the location as their launch site in a pop-up window */
		int reply = JOptionPane.showConfirmDialog(this, "Would you like to choose this location as your launch location?"); //Asking if they would like to confirm it
		if (reply == JOptionPane.YES_OPTION) { //If they do 
			locationX = e.getX();
			locationY = e.getY();
			this.getGraphics().setColor(Color.RED); //Then location will be marked red
			this.getGraphics().fillOval(locationX-(locSize/2), locationY-(locSize/2), locSize, locSize); //Marking the location
			if (locationSelected) {
				locationSelected = false;
			}
			else {
				locationSelected = true;
			}
			launchListener.onLaunchSelected((this.getUpperLat() - (locationY * pixelToLat)), (this.getLeftLon() + (locationX * pixelToLon)));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		initialX = e.getX();
		initialY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation(); //Gets how much the wheel has rotated
		this.radY = this.radY * (1.0 + (notches * 0.1));
		this.pixelToLongLat();
		repaint(); //Repaint (show new) map
	}
	
	public void addListener(LaunchSelectedListener listener) {
		launchListener = listener;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.sizeX = this.getSize().width;
		this.sizeY = this.getSize().height;
		this.pixelToLongLat();
		this.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}

