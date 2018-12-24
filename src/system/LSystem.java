package system;

import java.util.HashMap;
import java.util.Random;

import framework.Turtle;

public class LSystem {
	public String axiom = "";
	public HashMap<Character, LSystemProducer> productionRules = new HashMap<Character, LSystemProducer>();
	public HashMap<Character, LSystemOperator> interpretationRules = new HashMap<Character, LSystemOperator>();
	private Random r = new Random();
	
	public LSystem() {
		setPreset(LSystemPreset.SIERPINSKI);
		r.setSeed(0);
	}
	
	public LSystem(LSystemPreset preset) {
		setPreset(preset);
		r.setSeed(0);
	}
	
	public String getNthIteration(int numIterations) {
		// set the initially current string
		String currString = axiom;
		
		// do the specified number of transforms
		for (int i = 0; i < numIterations; i++) {
			String resultString = "";
			
			// for each character in the current string, get its transform
			for (int c = 0; c < currString.length(); c++) {
				Character currChar = currString.charAt(c);
				LSystemProducer production = productionRules.get(currChar);
				if (production == null)
					resultString += currChar;
				else
					resultString += production.produceString();
			}
			
			// set the working string to the current string
			currString = resultString;
		}
		
		// return the final string
		return currString;
	}
	
	public void setSeed(long seed) {
		r = new Random();
		r.setSeed(seed);
	}
	
