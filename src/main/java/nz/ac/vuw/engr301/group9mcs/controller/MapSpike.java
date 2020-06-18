/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import nz.ac.vuw.engr301.group9mcs.commons.DisplayHelper;

/**
 * A runner class for the map demo. Executable.
 * <br><br>
 * Implements the main JFrame
 * 
 * @author Claire
 */
public class MapSpike extends JFrame implements WindowListener {

	private static final long serialVersionUID = 8869451586408924734L;
	
	protected static final int WINDOW_WIDTH = 900;
	protected static final int WINDOW_HEIGHT = 600;
	
	protected final SelectDemoPanel selectdemo = new SelectDemoPanel();

	/**
	 * @throws HeadlessException
	 */
	public MapSpike() throws HeadlessException {
		super("Map Demo");
		this.setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Select Demo", selectdemo);
		tabs.addTab("Test2", new JLabel("content2"));
		this.add(tabs, BorderLayout.CENTER);
		this.addWindowListener(this);
		this.setSize(900, 600);
		DisplayHelper.center(this);
	}
	
	@Override
	public void windowClosing(WindowEvent e) 
	{
		this.setVisible(false);
		this.dispose();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	public static final void main(String[] args) {
		new MapSpike().setVisible(true);
	}
	
}
