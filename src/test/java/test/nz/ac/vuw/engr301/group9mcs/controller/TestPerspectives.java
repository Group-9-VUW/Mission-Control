package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.*;

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

	private void setUpPersistence() {
		JFrame f = new JFrame();
		MenuController m = new MenuController(f);
		///p = new PerspectiveController(m);
		//fp = new FakePerspective(
	}

	@Test
	void test() {

	}

}
