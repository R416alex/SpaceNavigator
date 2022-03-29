package main;
import java.util.concurrent.Future;
import com.mathworks.engine.MatlabEngine;


public class APIuser {
	private Object[] OE1;
	private Object[] OE2;
	private	int n = 5; //number of outputs (don't change)
	private double[] output1 = new double[n];
	private	double[] output2 = new double[n];
	
	public void PlanetOrbit(int planet1, int planet2) throws Exception {
		//public static void main(String[] args) throws Exception{
			
			
			
        	Future<MatlabEngine> engine = MatlabEngine.startMatlabAsync();
        	MatlabEngine eng = engine.get();
        	
        	eng.eval("cd 'C:\\Users\\josep\\Desktop\\SE 300\\Matlab_code'");
        	OE1 = eng.feval(n, "planetOrbit", planet1);
        	OE2 = eng.feval(n, "planetOrbit", planet2);
        	
        	for (int i = 0; i < n; i++) {
        		output1[i] = (Double) OE1[i];
        		output2[i] = (Double) OE2[i];
        		
        	}
        	
        	eng.close();
		}

	public double[] getOutput1() {
		return output1;
	}

	public void setOutput1(double[] output1) {
		this.output1 = output1;
	}

	public double[] getOutput2() {
		return output2;
	}

	public void setOutput2(double[] output2) {
		this.output2 = output2;
	}
		
}
