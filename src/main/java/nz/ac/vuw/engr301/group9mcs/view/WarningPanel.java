package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Shows a warning.
 * 
 * @author Bryony
 *
 */
public class WarningPanel extends JPanel{

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1236212273802691908L;

	/**
	 * The observable that speaks to Parent.
	 */
	ViewObservable obs;
	
	/**
	 * Creates the Panel, adds Observer.
	 * 
	 * @param o
	 */
	public WarningPanel(Observer o) {
		this.obs = new ViewObservable(o);
		
		this.setPreferredSize(new Dimension(300, 300));
		this.setLayout(new BorderLayout());
	}
	
	@Override
	protected void paintComponent(@Nullable Graphics g) {
		if (g == null) return;
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		g.setColor(Color.red);
		g.fillRect(0, 0, width, height);
	}
	
}





