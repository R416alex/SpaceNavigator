package main;

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

	}

}
