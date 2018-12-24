package framework;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import system.LSystem;
import system.LSystemOperator;
import system.LSystemPreset;
import system.LSystemProducer;

public class LSystemMain implements Runnable {
    private static String appName = "L-System";
    
    public static LSystem system = new LSystem(LSystemPreset.SIERPINSKI);
    public static LSystemPanel displayPanel = null;
    
    public static JPanel controlPanel = null;
    public static JPanel productionRuleList = null;
    public static JScrollPane productionRulePane = null;
    public static JPanel interpretationRuleList = null;
    public static JScrollPane interpretationRulePane = null;
    public static JTextField axiomField = null;
    
	public void run() {
        System.out.println();
	    System.out.println("---------------------");
	    System.out.println(appName);
        System.out.println("---------------------");
        System.out.println();
	    
        // create the display frame and panel
		final JFrame displayFrame = new JFrame(appName + " display");
       	displayPanel = new LSystemPanel();
        displayFrame.setLayout(new BorderLayout());
        displayFrame.add(displayPanel, BorderLayout.CENTER);
		displayFrame.pack();
		displayFrame.setSize(800, 700);
        displayFrame.setLocation(50, 50);
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setVisible(true);
		displayFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	displayPanel.setSize(displayFrame.getWidth(), displayFrame.getHeight());
            }
        });
		
        // create the control frame and panel
		final JFrame controlFrame = new JFrame(appName + " controls");
       	controlPanel = new JPanel();
       	controlFrame.setLayout(new BorderLayout());
       	controlFrame.add(controlPanel, BorderLayout.CENTER);
       	controlFrame.pack();
       	controlFrame.setSize(550, 500);
       	controlFrame.setLocation(50, 50);
       	controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setVisible(true);
		controlFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	controlPanel.setSize(controlFrame.getWidth(), controlFrame.getHeight());
            }
        });
		buildControlPanel(controlPanel);
		
		system.setPreset(LSystemPreset.SIERPINSKI);
		refreshRules();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new LSystemMain());
	}
	
	public static void buildControlPanel(JPanel panel) {
	    productionRuleList = new JPanel();
	    productionRuleList.setLayout(new BoxLayout(productionRuleList, BoxLayout.PAGE_AXIS));
	    interpretationRuleList = new JPanel();
	    interpretationRuleList.setLayout(new BoxLayout(interpretationRuleList, BoxLayout.PAGE_AXIS));
	    
	    JPanel settingsPanel = new JPanel();
	    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
	    
	    JLabel axiomLabel = new JLabel("Axiom:");
	    settingsPanel.add(axiomLabel);
	    
	    axiomField = new JTextField();
	    settingsPanel.add(axiomField);
	    
	    addSpacer(settingsPanel);
	    
	    JButton newProductionRuleButton = new JButton("New production rule");
	    newProductionRuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProductionRule();
			}
	    });
	    settingsPanel.add(newProductionRuleButton);
	    
	    addSpacer(settingsPanel);
	    
	    JButton newInterpretationRuleButton = new JButton("New interpretation rule");
	    newInterpretationRuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInterpretationRule();
			}
	    });
	    settingsPanel.add(newInterpretationRuleButton);
	    
	    addSpacer(settingsPanel);
	    
	    JButton applyRulesButton = new JButton("Apply rules");
	    applyRulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyRules();
			}
	    });
	    settingsPanel.add(applyRulesButton);
	    
	    addSpacer(settingsPanel);
	    
	    JButton refreshRulesButton = new JButton("Refresh rules");
	    refreshRulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshRules();
			}
	    });
	    settingsPanel.add(refreshRulesButton);
	    
	    addSpacer(settingsPanel);
	    
	    JButton clearRulesButton = new JButton("Clear rules");
	    clearRulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productionRuleList.removeAll();
			    productionRuleList.setPreferredSize(new Dimension(100, 0));
				productionRuleList.validate();
				productionRuleList.repaint();
				interpretationRuleList.removeAll();
				interpretationRuleList.setPreferredSize(new Dimension(100, 0));
				interpretationRuleList.validate();
				interpretationRuleList.repaint();
				applyRules();
			}
	    });
	    settingsPanel.add(clearRulesButton);
	    
	    panel.add(settingsPanel);
	    
	    productionRulePane = new JScrollPane(productionRuleList);
	    productionRulePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    productionRulePane.setPreferredSize(new Dimension(150, 400));
	    panel.add(productionRulePane);
	    
	    interpretationRulePane = new JScrollPane(interpretationRuleList);
	    interpretationRulePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    interpretationRulePane.setPreferredSize(new Dimension(150, 400));
	    panel.add(interpretationRulePane);
	}
	
	public static void addSpacer(JPanel panel) {
	    JPanel space = new JPanel();
	    panel.add(space);
	}
	
	public static void addProductionRule() {
		ProductionRulePanel rule = new ProductionRulePanel();
		
	    productionRuleList.add(rule);
	    productionRuleList.setPreferredSize(new Dimension(100, 80 * productionRuleList.getComponentCount()));
	    productionRuleList.validate();
	    productionRuleList.repaint();
	}
	
	public static void addInterpretationRule() {
		InterpretationRulePanel rule = new InterpretationRulePanel();
		
	    interpretationRuleList.add(rule);
	    interpretationRuleList.setPreferredSize(new Dimension(100, 180 * interpretationRuleList.getComponentCount()));
	    interpretationRuleList.validate();
	    interpretationRuleList.repaint();
	}
	
	public static void refreshRules() {
		axiomField.setText(system.axiom);
		
		// rebuild production rule list
		productionRuleList.removeAll();

		for (Entry<Character, LSystemProducer> lp : system.productionRules.entrySet()) {
			ProductionRulePanel p = new ProductionRulePanel();
			p.input.setText(lp.getKey() + "");
			p.output.setText(lp.getValue().produceString());
			
			productionRuleList.add(p);
		    productionRuleList.setPreferredSize(new Dimension(100, 80 * productionRuleList.getComponentCount()));
		}
		
		productionRulePane.validate();
		productionRulePane.repaint();
		
		// rebuild interpretation rule list
		interpretationRuleList.removeAll();

		for (Entry<Character, LSystemOperator> lp : system.interpretationRules.entrySet()) {
			InterpretationRulePanel p = new InterpretationRulePanel();
			p.input.setText(lp.getKey() + "");
			p.pushState.setSelected(lp.getValue().pushState);
			p.popState.setSelected(lp.getValue().popState);
			p.draw.setSelected(lp.getValue().draw);
			p.move.setSelected(lp.getValue().move);
			p.angle.setValue(lp.getValue().rotateDegrees);
			
			interpretationRuleList.add(p);
			interpretationRuleList.setPreferredSize(new Dimension(100, 180 * interpretationRuleList.getComponentCount()));
		}
		
		interpretationRulePane.validate();
		interpretationRulePane.repaint();
	}
	
	public static void applyRules() {
		system.axiom = axiomField.getText();
		
		// apply production rules
		system.productionRules.clear();
		for (Component c : productionRuleList.getComponents()) {
			ProductionRulePanel p = (ProductionRulePanel) c;
			final String input = p.input.getText();
			final String output = p.output.getText();
			
			system.productionRules.put(input.charAt(0), new LSystemProducer(output));
		}
		
		// apply interpretation rules
		system.interpretationRules.clear();
		for (Component c : interpretationRuleList.getComponents()) {
			InterpretationRulePanel p = (InterpretationRulePanel) c;
			double angleValue = Double.parseDouble(p.angle.getValue() + "");
			
			system.interpretationRules.put(p.input.getText().charAt(0), 
										   new LSystemOperator(
												   p.pushState.isSelected(),
												   p.popState.isSelected(),
												   angleValue,
												   p.move.isSelected(),
												   p.draw.isSelected()
												   )
										   );
		}
		
		displayPanel.repaint();
	}
}
