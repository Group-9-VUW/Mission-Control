package nz.ac.vuw.engr301.group9mcs.view.start;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Panel that displays the button for the start perspective
 *
 * @author Bryony Gatehouse
 * Copyright (C) 2020, Mission Control Group 9
 */
public class StartButton extends JPanel{

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Background Color
	 */
	private Color background;

	/**
	 * Button in center of panel.
	 */
	private JButton button;

	/**
	 * Construct the button.
	 *
	 * @param title String to be displayed on button
	 * @param background Background Color for the Panel
	 */
	public StartButton(String title, Color background) {
		this.background = background;
		this.setLayout(new GridBagLayout());
		this.button = new JButton(title);
		this.add(this.button, new GridBagConstraints());
	}

	@Override
	public void paintComponent(@Nullable Graphics g) {
		if (g == null) return;
		g.setColor(this.background);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Adds the given Action Listener to the Button.
	 *
	 * @param a
	 */
	public void addActionListener(ActionListener a) {
		this.button.addActionListener(a);
	}

}
