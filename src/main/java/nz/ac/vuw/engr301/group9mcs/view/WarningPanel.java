package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
	private ViewObservable obs;
	
	/**
	 * Warning.
	 */
	private String warning;
	
	/**
	 * Creates the Panel, adds Observer, adds warning message.
	 * 
	 * @param o
	 * @param w
	 */
	public WarningPanel(Observer o, String w) {
		//this.obs = new ViewObservable(o);
		this.warning = w;
		
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
		
		FontMetrics largeFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize("WARNING", (Graphics2D) g, width)));
		Rectangle2D largeRect = largeFont.getStringBounds("WARNING", g);
		FontMetrics smallFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize(this.warning, (Graphics2D) g, width)));
		Rectangle2D smallRect = smallFont.getStringBounds(this.warning, g);
		int fontHeight = (int)(largeRect.getHeight() + (largeFont.getAscent() / 4) + smallRect.getHeight());
		int y = (height / 2) - (fontHeight / 2);
		
		g.setColor(Color.yellow);
		g.fillRect(0, y, width, fontHeight);
		g.setColor(Color.black);
		
		int smallX = (int)((width - smallRect.getWidth()) / 2);
		int largeX = (int)((width - largeRect.getWidth()) / 2);
		g.setFont(largeFont.getFont());
		g.drawString("WARNING", largeX, y + (int)largeRect.getHeight() - (largeFont.getAscent() / 4));
		g.setFont(smallFont.getFont());
		g.drawString(this.warning, smallX, y + (int)largeRect.getHeight() + (int)smallRect.getHeight());
		
		//fitStringTop("WARNING", (Graphics2D) g, 0, y, width, fontHeight, largeFont);
		//fitStringBottom(this.warning, (Graphics2D) g, 0, y + largeFont.getHeight(), width, fontHeight, smallFont);
	}
	
	/**
	 * Display the String in the rectangle (x, y, width, height).
	 * 
	 * Should be centered on the y axis.
	 * Should be close to the bottom of the rectangle.
	 * Should fill the width of the rectangle.
	 * @param s 
	 * @param g 
	 * @param x 
	 * @param y 
	 * @param width 
	 * @param height 
	 * @param fm 
	 */
	private static void fitStringTop(String s, Graphics2D g, int x, int y, int width, int height, FontMetrics fm) {
		g.setFont(fm.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g);
		int xString = (int)((width - rect.getWidth()) / 2);
		int yString = 0;
		g.drawString(s, x + xString, y + yString);
	}
	
	/**
	 * Display the String in the rectangle (x, y, width, height).
	 * 
	 * Should be centered on the y axis.
	 * Should be close to the top of the rectangle.
	 * Should fill the width of the rectangle.
	 * 
	 * @param s
	 * @param g
	 * @param width
	 * @param x
	 * @param y
	 * @param height
	 * @param fm 
	 */
	private static void fitStringBottom(String s, Graphics2D g, int x, int y, int width, int height, FontMetrics fm) {
		g.setFont(fm.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g);
		int xString = (int)((width - rect.getWidth()) / 2);
		int yString = 0;
		g.drawString(s, x + xString, y + yString);
	}
	
	/**
	 * Return a font size for the String that fills the width.
	 * 
	 * @param s
	 * @param g
	 * @param width
	 * @return Returns a font size.
	 */
	private static int getFontSize(String s, Graphics2D g, int width) {
		int font = 1;
		Font f = new Font("Serif", Font.PLAIN, font);
		FontMetrics fm = g.getFontMetrics(f);
		while(fm.stringWidth(s) < width - 10) {
			font ++;
			f = new Font("Serif", Font.PLAIN, font);
			fm = g.getFontMetrics(f);
		}
		if (fm.stringWidth(s) > width) {
			font --;
		}
		System.out.println(s + " " + font);
		return font;
	}
	
}





