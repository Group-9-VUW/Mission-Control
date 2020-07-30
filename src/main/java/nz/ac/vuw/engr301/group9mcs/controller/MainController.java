package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Controller class.
 * Creates the screen.
 *
 * @author Bryony
 *
 */
public class MainController implements Observer{

	/**
	 * The entire frame of the program
	 */
	private JFrame frame;
	/**
	 * The menu controller.
	 */
	private MenuController menu;
	/**
	 * The perspective controller.
	 */
	//private PerspectiveController view;

	/**
	 * Creates the screen.
	 */
	@SuppressWarnings("null")
	public MainController() {
		createScreen();
	}

	/**
	 * Create the screen.
	 */
	private void createScreen() {
		this.frame = new JFrame("Mission Control");

		this.menu = new MenuController(this.frame);
		this.menu.addObserver(this);
		// this.menu.enableItems( {"Path/To/Item"} );
		//this.view = new PerspectiveController();
		//this.view.changeState("select", this.frame, this.menu);

		this.frame.setPreferredSize(new Dimension(300, 300));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
	}

	/**
	 * Change to named panel.
	 *
	 * @param name
	 */
	private void changePanel(String name) {
		this.frame.removeAll();
		//this.menu.addMenuBar(this.frame);
		//this.view.changeState("select", this.frame, this.menu);
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if(arg instanceof String[]) {
			String[] args = (String[]) arg;
			if(args[0].equals("Switch View") && args.length == 2) {
				@Nullable String s = args[1];
				if(s != null) {
					changePanel(s);
				}
			}
		}
	}

}










