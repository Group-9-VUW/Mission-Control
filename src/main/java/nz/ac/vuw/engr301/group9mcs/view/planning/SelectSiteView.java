/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view.planning;

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

import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.MapImage;
import nz.ac.vuw.engr301.group9mcs.view.PostionSelectedListener;
import nz.ac.vuw.engr301.group9mcs.view.ViewObservable;

/**
 * @author Claire
 */
public class SelectSiteView extends JPanel implements PostionSelectedListener {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1685376173755130952L;
	
	/**
	 * Observable used to communicate with Parent
	 */
	private final ViewObservable observable;
	
	/**
	 * Panel that holds Selectable Map
	 */
	private final SelectMapPanel selectPanel;
	
	/**
	 * Display for Latitude
	 */
	private final JTextField lat = new JTextField(20);
	/**
	 * Display for Longitude
	 */
	private final JTextField lon = new JTextField(20);
	
	/**
	 * Spinner to Choose time
	 */
	private final JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
	/**
	 * Spinner to choose date
	 */
	private final JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this.timeSpinner, "HH:mm");
	
	/**
	 * Constructs SelectSiteView
	 * 
	 * @param observer An observer object to send commands to
	 * @param image An internet enabled MapImage for display purposes
	 */
	public SelectSiteView(Observer observer, MapImage image) {
		this.setLayout(new BorderLayout());
		
		this.observable = new ViewObservable(observer);
		this.selectPanel = new SelectMapPanel(image);
		this.selectPanel.addListener(this);
		
		this.add(this.selectPanel, BorderLayout.CENTER);
		this.add(this.getSidePanel(), BorderLayout.WEST);
	}
	
	/**
	 * @return The Displayed Side Panel holding Position and Date/Time choosers
	 */
	private final JPanel getSidePanel(){
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
