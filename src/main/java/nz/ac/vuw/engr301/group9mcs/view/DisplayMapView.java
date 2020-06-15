package nz.ac.vuw.engr301.group9mcs.view;

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
   * UID.
   */
  private static final long serialVersionUID = 3810711176953194652L;

  /**
   * Set up JPanel for Displaying Map of Rocket's Progress.
   */
  public DisplayMapView() {
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
    g.drawRect(0, 0, 100, 100);
  }
  
}
