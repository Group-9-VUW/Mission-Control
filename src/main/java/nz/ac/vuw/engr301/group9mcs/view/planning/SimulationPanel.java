package nz.ac.vuw.engr301.group9mcs.view.planning;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	 * @param persp The parent perspective
	 */
	public SimulationPanel(SelectSitePerspective persp)
	{
		this.owner = persp;
		
		this.setLayout(new BorderLayout());
		
		this.add(this.getMainPanel(), BorderLayout.NORTH);
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
		return panel;
	}
	
	/**
	 * @return The side panel
	 */
	private JPanel getSidePanel()
	{
		JPanel panel = new JPanel();
		return panel;
	}

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
