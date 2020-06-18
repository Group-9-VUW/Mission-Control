/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;

/**
 * @author Claire
 */
public class WeatherDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4646037183904158183L;

	private final JButton getData = new JButton();
	
	private final JPanel top = new JPanel();
	private final JPanel bottom = new JPanel();
	
	public WeatherDemoPanel()
	{
		top.add(getData);
		
		getData.addActionListener(this);
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == getData) {
			if(NOAAGetter.isAvailable()) {
				
			} else {
				JOptionPane.showMessageDialog(this.getParent(),
					    "The OpenWeatherMap API is not available.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
