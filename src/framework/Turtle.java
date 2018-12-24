package framework;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Turtle {
	private static final double PI = 3.1415;
	private static final double TWO_PI = 2.0 * PI;
	
	// state
	private TurtleState currentState = new TurtleState();
	private LinkedList<TurtleState> stateStack = new LinkedList<TurtleState>();
	
	// fixed variables
	private Graphics graphics;
	private double minimumDist;
	
	public Turtle(Graphics g, double xInit, double yInit, double minDist) {
		graphics = g;
		currentState.xPos = xInit;
		currentState.yPos = yInit;
		minimumDist = minDist;
	}
	
	// getters
	
	public Color getColor() {
		return currentState.color;
	}
	
	// drawing methods
	
	public void drawAndAdvance() {
		double xPosInit = currentState.xPos;
		double yPosInit = currentState.yPos;
		
		//graphics.setColor(Color.GREEN);
		//graphics.fillRect((int)xPosInit - 1, (int)yPosInit - 1,
		//				  3, 3);
		
		advance();
		
		graphics.setColor(currentState.color);
		graphics.drawLine((int)xPosInit, (int)yPosInit, 
						  (int)currentState.xPos, (int)currentState.yPos);
	}
	
	// methods to alter the state of the turtle
	
	public void scaleWidth(double scaleFactor) {
		currentState.lineWidth *= scaleFactor;
	}
	
	public void reset() {
		currentState = new TurtleState();
		stateStack.clear();
	}
	
	public void pushState() {
		stateStack.push(new TurtleState(currentState));
	}
	
	public void popState() {
		currentState = stateStack.pop();
	}
	
	public void advance() {
		double xDisp = Math.cos(currentState.facing) * minimumDist * currentState.distMultiple;
		double yDisp = Math.sin(currentState.facing) * minimumDist * currentState.distMultiple;
		
		currentState.xPos += xDisp;
		currentState.yPos += yDisp;
	}
	
	public void rotate(double degrees) {
		double rads = (degrees / 180.0) * PI;
		
		currentState.facing += rads;
		while (currentState.facing > TWO_PI) {
			currentState.facing -= TWO_PI;
		}
		while (currentState.facing < 0) {
			currentState.facing += TWO_PI;
		}
	}
	
	public void setColor(Color c) {
		currentState.color = c;
	}
}
