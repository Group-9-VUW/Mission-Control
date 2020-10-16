package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.montecarlo.MonteCarloSimulation;

/**
 * A dialog that shows the progress of the simulation
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class SimulationDialog extends JDialog implements SimpleEventListener {

	/**
	 */
	private static final long serialVersionUID = 6928052809594474751L;

	/**
	 * The display label for this class
	 */
	private final JLabel label = new JLabel("Initializing...");

	/**
	 * A reference to the running simulation
	 */
	private final MonteCarloSimulation simulation;

	/**
	 * @param owner
	 * @param simulation
	 */
	public SimulationDialog(Window owner, MonteCarloSimulation simulation) {
		super(owner, "Running Simulation", ModalityType.APPLICATION_MODAL);
		this.setSize(250, 220);
		this.simulation = simulation;
		simulation.addSimulationListener(this);

		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		this.label.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		this.add(this.label, gbc);
		this.setVisible(true);
	}

	@Override
	public void event(String type) {
		this.label.setText(this.simulation.getProgressString());
		if(this.simulation.isDone()) {
			this.setVisible(false);
			this.dispose();
		}
	}

}
