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

import org.eclipse.jdt.annotation.Nullable;

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

	private @Nullable File file;

	public SelectDemoPanel(MapData data, SimpleEventListener saveListener)
	{
		this.bottom = new SelectMapView(data);
		this.bottom.addListener(this);

		this.saveListener = saveListener;

		this.top.add(new JLabel("Latitude: "));
		this.top.add(this.lat);
		this.top.add(new JLabel("Longditude: "));
		this.top.add(this.lon);
		this.top.add(this.save);

		this.setLayout(new BorderLayout());
		this.add(this.top, BorderLayout.NORTH);
		this.add(this.bottom, BorderLayout.CENTER);

		this.save.addActionListener(this);
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e == null) {return;}
		if(e.getSource() == this.save) {
			this.saveListener.event("save");
		}
	}

	public @Nullable File getSaveFile() {
		return this.file;
	}

	@Override
	public void onLaunchSelected(double latitude, double longitude) {
		this.lat.setText(Double.toString(latitude));
		this.lon.setText(Double.toString(longitude));
		this.latN = latitude;
		this.lonN = longitude;
	}

	/**
	 * @return the latN
	 */
	public double getLatN() {
		return this.latN;
	}

	/**
	 * @return the lonN
	 */
	public double getLonN() {
		return this.lonN;
	}

}
