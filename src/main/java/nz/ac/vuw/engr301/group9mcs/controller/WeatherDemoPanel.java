/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nz.ac.vuw.engr301.group9mcs.commons.WeatherData;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;

/**
 * @author Claire
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
		top.add(new JLabel("Latitude: "));
		top.add(lat);
		top.add(new JLabel("Longditude: "));
		top.add(lon);
		top.add(getData);
		
		bottom.setLayout(new GridBagLayout());
		bottom.add(new JLabel("Temperature"), getConstraints(0, 0));
		bottom.add(temperature, getConstraints(1, 0));
		bottom.add(new JLabel("Windspeed"), getConstraints(0, 1));
		bottom.add(windspeed, getConstraints(1, 1));
		bottom.add(new JLabel("Pressure"), getConstraints(0, 2));
		bottom.add(pressure, getConstraints(1, 2));
		bottom.add(new JLabel("Precipitation"), getConstraints(0, 3));
		bottom.add(precipitation, getConstraints(1, 3));
		
		lat.setColumns(15);
		lon.setColumns(15);
		
		getData.addActionListener(this);
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
	}
	
	private GridBagConstraints getConstraints(int x, int y)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		return constraints;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == getData) {
			if(NOAAGetter.isAvailable()) {
				try {
					double lat = Double.parseDouble(this.lat.getText());
					double lon = Double.parseDouble(this.lon.getText());
					WeatherData data = getter.getWeatherData(lat, lon);
					temperature.setText(Double.toString(data.getTemperature()));
					windspeed.setText(Double.toString(data.getWindSpeed()));
					pressure.setText(Double.toString(data.getPressure()));
					precipitation.setText(Double.toString(data.getPrecipitation()));
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(this.getParent(),
						    "Latitude and longditude must both be numbers with no units.",
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
