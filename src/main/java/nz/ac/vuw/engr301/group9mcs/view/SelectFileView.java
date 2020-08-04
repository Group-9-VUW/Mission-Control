/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Allows the user to select a .ork file to load the rocket specifications. 
 * @author pandasai
 *
 */
public class SelectFileView extends JPanel {
	private JFileChooser chooser; 
	private FileNameExtensionFilter filter;
	private final JButton chooseFileButton;
	private JLabel fileNameLabel;
	
	/**
	 * Constructor which adds the file chooser to the Panel
	 */
	public SelectFileView(){
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		
		this.chooseFileButton = new JButton("Choose File");
		
		this.add(this.chooseFileButton, c);
		
		this.chooser = new JFileChooser();
		this.filter = new FileNameExtensionFilter("Open Rocket Files", "ork");
		
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 20;
		this.fileNameLabel = new JLabel("No File Selected", SwingConstants.CENTER);

		this.add(this.fileNameLabel, c);
//		this.add(BorderLayout.NORTH, this.chooseFileButton);
//		this.add(BorderLayout.CENTER, this.fileNameLabel);
		this.setPreferredSize(new Dimension(300, 100));
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		SelectFileView view = new SelectFileView(); 
		frame.add(view);
		frame.pack();
		frame.setVisible(true);
	}
}
