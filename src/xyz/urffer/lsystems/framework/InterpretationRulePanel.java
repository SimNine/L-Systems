package xyz.urffer.lsystems.framework;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

public class InterpretationRulePanel extends JPanel {
	
	public JCheckBox pushState = new JCheckBox("Push state");
	public JCheckBox popState = new JCheckBox("Pop state");
	public JCheckBox move = new JCheckBox("Move");
	public JCheckBox draw = new JCheckBox("Draw");
	public JSpinner angle;
	
	public JTextField input;

	public InterpretationRulePanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(130, 180));
		this.setMaximumSize(new Dimension(130, 180));
		
		JLabel inputLabel = new JLabel("Input:");
		this.add(inputLabel);
		
		input = new JTextField();
		input.setAlignmentX(-20);
		input.setPreferredSize(new Dimension(50, 20));
		input.setMinimumSize(new Dimension(50, 20));
		input.setMaximumSize(new Dimension(50, 20));
		this.add(input);
		
		this.add(pushState);
		this.add(popState);
		
		JLabel angleLabel = new JLabel("Angle:");
		this.add(angleLabel);
		
		SpinnerModel model = new SpinnerNumberModel(0, -180, 180, 1);
		angle = new JSpinner(model);
		angle.setAlignmentX(-20);
		angle.setMinimumSize(new Dimension(60, 20));
		angle.setMaximumSize(new Dimension(60, 20));
		angle.setPreferredSize(new Dimension(60, 20));
		this.add(angle);
		
		this.add(move);
		this.add(draw);
		
		this.setBackground(new Color((int)(Math.random() * 50) + 200, (int)(Math.random() * 50) + 200, (int)(Math.random() * 50) + 200));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
}
