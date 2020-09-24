package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.PerspectiveController;
import nz.ac.vuw.engr301.group9mcs.controller.Resources;

/**
 * Test the Perspective Controller class.
 *
 * @author Bryony Gatehouse
 * @editor Joshua Hindley
 */
public final class TestPerspectiveController {

	/**
	 * A Perspective Controller.
	 */
	private PerspectiveController p;
	
	/**
	 * A Fake Perspective to pass into the Controller.
	 */
	private FakePerspective fp;
	
	/**
	 * A JPanel.
	 */
	private JPanel panel;
	
	/**
	 * A JFrame.
	 */
	private JFrame frame;
	
	/**
	 * A resources object
	 */
	private Resources resources;
	
	/**
	 * A Menu Controller.
	 */
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
		this.resources = new Resources(Null.nonNull(this.frame));
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
		setupPerspective();
		assertTrue(this.m.isEnabled("File/Start"));
	}

	/**
	 * Check that when a perspective is chosen the correct panel is added to the frame.
	 */
	@Test
	public void testCheckPanelIsSet() {
		setupPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that when a perspective is changed, the panel is removed.
	 */
	@Test
	public void testPanelIsRemovedAndAdded() {
		setupPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		this.p.changePerspective("second");
		assertTrue(this.p.getPanel().getComponents().length == 1);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that an error is thrown when the program tries to switch to a non-existent perspective.
	 */
	@Test
	public void testCantChangeToNonExistantPerspective() {
		setupPerspective();
		try {
			this.p.changePerspective("second");
			fail("Changing perspective to a non-existant perspective should result"
					+ "in a PreconditionViolationException being thrown.");
		} catch (PreconditionViolationException e) {
			assertTrue(e.getMessage().contains("second"));
		}
	}

	/**
	 * Check that the panel passed to the Perspective can be changed while on the JFrame.
	 */
	@Test
	public void testPanelCanBeChangedOutsidePerspectiveController() {
		setupPerspective();
		this.panel = new JPanel();
		FakePerspective second = new FakePerspective("second", this.panel);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second");
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		this.panel.setName("This is not a drill");
		assertTrue(!this.p.getPanel().getComponent(0).getName().equals("second"));
	}

	/**
	 * Check that the perspective controller ignores capitalisation.
	 */
	@Test
	public void testIgnoresCapitalisation() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		try {
			this.p.changePerspective("SECOND");
		} catch (PreconditionViolationException e) {
			fail("Name not in list: " + e.getMessage());
		}
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the main panel in perspective controller doesn't change.
	 */
	@Test
	public void testPanelIsAlwaysSame() {
		setupPerspective();
		JPanel before = this.p.getPanel();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second");
		JPanel after = this.p.getPanel();
		assertEquals(before, after);
	}

	/**
	 * Check that menu items are disabled when that perspective isn't on screen
	 * NOTE: Perspectives shouldn't be added after startup - this makes the menu move around.
	 */
	@Test
	public void testAddedMenuItemsAreDisabledWhenOutOfPersepective() {
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
	}

	/**
	 * Check that the perspectives can change perspective through the update() method.
	 */
	@Test
	public void testUpdateWorks() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"Switch view", "second"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the update() method requires the string "Switch View".
	 */
	@Test
	public void testUpdateRequiresSwitchView() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"second"};
		//this should not change panels
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the update() method ignores capitalisation.
	 */
	@Test
	public void testUpdateIgnoresCapitalisation() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"SWITCH VIEW", "SECOND"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the update() method requires the perspective name.
	 */
	@Test
	public void testUpdateRequiresPerspectiveName() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view"};
		//this should not change panels
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the update() method requires a string array.
	 */
	@Test
	public void testUpdateRequiresStringArray() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String args = "second";
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the update() method requires non-null arguments.
	 */
	@Test
	public void testUpdateRequiresNonNull() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view", null};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
}


















