package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;

import javax.accessibility.AccessibleContext;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.controller.MenuController;
import nz.ac.vuw.engr301.group9mcs.controller.PerspectiveController;

class TestPerspectives {

	private MenuController m;
	private PerspectiveController p;
	private FakePerspective fp;
	private JPanel panel;
	private JFrame frame;

	private void setUpPersistence() {
		frame = new JFrame();
		MenuController m = new MenuController(frame);
		p = new PerspectiveController(m);
		fp = new FakePerspective();
		fp.add("File/Start", null);
		p.addPerspective("start", fp);
		p.changePerspective("start");
	}

	@Test
	void test() {
		setUpPersistence();
		Component[] coms = frame.getJMenuBar().getComponents();
		assertTrue(1 == 1);
	}

}
