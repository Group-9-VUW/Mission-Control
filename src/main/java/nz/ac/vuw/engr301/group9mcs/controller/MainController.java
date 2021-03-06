package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nz.ac.vuw.engr301.group9mcs.controller.perspectives.Perspective;

/**
 * Controller class.
 * Creates the screen.
 *
 * @author Bryony Gatehouse
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class MainController extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -6186153488874946242L;

	/**
	 * The menu controller.
	 */
	private final MenuController menu;

	/**
	 * The perspective controller.
	 */
	private final PerspectiveController persp;

	/**
	 * Creates the screen.
	 */
	public MainController() {
		super("Mission Control");

		this.menu = new MenuController(this);
		this.menu.addMenuItem("file/exit", "Exit", (e) -> {
			this.setVisible(false);
			this.dispose();
		});
		this.menu.setAlwaysEnabled("file/exit");

		try {
			this.persp = new PerspectiveController(this.menu, new Resources(this));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
			throw new Error(e);
		}
		this.setLayout(new BorderLayout());
		this.add(this.persp.getPanel(), BorderLayout.CENTER);

		this.setSize(1080, 690);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Adds a perspective to this main
	 *
	 * @param name The name of the perspective
	 * @param perspective The perspective
	 */
	public void addPerspective(String name, Perspective perspective)
	{
		this.persp.addPerspective(name, perspective);
	}

	/**
	 * Changes the active perspective. Should only be called once
	 *
	 * @param name The name of the perspective to set
	 */
	public void setPerspective(String name)
	{
		this.persp.changePerspective(name);
	}

}