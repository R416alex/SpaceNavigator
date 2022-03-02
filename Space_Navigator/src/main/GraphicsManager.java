package main;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class GraphicsManager {



	public GraphicsManager(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/simscreentest.fxml"));
        primaryStage.setTitle("SpaceNavigators");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
	}
}
