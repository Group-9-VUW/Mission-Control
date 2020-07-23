package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LongLatHelper;
import nz.ac.vuw.engr301.group9mcs.externaldata.CachedMapData;
import nz.ac.vuw.engr301.group9mcs.view.DisplayMapView;

/**
 * View demo panel. For demonstrating the rocket display and cached map data.
 *
 * @author Claire
 * @formatter Joshua
 */
@SuppressWarnings(value = "all")
public class ViewDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7097275978056981331L;

	private final JButton load = new JButton("Load");
	private final JButton launchsim = new JButton("Simulate Launch");
	private final JButton stoplaunch = new JButton("Stop Launch");

	private final JPanel top = new JPanel();
	private JPanel bottom = new JPanel();
	@Nullable DisplayMapView dmv;

	private @Nullable Launch launch;
	double launchLat;
	double launchLon;

	@Nullable File saveFile;
	boolean simrunning = false;
	private boolean saveAvailable = false;

	public ViewDemoPanel() {
		this.top.add(this.load);
		this.top.add(this.launchsim);
		this.top.add(this.stoplaunch);
		this.setSave(null);

		this.setLayout(new BorderLayout());
		this.add(this.top, BorderLayout.NORTH);
		this.add(this.bottom, BorderLayout.CENTER);

		this.load.addActionListener(this);
		this.launchsim.addActionListener(this);
		this.stoplaunch.addActionListener(this);
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e == null) {return;}
		File save = this.saveFile;
		if(e.getSource() == this.load) {
			assert save != null;
			CachedMapData data = new CachedMapData(save);
			this.launchLat = data.centerLat();
			this.launchLon = data.centerLon();
			this.dmv = new DisplayMapView(data.centerLat(), data.centerLon(), data);
			assert this.dmv != null;
			this.dmv.updateRocketPosition(this.launchLat, this.launchLon);
			this.remove(this.bottom);
			this.bottom = this.dmv;
			this.add(this.bottom, BorderLayout.CENTER);
		} else if(e.getSource() == this.launchsim) {
			this.launch = new Launch();
			this.launch.start();
			this.simrunning = true;
			this.setSave(save);
		} else if(e.getSource() == this.stoplaunch) {
			if(this.launch != null) {
				this.launch.stopLaunch();
				this.launch = null;
			}
		}
	}

	/**
	 * Sets the save file available to this view. The actions capable of being undertaken are updated.
	 *
	 * @param save The save
	 */
	public void setSave(@Nullable File save)
	{
		this.saveFile = save;
		this.saveAvailable = save != null;
		this.load.setEnabled(this.saveAvailable && !this.simrunning);
		this.launchsim.setEnabled(this.saveAvailable && !this.simrunning);
		this.stoplaunch.setEnabled(this.simrunning);
	}

	private class Launch extends Thread {

		protected Launch() {}
		private final double SCALE_FACTOR = 1.0 / LongLatHelper.kilometersPerDegreeOfLatitude(ViewDemoPanel.this.launchLat);

		private double dist = 0.0;

		private volatile boolean running = true;

		@Override
		public void run()
		{
			while(this.dist <= 1.0 && this.running) {
				double posX = this.dist * this.SCALE_FACTOR;
				this.dist += 0.01;
				ViewDemoPanel.this.dmv.updateRocketPosition(ViewDemoPanel.this.launchLat + posX, ViewDemoPanel.this.launchLon);
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
