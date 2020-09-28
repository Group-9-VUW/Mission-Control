package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.controller.MenuController;

/**
 * Tests for MenuController.
 * 
 * @author Claire
 * @editor Joshua Hindley
 */
public final class TestMenuController {
	
	/**
	 * Tests path canonicalization, including exceptions
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPathCanonicalization() {
		//tests canonicalizing paths that should be the same, but whose pre-canonicalized paths
		//include different valid numbers slashes and capitalisations.
		assertEquals("some/path with spaces", MenuController.canonicalizePath("some/path with spaces"));
		assertEquals("some/path with spaces", MenuController.canonicalizePath("some/path with spaces/"));
		assertEquals("some/path with spaces", MenuController.canonicalizePath("/some/path with spaces"));
		assertEquals("some/path with spaces", MenuController.canonicalizePath("/some/path with spaces/"));
		assertEquals("some/path with spaces", MenuController.canonicalizePath("/some/PaTh WiTh SpAcES/"));
		
		//tests that invalid paths result in PreconditionViolation exceptions being thrown
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("some/invalid/path"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("some/"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("/invalid"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("/"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("//"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("///"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("donut/some/invalid/path"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("some//path"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("//path"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("some//"); });
		assertThrows(PreconditionViolationException.class, () -> { MenuController.canonicalizePath("some///"); });
	}
	
	/**
	 * Tests adding, and using, menu items
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testMenuItem() {
		try {
			JFrame frame = new JFrame();
			MenuController controller = new MenuController(frame);
			ActionListener l1, l2, l3;
			controller.addMenuItem("menu", "item", "Item", l1 = (e) -> { /**/ } );
			controller.addMenuItem("menu", "item", "Item", l2 = (e) -> { /**/ } );
			controller.disableAll();
			assertFalse(controller.isEnabled("menu/item"));
			controller.enableItem("menu/item");
			assertTrue(controller.isEnabled("menu/item"));
			controller.addMenuItem("menu", "item2", "Item 2", l3 = (e) -> { /**/ } );
			assertEquals(l1, frame.getJMenuBar().getMenu(0).getItem(0).getActionListeners()[1]);
			assertEquals(l2, frame.getJMenuBar().getMenu(0).getItem(0).getActionListeners()[0]);
			assertEquals(l3, frame.getJMenuBar().getMenu(0).getItem(1).getActionListeners()[0]);
		} catch(@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("!!! You're running this test on a headless build, unable to run tests the depend on JFrame !!!");
		}
		
	}
	
	/**
	 * Tests for catching exceptions thrown for adding, and using, menu items
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testExceptionMenuItem() {
		try {
			JFrame frame = new JFrame();
			MenuController controller = new MenuController(frame);
			// Catch exceptions thrown by searching for an unknown menu item.
			assertThrows(PreconditionViolationException.class, () -> { controller.isEnabled("menu/item");});
			assertThrows(PreconditionViolationException.class, () -> { controller.enableItem("menu/item");});
			assertThrows(PreconditionViolationException.class, () -> { controller.setAlwaysEnabled("menu/item");});
			ActionListener l1 = new ActionListener() {@Override public void actionPerformed(@Nullable ActionEvent e) {/**/}};
			controller.addMenuItem("menu", "item", "Item", l1 );
			controller.addMenuItem("menu", "item2", "Item", l1 );
			String[] args = {"menu/item", "menu/item2"};
			// Check Enable Items through Array works
			controller.enableItems(args);
			assertTrue(controller.isEnabled("menu/item"));
			assertTrue(controller.isEnabled("menu/item2"));
			// Check Always Enabled is true
			controller.setAlwaysEnabled("menu/item");
			controller.disableAll();
			assertTrue(controller.isEnabled("menu/item"));
			
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("!!! You're running this test on a headless build, unable to run tests the depend on JFrame !!!");
		}
	}

}
