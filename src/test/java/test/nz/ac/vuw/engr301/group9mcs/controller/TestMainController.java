package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.controller.MainController;

/**
 * Tests for MainController.
 *
 * @author Bryony Gatehouse
 * Copyright (C) 2020, Mission Control Group 9
 */
public final class TestMainController {

	/**
	 * Perspective that can be trusted.
	 */
	@SuppressWarnings("null")
	private FakePerspective fp;
	/**
	 * JPanel to pass to Perspective
	 */
	@SuppressWarnings("null")
	private JPanel pa;
	/**
	 * MainController to Test.
	 */
	@SuppressWarnings("null")
	private MainController m;

	/**
	 * Reset basic Elements
	 */
	private void setup() {
		this.pa = new JPanel();
		this.pa.setName("Fake Panel");
		this.fp = new FakePerspective("first", this.pa);
		this.m = new MainController();
		this.m.setVisible(false);
		this.m.setFocusable(false);
	}

	/**
	 * Checks that Panel is not added when Perspective added.
	 */
	@Test
	public void testPerspectiveAdd() {
		try {
			setup();
			this.m.addPerspective(this.fp.name(), this.fp);
			Component[] mCom = this.m.getComponents();
			JRootPane jrPane = (JRootPane)mCom[0];
			Component[] jrCom = jrPane.getComponents();
			JLayeredPane jlPane;
			if(jrCom[0] instanceof JLayeredPane) {
				jlPane = (JLayeredPane)jrCom[0];
			} else {
				jlPane = (JLayeredPane)jrCom[1];
			}
			Component[] jlCom = jlPane.getComponents();
			JPanel jPanel;
			if(jlCom[0] instanceof JPanel) {
				jPanel = (JPanel)jlCom[0];
			} else {
				jPanel = (JPanel)jlCom[1];
			}
			Component[] jPCom = jPanel.getComponents();
			JPanel jPanel2 = (JPanel)jPCom[0];
			Component[] jP2Com = jPanel2.getComponents();
			assertTrue(jP2Com.length == 0);
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("Test failed to run due to lack of monitor");
		}
	}

	/**
	 * Checks that Panel is added when Perspective set.
	 */
	@Test
	public void testPerspectiveSet() {
		try {
			setup();
			this.m.addPerspective(this.fp.name(), this.fp);
			this.m.setPerspective(this.fp.name());
			Component[] mCom = this.m.getComponents();
			JRootPane jrPane = (JRootPane)mCom[0];
			Component[] jrCom = jrPane.getComponents();
			JLayeredPane jlPane;
			if(jrCom[0] instanceof JLayeredPane) {
				jlPane = (JLayeredPane)jrCom[0];
			} else {
				jlPane = (JLayeredPane)jrCom[1];
			}
			Component[] jlCom = jlPane.getComponents();
			JPanel jPanel;
			if(jlCom[0] instanceof JPanel) {
				jPanel = (JPanel)jlCom[0];
			} else {
				jPanel = (JPanel)jlCom[1];
			}
			Component[] jPCom = jPanel.getComponents();
			JPanel jPanel2 = (JPanel)jPCom[0];
			Component[] jP2Com = jPanel2.getComponents();
			JPanel first = (JPanel)jP2Com[0];
			assertTrue(first.getName().equals(this.pa.getName()));
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("Test failed to run due to lack of monitor");
		}
	}

	/**
	 * Checks that Frame is disabled when exit button clicked.
	 */
	@Test
	public void testClose() {
		try {
			setup();
			assertTrue(this.m.isDisplayable());
			Component[] mCom = this.m.getComponents();
			JRootPane jrPane = (JRootPane)mCom[0];
			Component[] jrCom = jrPane.getComponents();
			JLayeredPane jlPane;
			if(jrCom[0] instanceof JLayeredPane) {
				jlPane = (JLayeredPane)jrCom[0];
			} else {
				jlPane = (JLayeredPane)jrCom[1];
			}
			Component[] jlCom = jlPane.getComponents();
			JMenuBar menubar;
			if (jlCom[0] instanceof JMenuBar) {
				menubar = (JMenuBar)jlCom[0];
			} else {
				menubar = (JMenuBar)jlCom[1];
			}
			JMenu menu = menubar.getMenu(0);
			Component[] meCom = menu.getMenuComponents();
			JMenuItem i = (JMenuItem)meCom[0];
			ActionListener act = i.getActionListeners()[0];
			act.actionPerformed(new ActionEvent(this, 0, "hello"));
			assertFalse(this.m.isDisplayable());
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("Test failed to run due to lack of monitor");
		}
	}
}