	public void setPreset(LSystemPreset preset) {
		// clear existing system
		axiom = "";
		productionRules.clear();
		interpretationRules.clear();
		
		switch (preset) {
		case ALGAE:
			axiom = "A";
			productionRules.put('A', new LSystemProducer("AB"));
			productionRules.put('B', new LSystemProducer("A"));
			// there are no production rules for this system
			break;
		case BINARY_TREE:
			axiom = "0";
			productionRules.put('0', new LSystemProducer("1[0]0"));
			productionRules.put('1', new LSystemProducer("11"));
			interpretationRules.put('0', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('1', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('[', new LSystemOperator(true, false, 45, false, false));
			interpretationRules.put(']', new LSystemOperator(false, true, -45, false, false));
			break;
		case CANTOR_SET:
			axiom = "A";
			productionRules.put('A', new LSystemProducer("ABA"));
			productionRules.put('B', new LSystemProducer("BBB"));
			interpretationRules.put('A', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('B', new LSystemOperator(false, false, 0, true, false));
			break;
		case KOCH_CURVE:
			axiom = "F";
			productionRules.put('F', new LSystemProducer("F+F-F-F+F"));
			interpretationRules.put('F', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('+', new LSystemOperator(false, false, 90, false, false));
			interpretationRules.put('-', new LSystemOperator(false, false, -90, false, false));
			break;
		case KOCH_SNOWFLAKE:
			axiom = "F+F+F+F";
			productionRules.put('F', new LSystemProducer("F+F-F-FF+F+F-F"));
			interpretationRules.put('F', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('+', new LSystemOperator(false, false, 90, false, false));
			interpretationRules.put('-', new LSystemOperator(false, false, -90, false, false));
			break;
		case SIERPINSKI:
			axiom = "F-G-G";
			productionRules.put('F', new LSystemProducer("F-G+F+G-F"));
			productionRules.put('G', new LSystemProducer("GG"));
			interpretationRules.put('F', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('G', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('+', new LSystemOperator(false, false, 120, false, false));
			interpretationRules.put('-', new LSystemOperator(false, false, -120, false, false));
			break;
		case DRAGON_CURVE:
			axiom = "FX";
			productionRules.put('X', new LSystemProducer("X+YF+"));
			productionRules.put('Y', new LSystemProducer("-FX-Y"));
			interpretationRules.put('F', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('-', new LSystemOperator(false, false, 90, false, false));
			interpretationRules.put('+', new LSystemOperator(false, false, -90, false, false));
			break;
		case FRACTAL_PLANT:
			axiom = "X";
			productionRules.put('X', new LSystemProducer("F+[[X]-X]-F[-FX]+X"));
			productionRules.put('F', new LSystemProducer("FF"));
			interpretationRules.put('F', new LSystemOperator(false, false, 0, true, true));
			interpretationRules.put('-', new LSystemOperator(false, false, 25, false, false));
			interpretationRules.put('+', new LSystemOperator(false, false, -25, false, false));
			interpretationRules.put('[', new LSystemOperator(true, false, 0, false, false));
			interpretationRules.put(']', new LSystemOperator(false, true, 0, false, false));
			break;
		case RIVER_LONG:
			axiom = "[SB]TSB";
			
			productionRules.put('S', new LSystemProducer() { // stream
				public String produceString() {
					double rand = r.nextDouble();
					if (rand > 0.9) { // turn
						if (r.nextDouble() > 0.5)
							return "RS";
						else
							return "LS";
					} else { // continue straight
						return "SS";
					}
				}
			});
			productionRules.put('B', new LSystemProducer() { // branch
				public String produceString() {
					double rand = r.nextDouble();
					if (rand > 0.8) { // fork
						if (r.nextDouble() > 0.5) { // parent goes right
							return "S[PRSB]CLSB";
						} else { // parent goes left
							return "S[CRSB]PLSB";
						}
					} else { // continue straight
						return "SB";
					}
				}
			});

			interpretationRules.put('S', new LSystemOperator(false, false, 0, true, true)); // stream
			interpretationRules.put('[', new LSystemOperator(true, false, 0, false, false)); // push state
			interpretationRules.put('0', new LSystemOperator(false, true, 0, false, false)); // pop state
			interpretationRules.put('L', new LSystemOperator() { // turn left
				public void affectTurtle(Turtle t) {
					double rotateAngle = r.nextDouble() * 10.0;
					t.rotate(-rotateAngle);
				}
			});
			interpretationRules.put('R', new LSystemOperator() { // turn right
				public void affectTurtle(Turtle t) {
					double rotateAngle = r.nextDouble() * 10.0;
					t.rotate(rotateAngle);
				}
			});
			interpretationRules.put('C', new LSystemOperator() { // child branch
				public void affectTurtle(Turtle t) {
					t.scaleWidth(0.5);
				}
			});
			interpretationRules.put('P', new LSystemOperator() { // parent branch
				public void affectTurtle(Turtle t) {
					t.scaleWidth(0.8);
				}
			});
			interpretationRules.put('T', new LSystemOperator(false, false, 180, false, false));
			break;
		case RIVER_BRANCHING:
			axiom = "[SB]TSB";
			
			productionRules.put('S', new LSystemProducer() { // stream
				public String produceString() {
					if (r.nextDouble() > 0.7) { // turn
						if (r.nextDouble() > 0.5)
							return "RS";
						else
							return "LS";
					} else { // continue straight
						return "SS";
					}
				}
			});
			productionRules.put('B', new LSystemProducer() { // branch
				public String produceString() {
					if (r.nextDouble() > 0.2) { // fork
						if (r.nextDouble() > 0.5) { // parent goes right
							return "S[PRSB]CLSB";
						} else { // parent goes left
							return "S[CRSB]PLSB";
						}
					} else { // continue straight
						return "SB";
					}
				}
			});
			
			interpretationRules.put('S', new LSystemOperator() { // stream
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('[', new LSystemOperator() { // push state
				public void affectTurtle(Turtle t) {
					t.pushState();
				}
			});
			interpretationRules.put(']', new LSystemOperator() { // pop state
				public void affectTurtle(Turtle t) {
					t.popState();
				}
			});
			interpretationRules.put('L', new LSystemOperator() { // turn left
				public void affectTurtle(Turtle t) {
					double rotateAngle = r.nextDouble() * 10.0;
					t.rotate(-rotateAngle);
				}
			});
			interpretationRules.put('R', new LSystemOperator() { // turn right
				public void affectTurtle(Turtle t) {
					double rotateAngle = r.nextDouble() * 10.0;
					t.rotate(rotateAngle);
				}
			});
			interpretationRules.put('C', new LSystemOperator() { // child branch
				public void affectTurtle(Turtle t) {
					t.scaleWidth(0.5);
				}
			});
			interpretationRules.put('P', new LSystemOperator() { // parent branch
				public void affectTurtle(Turtle t) {
					t.scaleWidth(0.8);
				}
			});
			interpretationRules.put('T', new LSystemOperator() { // turn around
				public void affectTurtle(Turtle t) {
					t.rotate(180);
				}
			});
			break;
		case CUSTOM:
			axiom = "C+C+C+C";
			productionRules.put('X', new LSystemProducer("[AC+C+B+C]"));
			interpretationRules.put('C', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(90);
				}
			});
			interpretationRules.put('[', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.pushState();
				}
			});
			interpretationRules.put(']', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.popState();
				}
			});
			interpretationRules.put('A', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.advance();
				}
			});
			interpretationRules.put('B', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			break;
		default:
			break;
		}
	}
}
