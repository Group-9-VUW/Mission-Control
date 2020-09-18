package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nz.ac.vuw.engr301.group9mcs.commons.RocketData;

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
	
	/**
	 * Text area to display rocket output.
	 */
	private JTextArea textArea;

	/**
	 * Creates the Panel
	 */
	public RocketOutputPanel() {
		this.setLayout(new BorderLayout());
		
		this.textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(this.textArea); 
		this.textArea.setEditable(false);
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Data will be converted to String format and pasted onto screen.
	 * It will then be discarded.
	 * 
	 * @param data Rocket Data to be displayed on screen.
	 */
	public void passRocketData(RocketData data) {
		this.textArea.setText(data.toString() + "\n");
	}

}
