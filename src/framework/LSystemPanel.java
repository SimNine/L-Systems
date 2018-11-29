package framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;

import system.LSystemOperator;
import system.LSystemPreset;

@SuppressWarnings("serial")
public class LSystemPanel extends JPanel {
	
	private double width;
	private double height;
	private int tempXdisp = 0;
	private int tempYdisp = 0;
	private int Xdisp = 0;
	private int Ydisp = 0;
	private double scale = 0;
	
	private int numIterations = 3;
	private LSystemPreset currPreset = LSystemPreset.SIERPINSKI;
	private long currSeed = 0;
	
	public LSystemPanel() {
		super();
		setFocusable(true);
		requestFocusInWindow();
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				Xdisp += tempXdisp - e.getX();
				Ydisp += tempYdisp - e.getY();
				tempXdisp = e.getX();
				tempYdisp = e.getY();
				repaint();
			}
			public void mouseMoved(MouseEvent e) {}
		});
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				tempXdisp = e.getX();
				tempYdisp = e.getY();
			}
			public void mouseReleased(MouseEvent e) {}
		});
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					scale += 0.5;
				} else if (e.getKeyCode() == KeyEvent.VK_S && scale > 0) {
					scale -= 0.5;
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					numIterations++;
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					numIterations--;
					if (numIterations < 0)
						numIterations = 0;
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					currPreset = currPreset.next();
					LSystemMain.system.setPreset(currPreset);
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					currSeed = (long)(Math.random() * Long.MAX_VALUE);
					LSystemMain.system.setSeed(currSeed);
				}
				repaint();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
	}
		
	public void paintComponent(Graphics g) {
		// clear the screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)width, (int)height);
		
		// print debug text
		int ln = 1;
		g.setColor(Color.BLACK);
		g.drawString("Preset: " + currPreset.toString(), 10, 15*ln++);
		g.drawString("Num iterations: " + numIterations, 10, 15*ln++);
		
		// create the turtle, get the current interpretation and get the system rules
		double xCenter = (width / 2) - Xdisp;
		double yCenter = (height / 2) - Ydisp;
		double minLength = (10.0 / numIterations) + scale;
		Turtle t = new Turtle(g, xCenter, yCenter, minLength);
		LSystemMain.system.setSeed(currSeed);
		String interpretation = LSystemMain.system.getNthIteration(numIterations);
		g.drawString("String: " + interpretation, 10, 15*ln++);
		g.drawString("Seed: " + currSeed, 10, 15*ln++);
		HashMap<Character, LSystemOperator> ops = LSystemMain.system.interpretationRules;
		
		// act on the interpretation
		for (int i = 0; i < interpretation.length(); i++) {
			Character currChar = interpretation.charAt(i);
			LSystemOperator op = ops.get(currChar);
			if (op != null)
				op.affectTurtle(t);
		}
		
		/*
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)width, (int)height);
		g.setColor(Color.RED);
		g.drawLine((int)width/2 - Xdisp, 0, (int)width/2 - Xdisp, (int)height);
		g.setColor(Color.BLUE);
		g.drawLine(0, (int)height/2 - Ydisp, (int)width, (int)height/2 - Ydisp);
		*/
	}
	
	public void setSize(int w, int h) {
		super.setSize(w, h);
		width = getWidth();
		height = getHeight();
		repaint();
	}
}