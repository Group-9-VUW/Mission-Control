package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * Panel that holds the ARM button.
 * 
 * @author Bryony
 *
 */
public class ArmedButtonPanel extends JPanel{

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 4874852085758750226L;

	/**
	 * The Observable that speaks to Parent.
	 */
	ViewObservable obs;

	/**
	 * Creates the panel that holds the arm button.
	 * 
	 * @param o
	 */
	public ArmedButtonPanel(Observer o) {
		this.obs = new ViewObservable(o);
		setPreferredSize(new Dimension(100, 300));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    this.setLayout(new GridBagLayout());
    this.add(armButton());
	}

	@Override
	protected void paintComponent(@Nullable Graphics g) {
		Null.nonNull(g).setColor(Color.magenta);
		Null.nonNull(g).fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Returns a Rounded ARM button.
	 * 
	 * @return A Rounded Button
	 */
	private JButton armButton() {
		RoundButton arm = new RoundButton("ARM");
		arm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String[] args = {"switch", "enterDetails"};
				ArmedButtonPanel.this.obs.notify(args);
			}
		});
		arm.setBackground(Color.red);
		return arm;
	}

}
