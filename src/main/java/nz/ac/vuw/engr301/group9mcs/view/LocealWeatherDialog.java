package nz.ac.vuw.engr301.group9mcs.view;

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
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.LocalWeatherData;

/**
 * A dialog for collecting local weather data
 * 
 * @author Claire
 */
public class LocealWeatherDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4638763170446938972L;
	
	/**
	 * The panel containing all the COM ports to select.
	 * 
	 * Populated with populate()
	 */
	private final JPanel dataEntry = new JPanel();
	
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
	 * The data object (if one exists) that was collected by this dialg
	 */
	@Nullable private LocalWeatherData data;
	
	/**
	 * @param root The window this dialog should block
	 */
	public LocealWeatherDialog(Window root)
	{
		super(root, "Enter local weather data", Dialog.ModalityType.APPLICATION_MODAL);
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel("Enter local weather data."), BorderLayout.NORTH);
		this.add(this.dataEntry, BorderLayout.CENTER);
		this.add(this.bottomButtons, BorderLayout.SOUTH);
		
		this.initBottom();
		//this.initFields();
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

	@Override
	public void actionPerformed(@Nullable ActionEvent e) {
		if(e != null) {
			if(e.getSource() == this.cancel) {
				this.setVisible(false);
				this.dispose();
			} else if(e.getSource() == this.confirm) {
				
			}
		}
	}

}
