package main;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GraphicsManager {

	private Stage primaryStage;
	public GraphicsManager(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
	}
	
	public void initialize() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(getClass().getResource("/simscreen.fxml").getPath());
        GridPane root = (GridPane) loader.load(fxmlStream);
        primaryStage.setTitle("SpaceNavigators");
        primaryStage.setFullScreenExitHint("");
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(250);
        
        primaryStage.getScene().setOnKeyPressed(e->{
        	System.out.println("Width: " + primaryStage.getWidth() + " Height: " + primaryStage.getHeight());
        });
	}
}
