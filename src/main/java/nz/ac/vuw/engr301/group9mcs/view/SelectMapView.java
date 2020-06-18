package nz.ac.vuw.engr301.group9mcs.view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.externaldata.MapData;

/** This view displays the Map in a specified window. The user can zoom in/out of the map using a mouse scroll wheel
 * They can also move the map via dragging and if they click on a spot it will highlight that spot and ask if they 
 * would like to choose the location as the launch location.
 * 
 * @author August
 *
 */

public class SelectMapView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 438543895484881L;
	private double longUL; //Longitude of top left corner of the map
	private double latUL; //Latitude of top left corner of the map
	private double longBR; //Longitude of bottom right corner of the map
	private double latBR; //Latitude of bottom right corner of the map
	private double zoom; //Zoom level
	private int sizeX; //Total size (in x pixels) of the map
	private int sizeY; //Total size (in y pixels) of the map
	private Image image; //Current image (current map) being displayed
	private double pixelToLat; //How many latitudes in a pixel
	private double pixelToLong; //How many longitudes in a pixel
	private int initialX; //Initial (first) x pixel of mouse drag
	private int initialY; //Initial (first) y pixel of mouse drag
	private int locationX; //x pixel of highlighted location
	private int locationY; //y pixel of highlighted location
	private int locSize; //Size of highlighted location
	private boolean locationSelected; //Has the user selected a location (launch site)
	private MapData mapData; //Used to get the map images
	private LaunchSelectedListener launchListener;
	
	/** This sets up fields, draws the initial map and determines longitude/latitude to pixel ratios */
	public SelectMapView(MapData data) {
		/* These latitudes and longitudes show Wellington Central */
		longUL = 174.739096;
		latUL = -41.273554;
		longBR = 174.832823;
		latBR = -41.333647;
		mapData = data;
		locationSelected = false;
		locSize = 20;
		zoom = 1;
		sizeX = this.getHeight();
		sizeY = this.getHeight();
		pixelToLongLat();
		repaint();
	}
	
	/** Calculates how many longitudes/latitudes are in a pixel */
	private void pixelToLongLat() {
		double longDiff = Math.abs(longUL - longBR);
		double latDiff = Math.abs(latUL - latBR);
		pixelToLong = longDiff/sizeX;
		pixelToLat = latDiff/sizeY;
	}
	
	@Override
	public void paintComponent(@Nullable Graphics g) {
		if (g == null) {
			return;
		}
		image = mapData.get(longUL, latUL, longBR, latBR); //Gets the image
		g.drawImage(image, size, size, this); //Draws it
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
		longUL = longUL - diffX*pixelToLong;
		longBR = longBR - diffX*pixelToLong;
		latUL = latUL + diffY*pixelToLat;
		latBR = latBR + diffY*pixelToLat;
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
			launchListener.onLaunchSelected((longUL + (locationX/pixelToLong)), (latUL + (locationY/pixelToLat));
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
		zoom = zoom - notches*0.1; //If notch < 0 then mouse wheel has scrolled up
		processZoom((notches*0.1)*-1); //Apply zoom to map
		repaint(); //Repaint (show new) map
	}

	/** Applys the current zoom to the map */
	private void processZoom(double zoomChange) {
		int longULPixels = (int) (longUL/pixelToLong); //Convert longitude to pixels
		longULPixels = (int) (longULPixels + (longULPixels*(zoomChange/2))); //Applying zoom to pixels
		longUL = longULPixels/pixelToLong; //Converting back to longitude
		
		int longBRPixels = (int) (longBR/pixelToLong); 
		longBRPixels = (int) (longBRPixels - (longBRPixels*(zoomChange/2)));
		longBR = longBRPixels/pixelToLong;
		
		int latULPixels = (int) (latUL/pixelToLat); //Convert latitude to pixels
		latULPixels = (int) (latULPixels + (latULPixels*(zoomChange/2))); //Applying zoom to pixels
		latUL = latULPixels/pixelToLat; //Converting back to latitude
		
		int latBRPixels = (int) (latBR/pixelToLat);
		latBRPixels = (int) (latBRPixels - (latBRPixels*(zoomChange/2)));
		latUL = latBRPixels/pixelToLat;
		
		locSize = locSize + (int)(locSize*zoomChange); //If the map is zoomed in then the mark should enlarge
	}
	
	public void addListener(LaunchSelectedListener listener) {
		launchListener = listener;
	}
}

