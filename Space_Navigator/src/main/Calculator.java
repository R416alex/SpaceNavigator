package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

import org.fxyz3d.geometry.Point3D;

public class Calculator {
	

	public MatlabEngine engine;
	
	public Calculator() {
		engine = null;
		}
	
	public ArrayList<Point3D> planetPath(double planet_id, LocalDate start, LocalDate end){
		
		double[] date1 = {start.getYear(), start.getMonthValue(), start.getDayOfMonth(),0,0,0};
		double[] date2 = {end.getYear(), end.getMonthValue(), end.getDayOfMonth(),0,0,0};
		if(engine != null) {
			try {
//				Object[] ans1 = engine.feval(1, "planetPOS", planet_id, date1, date2);
//				double[] A = (double[]) ans1[0];
//				
//				Point3D point = new Point3D(A[0],A[1],A[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Point3D PlanetPosition(double planet_id, double year, double month, double day, double hour, double minute, double second, double option) throws Exception {
	
		Object[] ans1 = engine.feval(4, "planet_oe_and_sv", planet_id, year, month, day, hour, minute, second, option);
		double[] OE = (double[]) ans1[0];
		double[] R = (double[]) ans1[1];
		double[] V = (double[]) ans1[2];
		
		
		return new Point3D(R[0], R[1], R[2]);
			
	}
	

	
	
public List<Point3D> Trajectory(double planetid1,double planetid2,double year1,double year2,double month1,double month2,double day1,double day2,double hour1, double hour2, double minute1,double minute2,double second1,double second2, double altitude1, double altitude2, int option) throws Exception {
		
		Object[] output = engine.feval(9,"TestAlg", planetid1, year1, month1, day1, hour1, minute1, second1, altitude1, planetid2, year2, month2, day2, hour2, minute2, second2, altitude2);
	//	double[] R1 = (double[]) output[0];
	//	double[] R2 = (double[]) output[3];
	//	List<Point3D> stateVectors = new ArrayList<Point3D>();
	//	stateVectors.add(new Point3D(-R1[1]/50000, R1[2]/50000, R1[0]/50000));
//		stateVectors.add(new Point3D(-R2[1]/50000, R2[2]/50000, R2[0]/50000));
//		
//		double[] V1 = (double[]) output[1];
//		double[] V2 = (double[]) output[4];
//		
//		double[] Vsc1 = (double[]) output[2];
//		double[] Vsc2 = (double[]) output[5];
//		
//		double tof = (double) output[6];
//		double totalDeltaV = (double) output[7];
//		
		double[][] Rspacecraft = (double[][]) output[8];
		List<Point3D> stateVectors = new ArrayList<Point3D>();
<<<<<<< Upstream, based on origin/master
		for (double[] d : Rspacecraft) {
			stateVectors.add(new Point3D(d[0]/50000,d[2]/50000,d[1]/50000));
		//	System.out.println(stateVectors.get(stateVectors.size()));
		}
=======
		stateVectors.add(new Point3D(R1[0], R1[1], R1[2]));
		stateVectors.add(new Point3D(R2[0], R2[1], R2[2]));
		
		double[] V1 = (double[]) output[1];
		double[] V2 = (double[]) output[4];
		
		double[] Vsc1 = (double[]) output[2];
		double[] Vsc2 = (double[]) output[5];
		
		double tof = (double) output[6];
		double totalDeltaV = (double) output[7];
		
		double[][] Rspacecraft = (double[][]) output[9];
		
		
>>>>>>> 8f13741 Test Alg should show trajectory to end planet
		return stateVectors;
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
	
}
