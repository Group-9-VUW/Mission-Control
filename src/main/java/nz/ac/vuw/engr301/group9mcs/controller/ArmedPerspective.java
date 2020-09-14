package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * Perspective that holds the Panels for the View while the rocket is Armed.
 * 
 * @author Bryony
 *
 */
public class ArmedPerspective extends Observable implements Perspective, Observer {

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	JPanel panel;
	
	/** 
	 * WARNING PANEL : armed and dangerous, do not touch rocket -> close when rocket is fired
	 */
	private JPanel warningPanel;
	
	/** 
	 * ROCKET DATA PANEL : text output from rocket?
	 */
	private JPanel rocketDataPanel;
	
	/**
	 * Construct the Panel
	 */
	public ArmedPerspective() {
		this.panel = new JPanel(new BorderLayout());
		// TODO: A proper Rocket Data Panel
		this.rocketDataPanel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1254759273362687813L;
			
			@Override
			protected void paintComponent(@Nullable Graphics g) {
				Null.nonNull(g).setColor(Color.green);
				Null.nonNull(g).fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		this.rocketDataPanel.setPreferredSize(new Dimension(400, 300));
		// TODO: A proper Warning Panel : "Armed and Dangerous", "Do not touch while armed"
		this.warningPanel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2650026648197700627L;
			
			@Override
			protected void paintComponent(@Nullable Graphics g) {
				Null.nonNull(g).setColor(Color.red);
				Null.nonNull(g).fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		this.warningPanel.setPreferredSize(new Dimension(400, 100));
		this.panel.add(this.rocketDataPanel, BorderLayout.CENTER);
		this.panel.add(this.warningPanel, BorderLayout.NORTH);
	}

	@Override
	public JPanel enable(MenuController menu) {
		return this.panel;
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "armed";
	}
	
	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		// TODO Auto-generated method stub
	}

}
