package framework;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import system.LSystem;
import system.LSystemPreset;

public class LSystemMain implements Runnable {
    private static String version = "0.1.0";
    private static String appName = "L-System Tester";
    
    public static LSystem system = new LSystem(LSystemPreset.SIERPINSKI);
    public static LSystemPanel panel = null;
    
	public void run() {
        System.out.println();
	    System.out.println("---------------------");
	    System.out.println(appName + " " + version);
        System.out.println("---------------------");
        System.out.println();
	    
        // create the frame and panel
		final JFrame frame = new JFrame(appName + " " + version);
       	panel = new LSystemPanel();
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(800, 700);
        frame.setLocation(50, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	panel.setSize(frame.getWidth(), frame.getHeight());
            }
        });
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new LSystemMain());
	}

}
