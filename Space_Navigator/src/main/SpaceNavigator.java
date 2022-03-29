package main;

import java.io.StringWriter;

import java.util.concurrent.Future;

import com.mathworks.engine.MatlabEngine;

import javafx.application.Application;
import javafx.stage.Stage;

public class SpaceNavigator extends Application {

	private GraphicsManager GraphicsManager;
	
	private Calculator Calculator;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Calculator = new Calculator();
		
		GraphicsManager = new GraphicsManager(primaryStage, Calculator);

//		OrbitTransfer hohman = new OrbitTransfer();
//
//		double[] testR = new double[3];
//		double[] testV = new double[3];
//		double[] testDay = new double[3];
//		double[] testTime = new double[3];
//
//		testR[0] = 200;
//		testR[1] = 0;
//		testR[2] = 0;
//
//		testV[0] = 200;
//		testV[1] = 0;
//		testV[2] = 0;
//
//		testDay[0] = 2004;
//		testDay[1] = 5;
//		testDay[2] = 12;
//
//		testTime[0] = 14;
//		testTime[1] = 45;
//		testTime[2] = 30;
//
//		double mu = 398600;
//
//		hohman.HohmanTansfer(testR, testV, mu, testDay, testTime);
//		hohman.Lambert(testR, testV, 20000);
//
//		Future<MatlabEngine> engine = MatlabEngine.startMatlabAsync();
//		MatlabEngine eng = engine.get();
//		StringWriter output = new StringWriter();
//		eng.eval("cd 'res/matlab/'");
//		eng.eval("Test2", output, null);
//		System.out.println(output);
	}

}
