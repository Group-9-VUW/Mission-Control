package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.PerspectiveController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;

/**
 * Test the Perspective Controller class.
 *
 * @author Bryony Gatehouse
 * @editor Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public final class TestPerspectiveController {

	/**
	 * A Perspective Controller.
	 */
	@SuppressWarnings("null")
	private PerspectiveController p;

	/**
	 * A Fake Perspective to pass into the Controller.
	 */
	@SuppressWarnings("null")
	private FakePerspective fp;

	/**
	 * A JPanel.
	 */
	@SuppressWarnings("null")
	private JPanel panel;

	/**
	 * A JFrame.
	 */
	@SuppressWarnings("null")
	private JFrame frame;

	/**
	 * A resources object
	 */
	@SuppressWarnings("null")
	private Resources resources;

	/**
	 * A Menu Controller.
	 */
	@SuppressWarnings("null")
	private MenuController m;

	/**
	 * A Fake ActionListener which does nothing.
	 */
	private ActionListener fakeListen = e -> {
		e.getID();
	};

	/**
	 * Sets up the basic test classes.
	 */
	private void setupPerspective() {
		this.frame = new JFrame();
		this.m = new MenuController(Null.nonNull(this.frame));
		try {
			this.resources = new Resources(Null.nonNull(this.frame));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.p = new PerspectiveController(Null.nonNull(this.m), Null.nonNull(this.resources));
		this.fp = new FakePerspective("FakePerspective", null);
		this.fp.add("File/Start", "Start", Null.nonNull(this.fakeListen));
		this.p.addPerspective("start", Null.nonNull(this.fp));
		this.p.changePerspective("start");
	}

	/**
	 * Check that when a perspective is chosen the required menu items are enabled/added.
	 */
	@Test
	public void testCheckMenuEnabled() {
		try {
			setupPerspective();
			assertTrue(this.m.isEnabled("File/Start"));
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that when a perspective is chosen the correct panel is added to the frame.
	 */
	@Test
	public void testCheckPanelIsSet() {
		try {
			setupPerspective();
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that when a perspective is changed, the panel is removed.
	 */
	@Test
	public void testPanelIsRemovedAndAdded() {
		try {
			setupPerspective();
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
			this.p.changePerspective("second");
			assertTrue(this.p.getPanel().getComponents().length == 1);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that an error is thrown when the program tries to switch to a non-existent perspective.
	 */
	@Test
	public void testCantChangeToNonExistantPerspective() {
		try {
			setupPerspective();
			try {
				this.p.changePerspective("second");
				fail("Changing perspective to a non-existant perspective should result"
						+ "in a PreconditionViolationException being thrown.");
			} catch (PreconditionViolationException e) {
				assertTrue(e.getMessage().contains("second"));
			}
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the panel passed to the Perspective can be changed while on the JFrame.
	 */
	@Test
	public void testPanelCanBeChangedOutsidePerspectiveController() {
		try {
			setupPerspective();
			this.panel = new JPanel();
			FakePerspective second = new FakePerspective("second", this.panel);
			this.p.addPerspective("second", second);
			this.p.changePerspective("second");
			assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
			this.panel.setName("This is not a drill");
			assertTrue(!this.p.getPanel().getComponent(0).getName().equals("second"));
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the perspective controller ignores capitalisation.
	 */
	@Test
	public void testIgnoresCapitalisation() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			try {
				this.p.changePerspective("SECOND");
			} catch (PreconditionViolationException e) {
				fail("Name not in list: " + e.getMessage());
			}
			assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the main panel in perspective controller doesn't change.
	 */
	@Test
	public void testPanelIsAlwaysSame() {
		try {
			setupPerspective();
			JPanel before = this.p.getPanel();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			this.p.changePerspective("second");
			JPanel after = this.p.getPanel();
			assertEquals(before, after);
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that menu items are disabled when that perspective isn't on screen
	 * NOTE: Perspectives shouldn't be added after startup - this makes the menu move around.
	 */
	@Test
	public void testAddedMenuItemsAreDisabledWhenOutOfPersepective() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			second.add("File/Second", "Second", Null.nonNull(this.fakeListen));
			second.add("Julius/Null", "Null", Null.nonNull(this.fakeListen));
			this.p.addPerspective("second", second);
			this.p.changePerspective("start");
			assertFalse(this.m.isEnabled("File/Second"));
			assertFalse(this.m.isEnabled("Julius/Null"));
			this.p.changePerspective("second");
			assertTrue(this.m.isEnabled("File/Second"));
			assertTrue(this.m.isEnabled("Julius/Null"));
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the perspectives can change perspective through the update() method.
	 */
	@Test
	public void testUpdateWorks() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String[] args = {"Switch view", "second"};
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the update() method requires the string "Switch View".
	 */
	@Test
	public void testUpdateRequiresSwitchView() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String[] args = {"second"};
			//this should not change panels
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the update() method ignores capitalisation.
	 */
	@Test
	public void testUpdateIgnoresCapitalisation() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String[] args = {"SWITCH VIEW", "SECOND"};
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the update() method requires the perspective name.
	 */
	@Test
	public void testUpdateRequiresPerspectiveName() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String[] args = {"switch view"};
			//this should not change panels
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the update() method requires a string array.
	 */
	@Test
	public void testUpdateRequiresStringArray() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String args = "second";
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

	/**
	 * Check that the update() method requires non-null arguments.
	 */
	@Test
	public void testUpdateRequiresNonNull() {
		try {
			setupPerspective();
			FakePerspective second = new FakePerspective("second", null);
			this.p.addPerspective("second", second);
			String[] args = {"switch view", null};
			this.p.update(null, args);
			assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("No Screen connected");
		}
	}

}


















