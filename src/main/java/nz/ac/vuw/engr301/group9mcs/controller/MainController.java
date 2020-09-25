package nz.ac.vuw.engr301.group9mcs.controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Controller class.
 * Creates the screen.
 *
 * @author Bryony
 * @author Claire
 */
public class MainController extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6186153488874946242L;
	
	/**
	 * The menu controller.
	 */
	private final MenuController menu;
	
	/**
	 * The perspective controller.
	 */
	private final PerspectiveController persp;

	/**
	 * Creates the screen.
	 */
	public MainController() {
		super("Mission Control");
		
		this.menu = new MenuController(this);
		this.menu.addMenuItem(getExitMenuPath(), "Exit", (e) -> {
			this.setVisible(false);
			this.dispose();
		});
		this.menu.enableItem(getExitMenuPath());
		
		this.persp = new PerspectiveController(this.menu, new Resources(this));
		this.setLayout(new BorderLayout());
		this.add(this.persp.getPanel(), BorderLayout.CENTER);

		this.setSize(1080, 690);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Adds a perspective to this main
	 * 
	 * @param name The name of the perspective
	 * @param perspective The perspective
	 */
	public void addPerspective(String name, Perspective perspective)
	{
		this.persp.addPerspective(name, perspective);
	}
	
	/**
	 * Changes the active perspective. Should only be called once
	 * 
	 * @param name The name of the perspective to set
	 */
	public void setPerspective(String name)
	{
		this.persp.changePerspective(name);
	}
	
	/**
	 * @return The Menu Path for the file/exit button.
	 */
	public static String getExitMenuPath() {
		return "file/exit";
	}


}