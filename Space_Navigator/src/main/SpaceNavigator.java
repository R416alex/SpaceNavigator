package main;

//import com.mathworks.engine.MatlabEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class SpaceNavigator extends Application {

	private GraphicsManager GraphicsManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GraphicsManager = new GraphicsManager(primaryStage);
	}

}
