package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.PerspectiveController;

@SuppressWarnings("javadoc")
class TestPerspectiveController {

	private PerspectiveController p;
	private FakePerspective fp;
	private JPanel panel;
	private JFrame frame;
	private MenuController m;

	/**
	 * 
	 */
	@SuppressWarnings("null")
	private void setUpPerspective() {
		this.frame = new JFrame();
		this.m = new MenuController(this.frame);
		this.p = new PerspectiveController(this.m);
		this.fp = new FakePerspective("FakePerspective", null);
		this.fp.add("File/Start", null);
		this.p.addPerspective("start", this.fp);
		this.p.changePerspective("start");
	}

	@Test
	void testCheckMenuEnabled() {
		setUpPerspective();
		assertTrue(this.m.isEnabled("File/Start"));
	}
	
	@Test
	void testCheckPanelIsSet() {
		setUpPerspective();
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
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
	
	@Test
	void testCantChangeToNoneExistantPerspective() {
		setUpPerspective();
		try {
			this.p.changePerspective("second");
		} catch (PreconditionViolationException e) {
			assertTrue(e.getMessage().contains("second"));
		}
	}
	
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
	 * NOTE: Perspectives shouldn't be added after startup - this makes the menu move around.
	 */
	@Test
	void testCheckAddedMenuItemsAreDisabledWhenOutOfPersepective() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		second.add("File/Second", null);
		second.add("Julius/Null", null);
		this.p.addPerspective("second", second);
		this.p.changePerspective("start");
		assertFalse(this.m.isEnabled("File/Second"));
		assertFalse(this.m.isEnabled("Julius/Null"));
		this.p.changePerspective("second");
		assertTrue(this.m.isEnabled("File/Second"));
		assertTrue(this.m.isEnabled("Julius/Null"));
	}
	
	@Test
	void testCheckUpDateWorks() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"Switch view", "second"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}
	
	@Test
	void testUpDateRequiresSwitchView() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"second"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
	@Test
	void testUpDateIgnoresCapitalisation() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"SWITCH VIEW", "SECOND"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "second");
	}
	
	@Test
	void testUpDateRequiresPerspectiveName() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String[] args = {"switch view"};
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
	@Test
	void testUpDateRequiresStringArray() {
		setUpPerspective();
		FakePerspective second = new FakePerspective("second", null);
		this.p.addPerspective("second", second);
		String args = "second";
		this.p.update(null, args);
		assertEquals(this.p.getPanel().getComponent(0).getName(), "FakePerspective");
	}
	
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
