package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

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
	 * Warning.
	 */
	private String[] warnings;
	
	/**
	 * Creates the Panel, adds warning message.
	 * 
	 * @param w
	 */
	public WarningPanel(String[] w) {
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
		FontMetrics smallFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize(longest, (Graphics2D) g, width)));
		
		Font[] fonts = fitToScreen(Null.nonNull(largeFont.getFont()), Null.nonNull(smallFont.getFont()), this.warnings.length, longest, (Graphics2D)g, height);
		largeFont = g.getFontMetrics(fonts[1]);
		Rectangle2D largeRect = largeFont.getStringBounds("WARNING", g);
		smallFont = g.getFontMetrics(fonts[0]);
		Rectangle2D smallRect = smallFont.getStringBounds(longest, g);
		
		
		int fontHeight = fontHeight(largeFont, Null.nonNull(largeRect), smallFont, Null.nonNull(smallRect), this.warnings.length, longest);
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
	}
	
	/**
	 * The total height of the combined strings based on the StringBounds and FontMetrics
	 * 
	 * @param large FontMetrics of the larger font
	 * @param largeRect	StringBounds of "WARNING" in the larger font
	 * @param small FontMetrics of the smaller font
	 * @param smallRect StringBounds of longest in the smaller font
	 * @param smallNumber Number of lines to be displayed in the smaller font
	 * @param longest The longest of the lines to be displayed in the smaller font
	 * @return The total height of the lines in various fonts.
	 */
	private static int fontHeight(FontMetrics large, Rectangle2D largeRect, FontMetrics small, Rectangle2D smallRect, int smallNumber, String longest) {
		int fontHeight = (int)(largeRect.getHeight() + (large.getAscent() / 4) 
				+ (smallRect.getHeight() * smallNumber) + ((small.getAscent() / 4) * (smallNumber - 1)));
		return fontHeight;
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
	
	/**
	 * Return two font sizes so that the Larger font "WARNING" and all lines of the smaller font can fit in the height of the screen.
	 * @param largeFont Larger Font
	 * @param smallFont Smaller Font
	 * @param smallNumber Number of Lines in the Smaller Font
	 * @param longest Longest line to be displayed in the smaller font
	 * @param g 
	 * @param height Height of the screen or rectangle to display lines in
	 * @return [new small font, new large font]
	 */
	private static Font[] fitToScreen(Font largeFont, Font smallFont, int smallNumber, String longest, Graphics2D g, int height) {
		Font small = smallFont;
		Font large = largeFont;
		FontMetrics fmS = g.getFontMetrics(small);
		FontMetrics fmL = g.getFontMetrics(large);
		Rectangle2D rectS = fmS.getStringBounds(longest, g);
		Rectangle2D rectL = fmL.getStringBounds("WARNING", g);
		int ratio = large.getSize() / small.getSize();
		int i = 0;
		while (height - 10 < fontHeight(fmL, Null.nonNull(rectL), fmS, Null.nonNull(rectS), smallNumber, longest)) {
			if (i % ratio == 0) {
				small = new Font(small.getFontName(), small.getStyle(), small.getSize() - 1);
				fmS = g.getFontMetrics(small);
			}
			large = new Font(large.getFontName(), large.getStyle(), large.getSize() - 1);
			fmL = g.getFontMetrics(large);
			rectS = fmS.getStringBounds(longest, g);
			rectL = fmL.getStringBounds("WARNING", g);
			i++;
		}
		Font[] fonts = {small, large};
		return fonts;
	}
	
}





