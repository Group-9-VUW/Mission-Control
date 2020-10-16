package nz.ac.vuw.engr301.group9mcs.avionics;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;

/**
 * A configuration panel for the LORA driver
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class LORAConfigPanel extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 8982784751106991015L;

	/**
	 * The LORA Driver to config
	 */
	private final LORADriver driver;

	/**
	 * The panel containing all the COM ports to select.
	 *
	 * Populated with populate()
	 */
	private final JPanel COMSelect = new JPanel();

	/**
	 * The control buttons at the bottom
	 */
	private final JPanel bottomButtons = new JPanel();

	/**
	 * Button to populate()
	 */
	private final JButton repopulate = new JButton("Populate");

	/**
	 * Button to confirm selection and close
	 */
	private final JButton confirm = new JButton("Confirm");

	/**
	 * List of all buttons that represent COM ports
	 */
	private final Set<JButton> ports = new HashSet<>();

	/**
	 * The currently selected COM port
	 */
	@Nullable private String selected;

	/**
	 * @param root The window this dialog should block
	 * @param driver The driver to configure
	 */
	public LORAConfigPanel(Window root, LORADriver driver)
	{
		super(root, "Set rocket COM port", Dialog.ModalityType.APPLICATION_MODAL);
		this.driver = driver;
		this.setLayout(new BorderLayout());

		this.add(new JLabel("Select serial COM port."), BorderLayout.NORTH);
		this.add(this.COMSelect, BorderLayout.CENTER);
		this.add(this.bottomButtons, BorderLayout.SOUTH);

		this.confirm.addActionListener(this);
		this.repopulate.addActionListener(this);

		this.initBottom();
		this.populate();

		this.setSize(350, 150);
		this.setVisible(true);
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
		this.bottomButtons.add(this.repopulate);
		gbc.gridx = 2;
		this.bottomButtons.add(this.confirm);
	}

	/**
	 * Populates the list of COM ports. Can be run multiple times, the method will
	 * erase the previous selection and create a new one
	 */
	private void populate()
	{
		String[] portlist = COMHelper.getAvailablePorts();

		this.COMSelect.removeAll();
		this.ports.clear();

		this.COMSelect.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.COMSelect.add(new JLabel("Port Name"), gbc);
		gbc.gridx = 1;
		this.COMSelect.add(new JLabel("Select"), gbc);

		if(portlist.length > 0) {
			for(String port : portlist) {
				JButton button = new JButton("Select");
				button.setActionCommand(port);
				button.addActionListener(this);
				this.ports.add(button);

				gbc.gridx = 0;
				gbc.gridy = gbc.gridy + 1;
				this.COMSelect.add(new JLabel(port), gbc);
				gbc.gridx = 1;
				this.COMSelect.add(button, gbc);
			}
		} else {
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 2;
			gbc.weightx = 1.0;
			this.COMSelect.add(new JLabel("No available COM Ports."), gbc);
		}
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e != null) {
			if(e.getSource() == this.repopulate) {
				this.selected = null;
				this.populate();
			} else if(e.getSource() == this.confirm) {
				if(this.selected != null) {
					try {
						this.driver.init(Null.nonNull(this.selected));
						this.setVisible(false);
						this.dispose();
					} catch(Exception ex) {
						DefaultLogger.logger.warn("Was unable to initiate COMs on the user selected port.");
						DefaultLogger.logger.warn(ex);
						JOptionPane.showMessageDialog(this, "Unable to initiate COMs on that port", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					this.setVisible(false);
					this.dispose();
				}
			} else {
				this.selected = e.getActionCommand();
				for(JButton port : this.ports) {
					if(port == e.getSource()) {
						port.setEnabled(false);
					} else {
						port.setEnabled(true);
					}
				}
			}
		}
	}

}
