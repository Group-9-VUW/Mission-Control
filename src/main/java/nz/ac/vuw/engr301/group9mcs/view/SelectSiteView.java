/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import nz.ac.vuw.engr301.group9mcs.commons.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.externaldata.MapImage;

/**
 * @author Claire
 */
public class SelectSiteView extends JPanel implements LaunchSelectedListener {

	private static final long serialVersionUID = -1685376173755130952L;
	
	private final ViewObservable observable;
	private final SelectMapView selectPanel;
	
	private final JTextField lat = new JTextField(20);
	private final JTextField lon = new JTextField(20);
	
	private final JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
	private final JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this.timeSpinner, "HH:mm");
	
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
		this.add(this.getSidePanel(), BorderLayout.WEST);
	}
	
	private final JPanel getSidePanel()
	{
		JPanel side = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.fill = GridBagConstraints.BOTH;
		
		side.add(new JLabel("Latitude:"), gbc);
		gbc.gridy++;
		side.add(this.lat, gbc);
		gbc.gridy++;
		side.add(new JLabel("Longitude:"), gbc);
		gbc.gridy++;
		side.add(this.lon, gbc);
		gbc.gridy++;
		
		this.timeSpinner.setEditor(this.timeEditor);
		this.timeSpinner.setValue(new Date()); 
		
		side.add(new JLabel("Launch Time (24hrs):"), gbc);
		gbc.gridy++;
		side.add(this.timeSpinner, gbc);
		gbc.gridy++;
		
		JButton submit = new JButton("Test Site");
		submit.addActionListener((e) -> {
			DefaultLogger.logger.debug("User pressed test site");
			this.observable.notify("site selected", this.lat.getText(), this.lon.getText(), ((Date) this.timeSpinner.getValue()).toString());
		});
		side.add(submit, gbc);
		gbc.gridy++;
		
		gbc.weighty = 1;
		side.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;
		
		JButton back = new JButton("Back");
		back.addActionListener((e) -> {
			DefaultLogger.logger.debug("User pressed back to rocket import");
			this.observable.notify(new String[] { "return to rocket import" });
		});
		side.add(back, gbc);
		gbc.gridy++;
		
		return side;
	}

	@Override
	public void onLaunchSelected(double latitude, double longitude) {
		this.lat.setText(Double.toString(latitude));
		this.lon.setText(Double.toString(longitude));
	}

}
