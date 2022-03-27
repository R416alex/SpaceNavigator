package main;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class GraphicsManager {

	
	private Stage primaryStage;
	private Scene scene;
	
	public GraphicsManager(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
	}

	public void initialize() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(getClass().getResource("/SimScreen.fxml").getPath());
        
        Parent root = loader.load(fxmlStream);
        
        
        primaryStage.setTitle("SpaceNavigators");
        primaryStage.setFullScreenExitHint("");


        scene = new Scene(root);
        scene.setFill(Color.BLACK);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.sizeToScene();
        

		
        letterbox(scene, (Pane)root);
      }
	
	

      private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio      = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
      }

      private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
          this.scene = scene;
          this.ratio = ratio;
          this.initHeight = initHeight;
          this.initWidth = initWidth;
          this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          final double newWidth  = scene.getWidth();
          final double newHeight = scene.getHeight();

          double scaleFactor =
              newWidth / newHeight > ratio
                  ? newHeight / initHeight
                  : newWidth / initWidth;
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);
            contentPane.setPrefWidth (newWidth  / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);

        }
      }
}
