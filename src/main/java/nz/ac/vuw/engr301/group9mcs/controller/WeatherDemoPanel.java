/**
 *
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nz.ac.vuw.engr301.group9mcs.commons.OWWeatherData;
import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;

/**
 * @author Claire
 * @formatter Joshua
 */
public class WeatherDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4646037183904158183L;

	private final JButton getData = new JButton("Get");
	private final JTextField lat = new JTextField();
	private final JTextField lon = new JTextField();

	private final JLabel temperature = new JLabel("-");
	private final JLabel windspeed = new JLabel("-");
	private final JLabel pressure = new JLabel("-");
	private final JLabel precipitation = new JLabel("-");

	private final NOAAGetter getter = new NOAAGetter("ead647e24776f26ed6f63af5f1bbf68c");

	private final JPanel top = new JPanel();
	private final JPanel bottom = new JPanel();

	public WeatherDemoPanel()
	{
		this.top.add(new JLabel("Latitude: "));
		this.top.add(this.lat);
		this.top.add(new JLabel("Longditude: "));
		this.top.add(this.lon);
		this.top.add(this.getData);

		this.bottom.setLayout(new GridBagLayout());
		this.bottom.add(new JLabel("Temperature"), getConstraints(0, 0));
		this.bottom.add(this.temperature, getConstraints(1, 0));
		this.bottom.add(new JLabel("Windspeed"), getConstraints(0, 1));
		this.bottom.add(this.windspeed, getConstraints(1, 1));
		this.bottom.add(new JLabel("Pressure"), getConstraints(0, 2));
		this.bottom.add(this.pressure, getConstraints(1, 2));
		this.bottom.add(new JLabel("Precipitation"), getConstraints(0, 3));
		this.bottom.add(this.precipitation, getConstraints(1, 3));

		this.lat.setColumns(15);
		this.lon.setColumns(15);

		this.getData.addActionListener(this);
		this.setLayout(new BorderLayout());
		this.add(this.top, BorderLayout.NORTH);
		this.add(this.bottom, BorderLayout.CENTER);
	}

	private static GridBagConstraints getConstraints(int x, int y)	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		return constraints;
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e == null) {return;}
		if(e.getSource() == this.getData) {
			if(NOAAGetter.isAvailable()) {
				try {
					double latitude = Double.parseDouble(this.lat.getText());
					double longitude = Double.parseDouble(this.lon.getText());
					OWWeatherData data = this.getter.getWeatherData(latitude, longitude);
					this.temperature.setText(Double.toString(data.getTemperature()));
					this.windspeed.setText(Double.toString(data.getWindSpeed()));
					this.pressure.setText(Double.toString(data.getPressure()));
					this.precipitation.setText(Double.toString(data.getPrecipitation()));
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(this.getParent(),
						    "Latitude and longditude must both be numbers with no units.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				} catch (InvalidParameterException | IOException ex) {
					JOptionPane.showMessageDialog(this.getParent(),
						    ex.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				} 
			} else {
				JOptionPane.showMessageDialog(this.getParent(),
					    "The OpenWeatherMap API is not available.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
