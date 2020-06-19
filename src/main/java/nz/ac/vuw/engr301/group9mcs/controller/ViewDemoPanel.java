package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import nz.ac.vuw.engr301.group9mcs.commons.LongLatHelper;
import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapData;
import nz.ac.vuw.engr301.group9mcs.view.DisplayMapView;

/**
 * View demo panel. For demonstrating the rocket display and cached map data.
 * 
 * @author Claire
 */
public class ViewDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7097275978056981331L;
	
	private final JButton load = new JButton("Load");
	private final JButton launchsim = new JButton("Simulate Launch");
	private final JButton stoplaunch = new JButton("Stop Launch");
	
	private final JPanel top = new JPanel();
	private JPanel bottom = new JPanel();
	private DisplayMapView dmv = null; 
	
	private Launch launch;
	private double launchLat;
	private double launchLon;
	
	private File saveFile = null;
	private boolean simrunning = false;
	private boolean saveAvailable = false;
	
	public ViewDemoPanel()
	{
		top.add(load);
		top.add(launchsim);
		top.add(stoplaunch);
		this.setSave(null);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(this.bottom, BorderLayout.CENTER);
		
		load.addActionListener(this);
		launchsim.addActionListener(this);
		stoplaunch.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == load) {
			CachedMapData data = new CachedMapData(this.saveFile);
			this.launchLat = data.centerLat();
			this.launchLon = data.centerLon();
			this.dmv = new DisplayMapView(data.centerLat(), data.centerLon(), data);
			this.dmv.updateRocketPosition(launchLat, launchLon);
			this.remove(this.bottom);
			this.bottom = this.dmv;
			this.add(this.bottom, BorderLayout.CENTER);
		} else if(e.getSource() == launchsim) {
			this.launch = this.new Launch();
			this.launch.start();
			this.simrunning = true;
			this.setSave(this.saveFile);
		} else if(e.getSource() == stoplaunch) {
			this.launch.stopLaunch();
			this.launch = null;
		}
	}
	
	/**
	 * Sets the save file available to this view. The actions capable of being undertaken are updated.
	 * 
	 * @param save The save
	 */
	public void setSave(File save)
	{
		this.saveFile = save;
		this.saveAvailable = save != null;
		load.setEnabled(saveAvailable && !simrunning);
		launchsim.setEnabled(saveAvailable && !simrunning);
		stoplaunch.setEnabled(simrunning);
	}
	
	private class Launch extends Thread {
		
		private final double SCALE_FACTOR = 1.0 / LongLatHelper.kilometersPerDegreeOfLatitude(ViewDemoPanel.this.launchLat);
		
		private double dist = 0.0;
		
		private volatile boolean running = true;
		
		@Override
		public void run()
		{
			while(dist <= 1.0 && running) {
				double posX = dist * SCALE_FACTOR;
				dist += 0.01;
				dmv.updateRocketPosition(launchLat + posX, launchLon);
				synchronized(this) {
					try {
						this.wait(250);
					} catch (InterruptedException e) {}
				}
			}
			ViewDemoPanel.this.simrunning = false;
			ViewDemoPanel.this.setSave(ViewDemoPanel.this.saveFile);
		}
		
		public void stopLaunch()
		{
			this.running = false;
		}
		
	}

}
