package nz.ac.vuw.engr301.group9mcs.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.view.SelectFileView;
import nz.ac.vuw.engr301.group9mcs.view.ViewObservable;

/**
 * Perspective that holds the Panels for the Selecting a Launch Site.
 * 
 * @author Bryony
 *
 */
public class SelectSitePerspective extends Observable implements Perspective, Observer {

	/**
	 * The Panel displayed on the screen that holds all other panels.
	 */
	private JPanel panel;
	private String filename;
	
	/**
	 * Create the Perspective and construct the Panel.
	 */
	public SelectSitePerspective() {
		this.panel = new JPanel();
		this.filename = "";
	}
	
	@Override
	public JPanel enable(MenuController menu) {
		start();
		return this.panel;
	}
	
	/**
	 * Start the Perspective at the File Selection.
	 */
	private void start() {
		this.panel.add(new SelectFileView(new ViewObservable(this)));
	}
	
	private void second() {
		this.panel.removeAll();
		// this.panel.add(SelectSiteView(....
	}
	
	private void third() {
		this.panel.removeAll();
		// this.panel.add(GoNoGoView(....
	}

	@Override
	public void init(MenuController menu, Observer o) {
		this.addObserver(o);
	}

	@Override
	public String name() {
		return "select";
	}

	@Override
	public void update(@Nullable Observable o, @Nullable Object arg) {
		if(arg != null) {
			if(arg instanceof String) {
				this.filename = (String) arg;
				second();
			}
		}
	}
	
}
