package nz.ac.vuw.engr301.group9mcs.controller.perspectives;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;
import nz.ac.vuw.engr301.group9mcs.controller.SavedLaunch;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAA;
import nz.ac.vuw.engr301.group9mcs.view.start.StartButton;

/**
 * Perspective the Application should open in - choose whether they want to create a new launch (at home) or launch a rocket (in field).
 *
 * @author Bryony Gatehouse
 * @editor Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class StartPerspective extends Observable implements Perspective {

	/**
	 * The Panel to return to Perspective Controller.
	 */
	private JPanel panel;

	/**
	 * Construct the Panel.
	 */
	public StartPerspective() {
		this.panel = new JPanel(new GridLayout(1, 2));
		this.panel.setPreferredSize(new Dimension(400, 400));

		StartButton select = new StartButton("Create a new Launch", Null.nonNull(Color.red));
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				if(!InternetMapImage.isAvailable()) {
					String[] args = {"Map Resource is currently not available.\n", "Please retry later.\n"};
					warn(args);
				} else if (!NOAA.isAvailable()) {
					String[] args = {"Weather Resource is currently not available.\n", "Please retry later.\n"};
					warn(args);
				} else {
					String[] args = {"This will write over your previous Launch.\n", "Are you sure you want to write over it?\n"};
					confirm(args, new String[] { "switch view", "site" });
				}
			}
		});

		StartButton unarmed = new StartButton("Run a saved Launch", Null.nonNull(Color.yellow));
		unarmed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				if(!SavedLaunch.hasLaunch()) {
					String[] args = {"No launch is saved.\n", "Please create a launch first.\n", "If you've already done this, it may be corrupted.\n"};
					warn(args);
				} else {
					String[] args = {"This will run the currently saved Launch.\n", "Please make sure you are in the same location\nthat was saved into the Launch.\n"};
					confirm(args, new String[] { "switch view", "unarmed" });
				}
			}
		});

		this.panel.add(select);
		this.panel.add(unarmed);
	}

	@Override
	public JPanel enable(MenuController menu, Resources resource) {
		return this.panel;
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "start";
	}

	@Override
	public void releaseResources() {
		// TODO Auto-generated method stub
	}

	/**
	 * Notifies the Observer that there is an Object they can view.
	 * Can be passed any type of Object.
	 *
	 * @param o
	 */
	private void notify(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}

	/**
	 * Opens an OptionPane to notify the user about what they are doing!
	 *
	 * @param message Message to display
	 * @param update Object to pass to parent if user confirms
	 */
	void confirm(String[] message, Object update) {
		int r = JOptionPane.showConfirmDialog(this.panel, message, "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
		if (r == JOptionPane.OK_OPTION) {
			this.notify(update);
		}
	}

	/**
	 * Opens an OptionPane to notify the user about a problem. Then close the screen.
	 *233-create-initial-perspective
	 * @param message Message to display
	 */
	void warn(String[] message) {
		JOptionPane.showMessageDialog(this.panel, message);
		System.exit(0);
	}

}
