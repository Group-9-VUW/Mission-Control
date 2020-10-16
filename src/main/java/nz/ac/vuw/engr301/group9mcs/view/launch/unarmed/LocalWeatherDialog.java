package nz.ac.vuw.engr301.group9mcs.view.launch.unarmed;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LocalWeatherData;

/**
 * A dialog for collecting local weather data
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class LocalWeatherDialog extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -4638763170446938972L;

	/**
	 * The panel containing all the COM ports to select.
	 *
	 * Populated with populate()
	 */
	private final JPanel dataEntry = new JPanel(new GridBagLayout());

	/**
	 * The control buttons at the bottom
	 */
	private final JPanel bottomButtons = new JPanel();

	/**
	 * Button to cancel
	 */
	private final JButton cancel = new JButton("Cancel");

	/**
	 * Button to confirm selection and close
	 */
	private final JButton confirm = new JButton("Confirm");

	/**
	 * Field for windspeed
	 */
	private final JTextField windspeed = new JTextField();

	/**
	 * Field for wind direction
	 */
	private final JTextField winddirection = new JTextField();

	/**
	 * Field for barometric pressure
	 */
	private final JTextField pressure = new JTextField();

	/**
	 * Field for temperature
	 */
	private final JTextField temp = new JTextField();

	/**
	 * The data object (if one exists) that was collected by this dialg
	 */
	@Nullable private LocalWeatherData data;

	/**
	 * @param root The window this dialog should block
	 */
	public LocalWeatherDialog(Window root)
	{
		super(root, "Enter local weather data", Dialog.ModalityType.APPLICATION_MODAL);
		this.setLayout(new BorderLayout());

		this.add(new JLabel("Enter local weather data."), BorderLayout.NORTH);
		this.add(this.dataEntry, BorderLayout.CENTER);
		this.add(this.bottomButtons, BorderLayout.SOUTH);

		this.initBottom();
		this.initFields();

		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);

		this.setSize(470, 220);
		this.setVisible(true);
	}

	/**
	 * Populates the list of fields
	 */
	private void initFields()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.dataEntry.add(new JLabel("Data"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(new JLabel("Entry"), gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("Unit"), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
		this.dataEntry.add(new JLabel("Wind Speed"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.windspeed, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("ms-1"), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
		this.dataEntry.add(new JLabel("Wind Direction"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.winddirection, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("degrees"), gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		this.dataEntry.add(new JLabel("Barometric Pressure"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.pressure, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("bar"), gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		this.dataEntry.add(new JLabel("Temperature"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.temp, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("C"), gbc);

		this.windspeed.setColumns(10);
		this.winddirection.setColumns(10);
		this.pressure.setColumns(10);
		this.temp.setColumns(10);
	}

	/**
	 * Initializes the bottom panel
	 */
	private void initBottom()
	{
		this.bottomButtons.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		this.bottomButtons.add(new JPanel(), gbc);
		gbc.weightx = 0.0;
		gbc.gridx = 1;
		this.bottomButtons.add(this.confirm);
		gbc.gridx = 2;
		this.bottomButtons.add(this.cancel);
	}

	/**
	 * @return The weather data, if the user correctly entered it.
	 */
	public @Nullable LocalWeatherData getData()
	{
		return this.data;
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e != null) {
			if(e.getSource() == this.cancel) {
				this.setVisible(false);
				this.dispose();
			} else if(e.getSource() == this.confirm) {
				try {
					double nSpeed = Double.parseDouble(this.windspeed.getText());
					double nDirection = Double.parseDouble(this.winddirection.getText());
					double nPressure = Double.parseDouble(this.pressure.getText());
					double nTemp = Double.parseDouble(this.temp.getText());
					this.data = new LocalWeatherData(nSpeed, nDirection, nPressure, nTemp);
					this.setVisible(false);
					this.dispose();
				} catch(@SuppressWarnings("unused") NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Error", "One of the values you inputted was not a number", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}
