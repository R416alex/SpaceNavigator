package main;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphicsManager {

	private Stage primaryStage;
	private Scene scene;
	private Calculator calculator;

	public GraphicsManager(Stage primaryStage, Calculator calculator) throws IOException {
		this.primaryStage = primaryStage;
		this.calculator = calculator;
		initialize();

	}

	public void initialize() throws IOException {
		Pane titlePane = new Pane();
		titlePane.setPrefSize(1280, 720);
		BackgroundImage bi = new BackgroundImage(new Image("/images/title.jpg"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(984, 715, false, false, false, false));

		titlePane.setBackground(new Background(bi));
		Scene title = new Scene(titlePane, 1280, 720);

		primaryStage.setScene(title);
		primaryStage.show();
		primaryStage.sizeToScene();

		primaryStage.show();

		FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), titlePane);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), titlePane);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);

		AnimationTimer timer = new timer(fadeOut);
		fadeIn.play();
		fadeIn.setOnFinished((e) -> {
			timer.start();
			try {
				calculator.initializeMATLAB();
			} catch (Exception e1) {
				System.out.println("test");
				}
		});
		fadeOut.setOnFinished((e) -> {
			timer.stop();
			setSimScreen();
		});
	}

	private void setSimScreen() {
		FXMLLoader loader = new FXMLLoader();
		try {
			Parent root = loader.load(getClass().getResourceAsStream("/SimScreen.fxml"));

			SimScreenController controller = loader.getController();

			controller.setCalculator(calculator);
			controller.getPlanetPos();
			primaryStage.setTitle("SpaceNavigators");
			primaryStage.setFullScreenExitHint("");

			scene = new Scene(root);
			scene.setFill(Color.BLACK);

			controller.camera.loadControlsForScene(scene);

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.sizeToScene();

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						controller.focusSubscene();
					}
				}
			});

			letterbox(scene, (Pane) root);
		} catch (Exception e) {
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

	private double last = 0;
	private int count = 0;

	private class timer extends AnimationTimer {
		FadeTransition fadeOut;

		public timer(FadeTransition f) {
			fadeOut = f;
		}

		@Override
		public void handle(long now) {
			if (now - last > 1666666.66) {
				last = now;
				count++;
				if (count >= 60) {
					fadeOut.play();
				}
			}
		}
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
			Scale scale = new Scale(scaleFactor, scaleFactor);
			scale.setPivotX(0);
			scale.setPivotY(0);
			scene.getRoot().getTransforms().setAll(scale);
			contentPane.setPrefWidth(newWidth / scaleFactor);
			contentPane.setPrefHeight(newHeight / scaleFactor);

		}
	}
}
