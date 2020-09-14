package nz.ac.vuw.engr301.group9mcs.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.eclipse.jdt.annotation.Nullable;

/**
 * The Side Panel for SelectSiteView
 * 
 * @author Bryony
 *
 */
public class GoNoGoSidePanelSelectSite extends JPanel{

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1711226536063004701L;
	/**
	 * The Observable that speaks to Parent.
	 */
	ViewObservable obs;

	/**
	 * Creates and returns the Side Panel for Select Site View.
	 * 
	 * @param fileName
	 * @param lat
	 * @param lon
	 * @param o
	 */
	public GoNoGoSidePanelSelectSite(String fileName, double lat, double lon, Observer o) {
		this.obs = new ViewObservable(o);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.fill = GridBagConstraints.BOTH;

		JLabel para = new JLabel("Parameters", SwingConstants.CENTER);
		para.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel fileNameLabel = new JLabel("Rocket File: " + fileName, SwingConstants.CENTER);
		JLabel latLabel = new JLabel("Latitude: " + lat, SwingConstants.CENTER);
		JLabel longLabel = new JLabel("Longitude: " + lon, SwingConstants.CENTER);

		JLabel percen = new JLabel("50%", SwingConstants.CENTER);
		percen.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel percen2 = new JLabel("Probability of Landing Safely", SwingConstants.CENTER);
		JLabel percen3 = new JLabel("(Not on someone's head)", SwingConstants.CENTER);
		JLabel length = new JLabel("2-20m", SwingConstants.CENTER);
		length.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel length2 = new JLabel("Predicted Landing from Launch Site", SwingConstants.CENTER);

		gbc.weighty = 1;
		this.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.add(para, gbc);
		gbc.gridy++;
		this.add(fileNameLabel, gbc);
		gbc.gridy++;
		this.add(latLabel, gbc);
		gbc.gridy++;
		this.add(longLabel, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.add(percen, gbc);
		gbc.gridy++;
		this.add(percen2, gbc);
		gbc.gridy++;
		this.add(percen3, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		this.add(length, gbc);
		gbc.gridy++;
		this.add(length2, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;

		JButton change = new JButton("Change Launch Parameters");
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String[] args = {"change parameters"};
				GoNoGoSidePanelSelectSite.this.obs.notify(args);
			}
		});
		this.add(change, gbc);
		gbc.gridy++;

		JButton save = new JButton("Save and Quit");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@Nullable ActionEvent e) {
				String args = "save and quit";
				GoNoGoSidePanelSelectSite.this.obs.notify(args);
			}
		});
		this.add(save, gbc);
		gbc.gridy++;

		gbc.weighty = 1;
		this.add(new JPanel(), gbc);
		gbc.weighty = 0;
		gbc.gridy++;
	}

}
