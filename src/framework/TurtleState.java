package framework;

import java.awt.Color;

public class TurtleState {
	// angle the turtle is facing
	double facing = 0;
	
	// current position of the turtle
	double xPos = 0;
	double yPos = 0;
	
	// scaling of the minimum distance to move
	double distMultiple = 1.0;
	
	// color of the line
	Color color = Color.BLACK;
	
	// width of the line (unused by this program)
	double lineWidth = 5;
	
	// default constructor
	public TurtleState() {
		// default
	}
	
	// copy constructor
	public TurtleState(TurtleState other) {
		this.facing = other.facing;
		this.xPos = other.xPos;
		this.yPos = other.yPos;
		this.distMultiple = other.distMultiple;
		this.color = other.color;
		this.lineWidth = other.lineWidth;
	}
}
