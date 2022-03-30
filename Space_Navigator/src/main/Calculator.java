package main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

import javafx.geometry.Point3D;

public class Calculator {
	
	private Object[] ans1 = new Object[4];
	private double[] R = new double[3];
	private double[] V = new double[3];
	private double[] OE = new double[11];
	
	MatlabEngine engine;
	public Calculator() {
		engine = null;
		}
	
	public Point3D PlanetPosition(double planet_id, double year, double month, double day, double hour, double minute, double second, double option) throws Exception {
		
		
		
		ans1 = engine.feval(4, "planet_oe_and_sv", planet_id, year, month, day, hour, minute, second, option);
		OE = (double[]) ans1[0];
		R = (double[]) ans1[1];
		V = (double[]) ans1[2];
		
		
		return new Point3D(R[0], R[1], R[2]);
			
	}
	
	//public void Transer()
	
	public void initializeMATLAB() throws Exception {
		engine = MatlabEngine.startMatlab();
		engine.eval("cd 'res/MATLAB' ");
		
	}
	
	public void shutdown() throws Exception {
		if (engine != null) {
			engine.close();
		}
	}

	public double[] getR() {
		return R;
	}

	public void setR(double[] r) {
		R = r;
	}

	public double[] getV() {
		return V;
	}

	public void setV(double[] v) {
		V = v;
	}

	public double[] getOE() {
		return OE;
	}

	public void setOE(double[] oE) {
		OE = oE;
	}
	
	
}
