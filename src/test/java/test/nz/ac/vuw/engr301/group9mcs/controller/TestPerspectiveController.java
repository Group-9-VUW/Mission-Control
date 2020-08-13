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

/**
 * Test the Perspective Controller Class
 *
 * @author Bryony Gatehouse
 *
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
	private void setUpPerspective() {
		this.frame = new JFrame();
		this.m = new MenuController(Null.nonNull(this.frame));
		this.p = new PerspectiveController(Null.nonNull(this.m));
		this.fp = new FakePerspective("FakePerspective", null);
		this.fp.add("File/Start", "Start", Null.nonNull(this.fakeListen));
		this.p.addPerspective("start", Null.nonNull(this.fp));
		this.p.changePerspective("start");
	}

	/**
	 * Check that when a perspective is chosen the required menu items are enabled/added.
	 */
	@Test
	void testCheckMenuEnabled() {
		setUpPerspective();
		assertTrue(this.m.isEnabled("File/Start"));
	}

	/**
	 * Check that when a perspective is chosen the correct panel is added to the frame.
	 */
	@Test
	void testCheckPanelIsSet() {
		setUpPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that when a Perspective is changed, the panel is removed.
	 */
	@Test
	void test3CheckPanelIsRemovedAndAdded() {
		setUpPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
		this.p.changePerspective("second");
		assertTrue(this.p.getPanel().getComponents().length == 1);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that an error is thrown when the program tries to switch to a none existent perspective.
	 */
	@Test
	void testCantChangeToNoneExistantPerspective() {
		setUpPerspective();
		try {
			this.p.changePerspective("second");
		} catch (PreconditionViolationException e) {
			assertTrue(e.getMessage().contains("second"));
		}
	}

	/**
	 * Check that the panel passed to the Perspective can be changed while on the JFrame.
	 */
	@Test
	void testCheckPanelCanBeChangedOutsidePerspectiveController() {
		setUpPerspective();
		this.panel = new JPanel();
		FakePerspective second = new FakePerspective("second", this.panel);
		this.p.addPerspective("second", second);
		this.p.changePerspective("second");
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
		this.panel.setName("This is not a drill");
		assertTrue(!this.p.getPanel().getComponent(0).getName().equals("second"));
	}

	/**
	 * Check that the Perspective controller ignores capitalisation.
	 */
	@Test
	void testCheckUsingCapitalLettersDontMatter() {
		setUpPerspective();
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
	 * Check that the main panel in Perspective Controller doesn't change.
	 */
	@Test
	void testCheckPanelIsAlwaysSame() {
		setUpPerspective();
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
	void testCheckAddedMenuItemsAreDisabledWhenOutOfPersepective() {
		setUpPerspective();
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
	 * Check that the perspectives can change Perspective through the Update method.
	 */
	@Test
	void testCheckUpDateWorks() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"Switch view", "second"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the Update method requires the string "Switch View".
	 */
	@Test
	void testUpDateRequiresSwitchView() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"second"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the Update method ignores Capitalisation.
	 */
	@Test
	void testUpDateIgnoresCapitalisation() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"SWITCH VIEW", "SECOND"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}

	/**
	 * Check that the Update method requires the Perspective name.
	 */
	@Test
	void testUpDateRequiresPerspectiveName() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the UpDate method requires a String array.
	 */
	@Test
	void testUpDateRequiresStringArray() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String args = "second";
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

	/**
	 * Check that the Update method requires non-null
	 */
	@Test
	void testUpDateRequiresNonNull() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view", null};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}

}
