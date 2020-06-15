package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Display map view is for showing the position of the rocket. 
 * Its viewing zoom and position should be automatically decided 
 * based upon the position of the launch site and the position of 
 * the rocket. It's purely output, the user shouldn't be able to engage 
 * with it at all
 * There will be a MapData interface that allows you to get a map 
 * image for a certain area specified in longitude and latitude 
 * (I'm thinking upper-left, lower-right corner notation, as it 
 * allows the caller to choose aspect ratio in only two arguments). 
 * 
 * @author Bryony
 *
 */

public class DisplayMapView extends JPanel {
  
  /**
   * The Longitude of where the launch site is.
   */
  private double longLaunchSite;
  /**
   * The Latitude of where the launch site is.
   */
  private double latLaunchSite;
  /**
   * The longitude of where the rocket is.
   */
  private double longRocket;
  /**
   * The Latitude of where the rocket is.
   */
  private double latRocket;
  
  /**
   * UID.
   */
  private static final long serialVersionUID = 3810711176953194652L;

  /**
   * Set up JPanel for Displaying Map of Rocket's Progress.
   * @param latLaunchSite The Latitude of where the launch site is
   * @param longLaunchSite The Longitude of where the launch site is
   */
  public DisplayMapView(double latLaunchSite, double longLaunchSite) {
    this.longLaunchSite = longLaunchSite;
    this.longRocket = longLaunchSite;
    this.latLaunchSite = latLaunchSite;
    this.latRocket = latLaunchSite;
  }
  
  private void addMenu() {
    JMenuBar menu = new JMenuBar();
    JMenu group9 = new JMenu("Group9"); //$NON-NLS-1$
    JMenuItem exit = new JMenuItem("Quit Group9"); //$NON-NLS-1$
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(@Nullable ActionEvent e) {
        System.exit(0);
      }
    });
    group9.add(exit);
    menu.add(group9);
    this.add(menu);
  }
  
  @Override
  public void paintComponent(@Nullable Graphics g) {
    if (g == null) {
      return;
    }
    g.fillRect(0, 0, (int)this.longLaunchSite, (int)this.latLaunchSite);
    g.setColor(Color.white);
    g.drawRect(0, 0, (int)this.longRocket, (int)this.latRocket);
  }
  
  /**
   * Update the location of the rocket.
   * 
   * @param longR Longitude of the rocket's location
   * @param latR Latitude of the rocket's location
   */
  public void updateRocketPosition(double longR, double latR) {
    this.longRocket = longR;
    this.latRocket = latR;
  }
  
}
