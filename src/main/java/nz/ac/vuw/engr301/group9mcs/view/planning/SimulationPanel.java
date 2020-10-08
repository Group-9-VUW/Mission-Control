package nz.ac.vuw.engr301.group9mcs.view.planning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.controller.perspectives.SelectSitePerspective;

/**
 * A panel for running and showing the results of simulations
 * 
 * @author Claire
 */
public class SimulationPanel extends JPanel implements ActionListener {
	
	/**
	 */
	private static final long serialVersionUID = -8786447041281812385L;

	/**
	 * This object's parent
	 */
	private final SelectSitePerspective owner;
	
	/**
	 * Run simulation button
	 */
	private JButton runSimulation = new JButton("Run Simulation");
	
	/**
	 * Return button
	 */
	private JButton goBack = new JButton("Select Different Site");
	
	/**
	 * Save button
	 */
	private JButton save = new JButton("Save site");
	
	/**
	 * A text area where the results go
	 */
	private JTextArea results = new JTextArea("Simulation not yet run");
	
	/**
	 * @param persp The parent perspective
	 */
	public SimulationPanel(SelectSitePerspective persp)
	{
		this.owner = persp;
		
		this.setLayout(new BorderLayout());
		
		this.add(this.getMainPanel(), BorderLayout.CENTER);
		this.add(this.getSidePanel(), BorderLayout.WEST);
		
		this.runSimulation.addActionListener(this);
		this.goBack.addActionListener(this);
		this.save.addActionListener(this);
	}
	
	/**
	 * @return The main panel
	 */
	private JPanel getMainPanel()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("No simulation run yet."), BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * @return The side panel
	 */
	private JPanel getSidePanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		
		this.results.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		this.results.setBackground(Color.LIGHT_GRAY.brighter());
		this.results.setColumns(22);
		
		panel.add(this.results, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		
		panel.add(new JPanel(), gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0.0;
		
		panel.add(this.runSimulation, gbc);
		
		gbc.gridy = 3;
		
		panel.add(this.save, gbc);
		
		gbc.gridy = 4;
		
		panel.add(this.goBack, gbc);
		
		return panel;
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
