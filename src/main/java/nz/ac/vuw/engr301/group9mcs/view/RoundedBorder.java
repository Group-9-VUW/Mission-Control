package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * Rounds the border of a component.
 * 
 * @author Bryony
 *
 */
public class RoundedBorder implements Border {

    /**
     * Radius of the rounding
     */
    private int radius;

    /**
     * Rounds the border of a button
     * 
     * @param radius
     */
    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
		public Insets getBorderInsets(@Nullable Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    @Override
		public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(@Nullable Component c, @Nullable Graphics g, int x, int y, int width, int height) {
        Null.nonNull(g).drawRoundRect(x, y, width-1, height-1, this.radius, this.radius);
    }
}
