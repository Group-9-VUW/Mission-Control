/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	
	/**
	 * Constructor which adds the file chooser to the Panel
	 */
	public SelectFileView(){
		this.setLayout(new BorderLayout());
		this.chooser = new JFileChooser();
		this.filter = new FileNameExtensionFilter("Open Rocket Files", "ork");
		this.chooseFileButton = new JButton("Choose File");
		//this.chooseFileButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.chooseFileButton.setPreferredSize(new Dimension(60, 20));
		this.add(BorderLayout.NORTH, this.chooseFileButton);
		this.setPreferredSize(new Dimension(300, 300));
		
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
