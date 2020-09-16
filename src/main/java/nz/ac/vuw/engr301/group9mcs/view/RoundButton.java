package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * A ROUND JBUTTON!!!!!
 * 
 * @author Bryony
 *
 */
public class RoundButton extends JButton{

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 2480398818608686395L;

	/**
	 * Creates a Round Button!
	 * 
	 * @param label
	 */
	public RoundButton(String label) {
    super(label);
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,size.height);
    setPreferredSize(size);
    this.setBorder(new RoundedBorder(10));

    setContentAreaFilled(false);
  }

  @Override
	protected void paintComponent(@Nullable Graphics g) {
    if (getModel().isArmed()) {
      Null.nonNull(g).setColor(Color.lightGray);
    } else {
    	Null.nonNull(g).setColor(getBackground());
    }
    Null.nonNull(g).fillOval(0, 0, getSize().width-1, getSize().height-1);

    super.paintComponent(g);
  }

  @Override
	protected void paintBorder(@Nullable Graphics g) {
  	Null.nonNull(g).setColor(getForeground());
  	Null.nonNull(g).drawOval(0, 0, getSize().width-1, getSize().height-1);
  }

  /**
   * Shape of the button. Used in contained method.
   */
  @Nullable Shape shape;
  
  @Override
	public boolean contains(int x, int y) {
    if (this.shape == null || !Null.nonNull(this.shape).getBounds().equals(getBounds())) {
      this.shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return Null.nonNull(this.shape).contains(x, y);
  }
  
}
