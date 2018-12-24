package system;

public class LSystemProducer {

	public String output;
	
	public LSystemProducer() {
		output = null;
	}
	
	public LSystemProducer(String out) {
		output = out;
	}
	
	public String produceString() {
		return output;
	}
	
}
