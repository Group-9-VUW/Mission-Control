package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Observer;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * The Side Panel for Unarmed Perspective.
 * 
 * @author Bryony
 *
 */
public class GoNoGoSidePanelAtSite extends JPanel {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1687182642676145786L;
	
	/**
	 * Observable to speak backwards
	 */
	ViewObservable obs;
	
	/**
	 * Whether or not we've received simulation data
	 */
	private boolean hasData = false;
	
	/**
	 * The probability that we'll land safely [0-100]%
	 */
	private double safeProbability = -1.0;
	
	/**
	 * The estimated distance from the launch site in meters
	 */
	private double predictedDist = -1.0;

	/**
	 * Save passed parameters (information about simulation)
	 * 
	 * @param o
	 */
	public GoNoGoSidePanelAtSite(Observer o) {
		this.obs = new ViewObservable(o);
	}
	
	/**
	 * Informs this panel that a simulation has been run so that it can display the results.
	 * 
	 * @param nSafeProbability The probability that we'll land safely
	 * @param nPredictedDist The estimated average distance from the launch site
	 */
	public void giveData(double nSafeProbability, double nPredictedDist)
	{
		this.hasData = true;
		this.safeProbability = nSafeProbability;
		this.predictedDist = nPredictedDist;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(@Nullable Graphics g) {
		int height = this.getHeight() / 2 - this.getHeight() / 8;
		int startGap = this.getHeight() / 16;
		
		if(this.hasData) {
			String[] large1 = { this.safeProbability + "%"};
			String[] small1 = {"Probability of Landing Safely", "(Not on someone's head)"};
			printSection(large1, small1, startGap, height, g);
			
			String[] large2 = { this.predictedDist + "m"};
			String[] small2 = {"Predicted Landing from Launch Site", ""};
			printSection(large2, small2, this.getHeight() / 2 + startGap, height, g);
			
			//TODO: Print recommendation
		} else {
			String[] large1 = { "Don't Launch" };
			String[] small1 = { "You're one simulation short of", "actually having run a simulation." };
			printSection(large1, small1, startGap, height, g);
		}
	}

	/**
	 * Prints the section assuming that Large contains short strings and small contains long strings.
	 * Large strings are printed on top.
	 * They should fit inside the panel indicated (given height and current panel width)
	 * 
	 * @param large
	 * @param small
	 * @param startY
	 * @param height
	 * @param g
	 */
	private void printSection(String[] large, String[] small, int startY, int height, @Nullable Graphics g) {
		if (g == null) return;

		int width = this.getWidth();

		String lLong = getLongest(large);
		String sLong = getLongest(small);

		FontMetrics lFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize(lLong, (Graphics2D) g, width)));
		FontMetrics sFont = g.getFontMetrics(new Font("Serif", Font.PLAIN, getFontSize(sLong, (Graphics2D) g, width)));

		Font[] fonts = fitToScreen(Null.nonNull(lFont.getFont()), Null.nonNull(sFont.getFont()), small.length, sLong, large.length, lLong, (Graphics2D)g, height);
		
		lFont = g.getFontMetrics(fonts[1]);
		Rectangle2D lRect = lFont.getStringBounds(lLong, g);
		sFont = g.getFontMetrics(fonts[0]);
		Rectangle2D sRect = sFont.getStringBounds(sLong, g);
		
		int fontHeight = fontHeight(lFont, Null.nonNull(lRect), sFont, Null.nonNull(sRect), small.length, large.length);
		int y = startY + (height / 2) - (fontHeight / 2);
		int yOffset = (int)(lRect.getHeight() * large.length + (lFont.getAscent() / 4));
		g.setColor(Color.black);
		
		g.setFont(lFont.getFont());
		for(int i = 0; i < large.length; i++) {
			lRect = lFont.getStringBounds(large[i], g);
			int x = (int)((width - lRect.getWidth()) / 2);
			int localY = y + (int)(lRect.getHeight() * (i + 1)) - (lFont.getAscent() / 4);
			g.drawString(large[i], x, localY);
		}
		g.setFont(sFont.getFont());
		y = y + yOffset;
		for(int i = 0; i < small.length; i++) {
			sRect = sFont.getStringBounds(small[i], g);
			g.drawString(small[i], (int)((width - sRect.getWidth()) / 2), y + (int)sRect.getHeight() + (((int)sRect.getHeight() + (sFont.getAscent() / 4)) * i));
		}
	}

	/**
	 * The total height of the combined strings based on the StringBounds and FontMetrics
	 * 
	 * @param large FontMetrics of the larger font
	 * @param lRect StringBounds of "WARNING" in the larger font
	 * @param small FontMetrics of the smaller font
	 * @param sRect StringBounds of longest in the smaller font
	 * @param sNo Number of lines to be displayed in the smaller font
	 * @param lNo Number of lines to be displayed in the larger font
	 * @return The total height of the lines in various fonts.
	 */
	private static int fontHeight(FontMetrics large, Rectangle2D lRect, FontMetrics small, Rectangle2D sRect, int sNo, int lNo) {
		int fontHeight = (int)(lRect.getHeight() * lNo + (large.getAscent() / 4) 
				+ (sRect.getHeight() * sNo) + ((small.getAscent() / 4) * (sNo - 1)));
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
		if (font <= 0) {
			font = 1;
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
	 * @param lFont Larger Font
	 * @param sFont Smaller Font
	 * @param sNo Number of Lines in the Smaller Font
	 * @param sLong Longest line to be displayed in the smaller font
	 * @param lNo Number of Lines in the Larger Font
	 * @param lLong Longest line to be displayed in the smaller font
	 * @param g 
	 * @param height Height of the screen or rectangle to display lines in
	 * @return [new small font, new large font]
	 */
	private static Font[] fitToScreen(Font lFont, Font sFont, int sNo, String sLong, int lNo, String lLong, Graphics2D g, int height) {
		Font small = sFont;
		Font large = lFont;
		FontMetrics fmS = g.getFontMetrics(small);
		FontMetrics fmL = g.getFontMetrics(large);
		Rectangle2D rectS = fmS.getStringBounds(sLong, g);
		Rectangle2D rectL = fmL.getStringBounds(lLong, g);
		int ratio = large.getSize() / small.getSize();
		int i = 0;
		while (height - 10 < fontHeight(fmL, Null.nonNull(rectL), fmS, Null.nonNull(rectS), sNo, lNo)) {
			if (i % ratio == 0) {
				small = new Font(small.getFontName(), small.getStyle(), small.getSize() - 1);
				fmS = g.getFontMetrics(small);
			}
			large = new Font(large.getFontName(), large.getStyle(), large.getSize() - 1);
			fmL = g.getFontMetrics(large);
			rectS = fmS.getStringBounds(sLong, g);
			rectL = fmL.getStringBounds("WARNING", g);
			i++;
		}
		Font[] fonts = {small, large};
		return fonts;
	}


}
