package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class GraphicsManager {

	private double renderHeight = 480;
	private double renderWidth = 854;

	private boolean initComplete;

	private Stage stage;
	private SimScreen simScreen;
	private Scene scene;


	public GraphicsManager(Stage primaryStage) {

		initComplete = false;

		stage = primaryStage;

		stage.setTitle("SpaceNavigators");

		simScreen = new SimScreen(renderHeight, renderWidth, stage);

		scene = new Scene(simScreen);
		Scale scale = new Scale(1, 1);
		scale.setPivotX(0);
		scale.setPivotY(0);
		scene.getRoot().getTransforms().setAll(scale);
		stage.setScene(scene);
		stage.setResizable(true);

		stage.show();
		stage.setMinHeight(stage.getHeight());
		stage.setMinWidth(stage.getWidth());
		
		

		letterbox(scene, simScreen);
		
		stage.setFullScreenExitHint("Press F to exit");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case F:    stage.setFullScreen(!stage.isFullScreen()); 
                    break;
				default:
					break;
                }
            }
		});
		
	}

	public void update(long time) {
		if (!initComplete) {
			stage.sizeToScene();
			
			initComplete = true;
		}
	}

	private void letterbox(final Scene scene, final Pane contentPane) {
		final double initWidth = scene.getWidth();
		final double initHeight = scene.getHeight();

		final double ratio = initWidth / initHeight;
		SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth,
				contentPane);
		scene.widthProperty().addListener(sizeListener);
		scene.heightProperty().addListener(sizeListener);

	}

	private static class SceneSizeChangeListener implements ChangeListener<Number> {
		private final Scene scene;
		private final double ratio;
		private final double initHeight;
		private final double initWidth;
		private final Pane contentPane;

		public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth,
				Pane contentPane) {
			this.scene = scene;
			this.ratio = ratio;
			this.initHeight = initHeight;
			this.initWidth = initWidth;
			this.contentPane = contentPane;

		}

		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
			final double newWidth = scene.getWidth();
			final double newHeight = scene.getHeight();
			double scaleFactor = newWidth / newHeight > ratio ? newHeight / initHeight : newWidth / initWidth;
			if (scaleFactor >= 1) {
				Scale scale = new Scale(scaleFactor, scaleFactor);
				scale.setPivotX(0);
				scale.setPivotY(0);
				scene.getRoot().getTransforms().setAll(scale);
				contentPane.setPrefWidth(newWidth / scaleFactor);
				contentPane.setPrefHeight(newHeight / scaleFactor);

			} else {
				contentPane.setPrefWidth(Math.max(initWidth, newWidth));
				contentPane.setPrefHeight(Math.max(initHeight, newHeight));
			}
		}
	}

}

