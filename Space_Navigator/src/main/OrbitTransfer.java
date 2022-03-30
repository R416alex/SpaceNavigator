 package main;
//File: Orbit Transfer algorithm
//Author: Joseph Anderson
//Date Modified: 2/14/2022

import java.util.concurrent.Future;

import com.mathworks.engine.MatlabEngine;

public class OrbitTransfer {
	private Object[] ans = new Object[8];
	private double[] oe = new double[7];
	private double[] output = new double[8];
	private double year, month, day, hour, minute, second;
	private double V1, V2;
	
	
        public void HohmanTansfer (String planet1, String date1, String time1, String planet2, String date2, String time2 ) throws Exception{
        	
        	
        	Future<MatlabEngine> engine = MatlabEngine.startMatlabAsync();
        	MatlabEngine eng = engine.get();
        	//execute calculation package
        	eng.eval("cd 'C:\\Users\\josep\\Desktop\\SE 300\\Calculation Package'");
          //  ans = eng.feval(8, "TestAlg", );
            
        	
            
            eng.close();
        }

        public void Lambert(double[] R1,  double [] R2, double time) throws Exception{
        	Future<MatlabEngine> engine = MatlabEngine.startMatlabAsync();
        	MatlabEngine eng = engine.get();
        	Object[] V = new Object[2];
        	
        	eng.eval("cd 'C:\\Users\\josep\\Desktop\\SE 300\\Matlab_code'");
        	V = eng.feval(2,"Lambert", R1, R2, time);
        	V1 = (double) V[0];
        	V2 = (double) V[1];
        	System.out.println(V1);
        	System.out.println(V2);
        	eng.close();
        }

		public void BiEllipticTransfer(double Position1, double Position2, double Time){

        }



		public Object[] getAns() {
			return ans;
		}



		public void setAns(Object[] ans) {
			this.ans = ans;
		}



		public double[] getOe() {
			return oe;
		}



		public void setOe(double[] oe) {
			this.oe = oe;
		}

		public void HohmanTansfer(double[] testR, double[] testV, double mu, double[] testDay, double[] testTime) {
			// TODO Auto-generated method stub
			
		}



		
        }