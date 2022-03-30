package main;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

import javafx.geometry.Point3D;

public class Calculator {
	
	private Object[] ans1 = new Object[4];
	private double[] R = new double[3];
	private double[] V = new double[3];
	private double[] OE = new double[11];
	
	//Transfer Algorithm Variables
	private double planetid1, planetid2;
	private double year1, year2;
	private double month1, month2;
	private double day1, day2;
	private double hour1, hour2;
	private double minute1, minute2;
	private double second1, second2;
	private double altitude = 600;
	//Transfer Algorithm Outputs
	private Object[] output = new Object[8];
	private double[] R1 = new double[3];
	private double[] R2 = new double[3];
	
	ArrayList<Point3D> stateVectors = new ArrayList<Point3D>();
	
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
	
	public ArrayList<Point3D> Trajectory(double[] startPlanet, double[] endPlanet) throws Exception {
		
		planetid1 = startPlanet[0];
		planetid2 = endPlanet[0];
		
		year1 = startPlanet[1];
		year2 = endPlanet[1];
		
		month1 = startPlanet[2];
		month2 = endPlanet[2];
		
		day1 = startPlanet[3];
		day2 = endPlanet[3];
		
		hour1 = startPlanet[4];
		hour2 = endPlanet[4];
		
		minute1 = startPlanet[5];
		minute2 = endPlanet[5];
		
		second1 = startPlanet[6];
		second2 = endPlanet[6];
		
		output = engine.feval(8,"TestAlg", planetid1, year1, month1, day1, hour1, minute1, second1, altitude, planetid2, year2, month2, day2, hour2, minute2, second2, altitude);
		R1 = (double[]) output[0];
		R2 = (double[]) output[3];
		stateVectors.add(new Point3D(R1[0], R1[1], R1[2]));
		stateVectors.add(new Point3D(R2[0], R2[1], R2[2]));
		
		
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

	public double getPlanetid1() {
		return planetid1;
	}

	public void setPlanetid1(double planetid1) {
		this.planetid1 = planetid1;
	}

	public double getPlanetid2() {
		return planetid2;
	}

	public void setPlanetid2(double planetid2) {
		this.planetid2 = planetid2;
	}

	public double getYear1() {
		return year1;
	}

	public void setYear1(double year1) {
		this.year1 = year1;
	}

	public double getYear2() {
		return year2;
	}

	public void setYear2(double year2) {
		this.year2 = year2;
	}

	public double getMonth1() {
		return month1;
	}

	public void setMonth1(double month1) {
		this.month1 = month1;
	}

	public double getMonth2() {
		return month2;
	}

	public void setMonth2(double month2) {
		this.month2 = month2;
	}

	public double getDay1() {
		return day1;
	}

	public void setDay1(double day1) {
		this.day1 = day1;
	}

	public double getDay2() {
		return day2;
	}

	public void setDay2(double day2) {
		this.day2 = day2;
	}

	public double getHour1() {
		return hour1;
	}

	public void setHour1(double hour1) {
		this.hour1 = hour1;
	}

	public double getHour2() {
		return hour2;
	}

	public void setHour2(double hour2) {
		this.hour2 = hour2;
	}

	public double getMinute1() {
		return minute1;
	}

	public void setMinute1(double minute1) {
		this.minute1 = minute1;
	}

	public double getMinute2() {
		return minute2;
	}

	public void setMinute2(double minute2) {
		this.minute2 = minute2;
	}

	public double getSecond1() {
		return second1;
	}

	public void setSecond1(double second1) {
		this.second1 = second1;
	}

	public double getSecond2() {
		return second2;
	}

	public void setSecond2(double second2) {
		this.second2 = second2;
	}
	
	
}
