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

import nz.ac.vuw.engr301.group9mcs.commons.Null;

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
	private String[] warnings;
	
	/**
	 * Creates the Panel, adds Observer, adds warning message.
	 * 
	 * @param o
	 * @param w
	 */
	public WarningPanel(Observer o, String[] w) {
		this.obs = new ViewObservable(o);
		this.warnings = w;
		
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
		
		String longest = getLongest(this.warnings);
		
		FontMetrics largeFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize("WARNING", (Graphics2D) g, width)));
		Rectangle2D largeRect = largeFont.getStringBounds("WARNING", g);
		FontMetrics smallFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize(longest, (Graphics2D) g, width)));
		Rectangle2D smallRect = smallFont.getStringBounds(longest, g);
		int fontHeight = (int)(largeRect.getHeight() + (largeFont.getAscent() / 4) 
				+ (smallRect.getHeight() * this.warnings.length) + ((smallFont.getAscent() / 4) * (this.warnings.length - 1)));
		int y = (height / 2) - (fontHeight / 2);
		
		g.setColor(Color.yellow);
		g.fillRect(0, y, width, fontHeight);
		g.setColor(Color.black);
		
		int largeX = (int)((width - largeRect.getWidth()) / 2);
		g.setFont(largeFont.getFont());
		g.drawString("WARNING", largeX, y + (int)largeRect.getHeight() - (largeFont.getAscent() / 4));
		g.setFont(smallFont.getFont());
		for(int i = 0; i < this.warnings.length; i++) {
			smallRect = smallFont.getStringBounds(this.warnings[i], g);
			g.drawString(this.warnings[i], (int)((width - smallRect.getWidth()) / 2), 
					y + (int)largeRect.getHeight() + (int)smallRect.getHeight() + (((int)smallRect.getHeight() + (smallFont.getAscent() / 4)) * i));
		}
		
		//fitStringTop("WARNING", (Graphics2D) g, 0, y, width, fontHeight, largeFont);
		//fitStringBottom(this.warning, (Graphics2D) g, 0, y + largeFont.getHeight(), width, fontHeight, smallFont);
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
		if (fm.stringWidth(s) > width - 4) {
			font --;
		}
		return font;
	}
	
	/**
	 * Return the longest String from an array.
	 * 
	 * @param s
	 * @return Return the longest String from an array.
	 */
	private static String getLongest(String[] s) {
		String longest = "";
		for(int i = 0; i < s.length; i++) {
			if (s[i].length() > longest.length()) {
				longest = s[i];
			}
		}
		return Null.nonNull(longest);
	}
	
}





