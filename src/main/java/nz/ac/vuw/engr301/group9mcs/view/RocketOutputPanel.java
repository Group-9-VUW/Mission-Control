package nz.ac.vuw.engr301.group9mcs.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays the information returned from the rocket.
 * 
 * @author Bryony
 *
 */
public class RocketOutputPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4163333043424348578L;
	
	private JTextArea textArea;
	
	/**
	 * Creates the Panel -> should be passed a listener/other that it connected to the rocket output.
	 */
	public RocketOutputPanel() {
		textArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
	}

}
