/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.externaldata.MapData;
import nz.ac.vuw.engr301.group9mcs.view.LaunchSelectedListener;
import nz.ac.vuw.engr301.group9mcs.view.SelectMapView;

/**
 * @author Claire
 */
public class SelectDemoPanel extends JPanel implements ActionListener, LaunchSelectedListener {

	private static final long serialVersionUID = -274808354575526177L;
	
	private final JLabel lat = new JLabel("-");
	private final JLabel lon = new JLabel("-");
	
	private final JButton save = new JButton("Save");
	
	private final JPanel top = new JPanel();
	private final SelectMapView bottom;
	
	private final SimpleEventListener saveListener;
	
	private double latN, lonN;
	
	private File file;
	
	public SelectDemoPanel(MapData data, SimpleEventListener saveListener)
	{
		this.bottom = new SelectMapView(data);
		this.bottom.addListener(this);
		
		this.saveListener = saveListener;
		
		top.add(new JLabel("Latitude: "));
		top.add(lat);
		top.add(new JLabel("Longditude: "));
		top.add(lon);
		top.add(save);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		//this.add(bottom, BorderLayout.CENTER);
		
		save.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			saveListener.event("save");
		}
	}
	
	public File getSaveFile() {
		return this.file;
	}

	@Override
	public void onLaunchSelected(double lat, double lon) {
		this.lat.setText(Double.toString(lat));
		this.lon.setText(Double.toString(lon));
		this.latN = lat;
		this.lonN = lon;
	}

	/**
	 * @return the latN
	 */
	public double getLatN() {
		return latN;
	}

	/**
	 * @return the lonN
	 */
	public double getLonN() {
		return lonN;
	}

}
