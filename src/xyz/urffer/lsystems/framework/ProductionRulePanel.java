package xyz.urffer.lsystems.framework;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class ProductionRulePanel extends JPanel {
	
	public JTextField input;
	public JTextField output;

	public ProductionRulePanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(130, 80));
		this.setMaximumSize(new Dimension(130, 80));
		
		JLabel inputLabel = new JLabel("Input:");
		this.add(inputLabel);
		
		input = new JTextField();
		this.add(input);
		
		JLabel outputLabel = new JLabel("Output:");
		this.add(outputLabel);
		
		output = new JTextField();
		this.add(output);
		
		this.setBackground(new Color((int)(Math.random() * 50) + 200, (int)(Math.random() * 50) + 200, (int)(Math.random() * 50) + 200));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
}
