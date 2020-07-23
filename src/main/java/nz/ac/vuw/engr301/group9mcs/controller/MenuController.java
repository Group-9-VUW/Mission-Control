package main.java.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Bryony
 *
 */
public class MenuController {

	/**
	 * 
	 */
	private JMenu selectLaunch;
	/**
	 * 
	 */
	private JMenu preLaunch;
	/**
	 * 
	 */
	private JMenu launch;
	
	/**
	 * @param frame
	 */
	@SuppressWarnings("null")
	public MenuController(JFrame frame) {
		createMenu();
	}
	
	/**
	 * @return Returns a Menu with all important items added
	 */
	private JMenuBar createMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(quit);
		
		this.selectLaunch = new JMenu("Select Launch");
		JMenuItem enter = new JMenuItem("Enter Coordinates");
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				// TODO What to do?
			}
		});
		this.selectLaunch.add(enter);
		this.selectLaunch.setEnabled(false);
		
		this.preLaunch = new JMenu("Pre-Launch");
		JMenuItem enterPre = new JMenuItem("Enter Coordinates");
		enterPre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				// TODO What to do?
			}
		});
		this.preLaunch.add(enterPre);
		this.preLaunch.setEnabled(false);
		
		this.launch = new JMenu("Launch");
		JMenuItem labels = new JMenuItem("Hide Coordinates");
		labels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				// TODO Auto-generated method stub
				if(labels.getText().equals("Hide Coordinates")){
					labels.setText("See Coordinates");
				} else {
					labels.setText("Hide Coordinates");
				}
			}
		});
		this.launch.add(labels);
		this.launch.setEnabled(false);
		
		menu.add(file);
		menu.add(this.launch);
		menu.add(this.preLaunch);
		menu.add(this.selectLaunch);
		return menu;
	}
	
}











