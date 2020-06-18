/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Claire
 */
public class SelectDemoPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -274808354575526177L;
	
	private final JLabel lat = new JLabel("-");
	private final JLabel lon = new JLabel("-");
	
	private final JButton save = new JButton("Save");
	
	private final JPanel top = new JPanel();
	private final JPanel bottom = null; //SelectView required here
	
	public SelectDemoPanel()
	{
		top.add(new JLabel("Latitude: "));
		top.add(lat);
		top.add(new JLabel("Longditude: "));
		top.add(lon);
		top.add(save);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		//this.add(bottom, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			//TODO: Implement saving
		}
	}

}
