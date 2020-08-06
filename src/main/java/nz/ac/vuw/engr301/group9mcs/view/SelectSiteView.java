/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.util.Observer;

import javax.swing.JPanel;

import nz.ac.vuw.engr301.group9mcs.externaldata.MapImage;

/**
 * @author Claire
 */
public class SelectSiteView extends JPanel implements LaunchSelectedListener {

	private static final long serialVersionUID = -1685376173755130952L;
	
	private final ViewObservable observable;
	private final SelectMapView selectPanel;
	
	/**
	 * Constructs SelectSiteView
	 * 
	 * @param observer An observer object to send commands to
	 * @param image An internet enabled MapImage for display purposes
	 */
	public SelectSiteView(Observer observer, MapImage image)
	{
		this.setLayout(new BorderLayout());
		
		this.observable = new ViewObservable(observer);
		this.selectPanel = new SelectMapView(image);
		this.selectPanel.addListener(this);
		
		this.add(this.selectPanel, BorderLayout.CENTER);
	}

	@Override
	public void onLaunchSelected(double lat, double lon) {
		// TODO Auto-generated method stub
	}

}
