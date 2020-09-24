package test.nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * To be passed instead of JFrame
 * 
 * @author Bryony
 *
 */
public class FakeJFrame extends JFrame {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FakeJFrame(String name) {
		this.dispose();
		this.setName(name);
	}
	
}
