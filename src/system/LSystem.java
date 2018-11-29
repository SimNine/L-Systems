package system;

import java.util.HashMap;

import framework.Turtle;

public class LSystem {
	String axiom = "";
	public HashMap<Character, LSystemProducer> productionRules = new HashMap<Character, LSystemProducer>();
	public HashMap<Character, LSystemOperator> interpretationRules = new HashMap<Character, LSystemOperator>();
	
	public LSystem() {
		setPreset(LSystemPreset.SIERPINSKI);
	}
	
	public LSystem(LSystemPreset preset) {
		setPreset(preset);
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
	
	private void addSimpleProduction(char c, String prod) {
		productionRules.put(c, new LSystemProducer() {
			public String produceString() {
				return prod;
			}
		});
	}
	
	public void setPreset(LSystemPreset preset) {
		// clear existing system
		axiom = "";
		productionRules.clear();
		interpretationRules.clear();
		
		switch (preset) {
		case ALGAE:
			axiom = "A";
			addSimpleProduction('A', "AB");
			addSimpleProduction('B', "A");
			// there are no production rules for this system
			break;
		case BINARY_TREE:
			axiom = "0";
			addSimpleProduction('0', "1[0]0");
			addSimpleProduction('1', "11");
			interpretationRules.put('0', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('1', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('[', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.pushState();
					t.rotate(45);
				}
			});
			interpretationRules.put(']', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.popState();
					t.rotate(-45);
				}
			});
			break;
		case CANTOR_SET:
			axiom = "A";
			addSimpleProduction('A', "ABA");
			addSimpleProduction('B', "BBB");
			interpretationRules.put('A', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('B', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.advance();
				}
			});
			break;
		case KOCH_CURVE:
			axiom = "F";
			addSimpleProduction('F', "F+F-F-F+F");
			interpretationRules.put('F', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(90);
				}
			});
			interpretationRules.put('-', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.pushState();
					t.rotate(-90);
				}
			});
			break;
		case KOCH_SNOWFLAKE:
			axiom = "F+F+F+F";
			addSimpleProduction('F', "F+F-F-FF+F+F-F");
			interpretationRules.put('F', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(90);
				}
			});
			interpretationRules.put('-', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.pushState();
					t.rotate(-90);
				}
			});
			break;
		case SIERPINSKI:
			axiom = "F-G-G";
			addSimpleProduction('F', "F-G+F+G-F");
			addSimpleProduction('G', "GG");
			interpretationRules.put('F', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('G', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(120);
				}
			});
			interpretationRules.put('-', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(-120);
				}
			});
			break;
		case DRAGON_CURVE:
			axiom = "FX";
			addSimpleProduction('X', "X+YF+");
			addSimpleProduction('Y', "-FX-Y");
			interpretationRules.put('F', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('-', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(90);
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(-90);
				}
			});
			break;
		case FRACTAL_PLANT:
			axiom = "X";
			addSimpleProduction('X', "F+[[X]-X]-F[-FX]+X");
			addSimpleProduction('F', "FF");
			interpretationRules.put('F', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.drawAndAdvance();
				}
			});
			interpretationRules.put('-', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(25);
				}
			});
			interpretationRules.put('+', new LSystemOperator() {
				public void affectTurtle(Turtle t) {
					t.rotate(-25);
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
			break;
		case RIVER_LONG:
			axiom = "S";
			productionRules.put('S', new LSystemProducer() {
				public String produceString() {
					if (Math.random() > 0.9) {
						return "S"; //TODO
					} else {
						return "SS";
					}
				}
			});
			break;
		case RIVER_BRANCHING:
			break;
		case CUSTOM:
			axiom = "C+C+C+C";
			addSimpleProduction('C', "[AC+C+B+C]");
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
