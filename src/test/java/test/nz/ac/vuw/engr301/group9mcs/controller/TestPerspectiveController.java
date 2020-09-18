package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.*;

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
class TestPerspectiveController {

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
		this.p = new PerspectiveController(Null.nonNull(this.m));
		this.fp = new FakePerspective("FakePerspective", null);
		this.fp.add("File/Start", "Start", Null.nonNull(this.fakeListen));
		this.p.addPerspective("start", Null.nonNull(this.fp));
		this.p.changePerspective("start", null);
	}

	/**
	 * Check that when a perspective is chosen the required menu items are enabled/added.
	 */
	@Test
	void testCheckMenuEnabled() {
		setupPerspective();
		assertTrue(this.m.isEnabled("File/Start"));
	}

	/**
	 * Check that when a perspective is chosen the correct panel is added to the frame.
	 */
	@Test
	void testCheckPanelIsSet() {
		setupPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that when a perspective is changed, the panel is removed.
	 */
	@Test
	void testPanelIsRemovedAndAdded() {
		setupPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		this.p.changePerspective("second", null);
		assertTrue(this.p.getPanel().getComponents().length == 1);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that an error is thrown when the program tries to switch to a non-existent perspective.
	 */
	@Test
	void testCantChangeToNonExistantPerspective() {
		setupPerspective();
		try {
			this.p.changePerspective("second", null);
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
	void testPanelCanBeChangedOutsidePerspectiveController() {
		setupPerspective();
		this.panel = new JPanel();
		FakePerspective second = new FakePerspective("second", this.panel);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second", null);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		this.panel.setName("This is not a drill");
		assertTrue(!this.p.getPanel().getComponent(0).getName().equals("second"));
	}

	/**
	 * Check that the perspective controller ignores capitalisation.
	 */
	@Test
	void testIgnoresCapitalisation() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		try {
			this.p.changePerspective("SECOND", null);
		} catch (PreconditionViolationException e) {
			fail("Name not in list: " + e.getMessage());
		}
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the main panel in perspective controller doesn't change.
	 */
	@Test
	void testPanelIsAlwaysSame() {
		setupPerspective();
		JPanel before = this.p.getPanel();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second", null);
		JPanel after = this.p.getPanel();
		assertEquals(before, after);
	}

	/**
	 * Check that menu items are disabled when that perspective isn't on screen
	 * NOTE: Perspectives shouldn't be added after startup - this makes the menu move around.
	 */
	@Test
	void testAddedMenuItemsAreDisabledWhenOutOfPersepective() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		second.add("File/Second", "Second", Null.nonNull(this.fakeListen));
		second.add("Julius/Null", "Null", Null.nonNull(this.fakeListen));
		this.p.addPerspective("second", second);
		this.p.changePerspective("start", null);
		assertFalse(this.m.isEnabled("File/Second"));
		assertFalse(this.m.isEnabled("Julius/Null"));
		this.p.changePerspective("second", null);
		assertTrue(this.m.isEnabled("File/Second"));
		assertTrue(this.m.isEnabled("Julius/Null"));
	}

	/**
	 * Check that the perspectives can change perspective through the update() method.
	 */
	@Test
	void testUpdateWorks() {
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
	void testUpdateRequiresSwitchView() {
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
	void testUpdateIgnoresCapitalisation() {
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
	void testUpdateRequiresPerspectiveName() {
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
	void testUpdateRequiresStringArray() {
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
	void testUpdateRequiresNonNull() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view", null};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
	/**
	 * Check that the Resource object is added to the Perspective correctly.
	 */
	@Test
	void testAddResource() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		Resources res = new Resources();
		res.setLongitude(2000);
		res.setLatitude(3.4);
		this.p.changePerspective("second", res);
		assertTrue(second.removeResource() != null);
		assertEquals(Null.nonNull(second.removeResource()).getLatitude(), res.getLatitude());
		assertEquals(Null.nonNull(second.removeResource()).getLongitude(), res.getLongitude());
	}
	
	/**
	 * Check that the Perspective uses a new Resource if none is given to them.
	 */
	@Test
	void testAddNoResource() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second", null);
		assertTrue(second.removeResource() != null);
	}
	
	/**
	 * Check that a resource is handed over when Perspectives are changed.
	 * Also checks that current perspective is saved correctly.
	 */
	@Test
	void testHandingOverResource() {
		setupPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		Resources res = new Resources();
		res.setLongitude(2000);
		res.setLatitude(3.4);
		this.fp.addResources(res);
		String[] args = {"switch view", "second"};
		this.p.update(null, args);
		assertTrue(second.removeResource() == res);
	}
	
}


















