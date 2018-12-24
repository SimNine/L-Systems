package system;

import framework.Turtle;

public class LSystemOperator {
	
	public boolean pushState = false;
	public boolean popState = false;
	public double rotateDegrees = 0.0;
	public boolean move = false;
	public boolean draw = false;
	
	public LSystemOperator() {}
	
	public LSystemOperator(boolean pushState, boolean popState,
						   double rotateDegrees,
						   boolean move, boolean draw) {
		this.pushState = pushState;
		this.popState = popState;
		this.rotateDegrees = rotateDegrees;
		this.move = move;
		this.draw = draw;
	}

	public void affectTurtle(Turtle t) {
		if (pushState)
			t.pushState(); 
		
		if (popState)
			t.popState();
		
		if (rotateDegrees != 0.0)
			t.rotate(rotateDegrees);
		
		if (move) {
			if (draw)
				t.drawAndAdvance();
			else
				t.advance();
		}
	}
	
}
