package nz.ac.vuw.engr301.group9mcs.view.planning;

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

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;

/**
 * A dialog for collecting local weather data
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class LaunchRodDialog extends JDialog implements ActionListener {

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
	 * Field for angle
	 */
	private final JTextField angle = new JTextField();

	/**
	 * Field for directionn
	 */
	private final JTextField direction = new JTextField();

	/**
	 * Field for height
	 */
	private final JTextField height = new JTextField();

	/**
	 * The data object (if one exists) that was collected by this dialg
	 */
	@Nullable private LaunchRodData data;

	/**
	 * @param root The window this dialog should block
	 */
	public LaunchRodDialog(Window root)
	{
		super(root, "Enter launch rod data.", Dialog.ModalityType.APPLICATION_MODAL);
		this.setLayout(new BorderLayout());

		this.add(new JLabel("Enter launch rod data."), BorderLayout.NORTH);
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
		this.dataEntry.add(new JLabel("Rod Angle"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.angle, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("degrees"), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
		this.dataEntry.add(new JLabel("Rod Direction"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.direction, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("degrees"), gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		this.dataEntry.add(new JLabel("Rod Height"), gbc);
		gbc.gridx = 1;
		this.dataEntry.add(this.height, gbc);
		gbc.gridx = 2;
		this.dataEntry.add(new JLabel("meters"), gbc);


		this.angle.setColumns(10);
		this.direction.setColumns(10);
		this.height.setColumns(10);
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
	public @Nullable LaunchRodData getData()
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
					double nAngle = Double.parseDouble(this.angle.getText());
					double nDirection = Double.parseDouble(this.direction.getText());
					double nHeight = Double.parseDouble(this.height.getText());
					this.data = new LaunchRodData(nAngle, nDirection, nHeight);
					this.setVisible(false);
					this.dispose();
				} catch(@SuppressWarnings("unused") NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Error", "One of the values you inputted was not a number", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}
