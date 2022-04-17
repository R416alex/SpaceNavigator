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

	public ArrayList<Point3D> planetPath(double planet_id, LocalDate start, LocalDate end) {

		double[] date1 = { start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0 };
		double[] date2 = { end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 0, 0, 0 };
		if (engine != null) {
			try {
				Object[] ans1 = engine.feval(1, "planetPOS", planet_id, date1, date2);

				ArrayList<Point3D> path = new ArrayList<Point3D>();

				for (Object a : ans1) {
					double[] b = (double[]) a;
					Point3D point = new Point3D(b[0], b[1], b[2]);
					point = scale(point);
					path.add(point);
				}
				return path;
			} catch (Exception e) {

			}
		}
		return null;
	}

	public Point3D PlanetPosition(double planet_id, double year, double month, double day, double hour, double minute,
			double second, double option) throws Exception {

		Object[] ans1 = engine.feval(4, "planet_oe_and_sv", planet_id, year, month, day, hour, minute, second, option);
		double[] OE = (double[]) ans1[0];
		double[] R = (double[]) ans1[1];
		double[] V = (double[]) ans1[2];

		return scale(new Point3D(R[0], R[1], R[2]));

	}

	public Object[] Trajectory(double planetid1, double planetid2, double year1, double year2, double month1,
			double month2, double day1, double day2, double hour1, double hour2, double minute1, double minute2,
			double second1, double second2, double altitude1, double altitude2, int option) throws Exception {

		Object[] output = engine.feval(9, "TestAlg", planetid1, year1, month1, day1, hour1, minute1, second1, altitude1,
				planetid2, year2, month2, day2, hour2, minute2, second2, altitude2);
		double[][] Rspacecraft = (double[][]) output[8];
		List<Point3D> stateVectors = new ArrayList<Point3D>();
		for (double[] d : Rspacecraft) {
			stateVectors.add(scale(new Point3D(d[0], d[2], d[1])));
		}
		Object[] ans = { stateVectors, output[7], output[6] };
		return ans;
	}

	public Point3D scale(Point3D p) {
		double r = Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2) + Math.pow(p.getZ(), 2));
		double theta = Math.atan2(p.getY(), p.getX());
		double phi = Math.acos(p.getZ() / r);

		r = (Math.sqrt(r) / 5) + 500;

		double x = r * Math.cos(theta) * Math.sin(phi);
		double y = (r * Math.sin(theta) * Math.sin(phi));
		double z = (r * Math.cos(phi));

		return new Point3D(x, y, z);
	}

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
