package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View demo panel. For demonstrating the rocket display and cached map data.
 * 
 * @author Claire
 */
public class ViewDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7097275978056981331L;
	
	private final JButton load = new JButton("Load");
	private final JButton launchsim = new JButton("Simulate Launch");
	
	private final JPanel top = new JPanel();
	private final JPanel bottom = null; //SelectView required here
	
	private boolean simrunning = false;
	
	public ViewDemoPanel()
	{
		top.add(load);
		top.add(launchsim);
		this.setSaveAvailable(false);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		//this.add(bottom, BorderLayout.CENTER);
		
		load.addActionListener(this);
		launchsim.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == load) {
			//TODO: Implement saving
		} else if(e.getSource() == launchsim) {
			//TODO: implement sim behavior
		}
	}
	
	public void setSaveAvailable(boolean saveAvailable)
	{
		load.setEnabled(saveAvailable && !simrunning);
		launchsim.setEnabled(saveAvailable && !simrunning);
	}

}
