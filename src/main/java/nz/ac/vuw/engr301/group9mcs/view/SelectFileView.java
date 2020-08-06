package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import nz.ac.vuw.engr301.group9mcs.commons.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.commons.Null;


/**
 * Allows the user to select a .ork file to load the rocket specifications. 
 * @author pandasai
 *
 */
public class SelectFileView extends JPanel {
	private JFileChooser fileChooser;
	private final JButton chooseFileButton;
	private JLabel fileNameLabel;
	private ViewObservable observer;

	/**
	 * Constructor which adds the file chooser to the Panel.
	 * @param observer - The observer for this View that waits for the file input from the user. 
	 */
	public SelectFileView(Observer observer){
		this.observer = new ViewObservable(observer);

		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE; //Stops button from resizing with the JLabel
		c.gridx = 1;
		c.gridy = 0;

		this.chooseFileButton = new JButton("Choose File");
		
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new FileNameExtensionFilter("Open Rocket Files (.ork)", "ork"));
		
		this.chooseFileButton.addActionListener(e -> {
			this.chooseFile();
		});
		
		this.add(this.chooseFileButton, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 20;
		this.fileNameLabel = new JLabel("No File Selected", SwingConstants.CENTER);

		this.add(this.fileNameLabel, c);
		this.setPreferredSize(new Dimension(300, 100));
		
	}

	/**
	 * Opens the JFileChooser to allow the user to select a .ork file. 
	 */
	private void chooseFile() {
		int result = this.fileChooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION) {
			// User has selected a valid .ork file
			this.fileNameLabel.setText(this.fileChooser.getSelectedFile().getName());
			File orkFile = Null.nonNull(this.fileChooser.getSelectedFile());
			try {
				this.observer.notify("rocket imported", orkFile.getCanonicalPath());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "The chosen file is either invalid or inaccessible.", "Bad File", JOptionPane.ERROR_MESSAGE);
				DefaultLogger.logger.error("IOException getting canonical filename", e);
			}
		}
	}
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

}
