package main;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class SimScreenControler {

	ObservableList<String> PlanetList = FXCollections.observableArrayList("Mercury", "Venus", "Earth", "Mars",
			"Jupiter", "Saturn", "Uranus", "Neptune", "Pluto");

	@FXML
	private ChoiceBox<String> StartingPlanetSelector;

	@FXML
	private ChoiceBox<String> DestinationPlanetSelector;

	@FXML
	private Button FullScreenButton;

	@FXML
	private Button QuitButton;

	@FXML
	private Button SetOrbitalElementsButton;

	@FXML
	private GridPane OrbitalElements;

	@FXML
	private SubScene subscene;

	private PerspectiveCamera camera;

	public SimScreenControler() {
	}

	private long last = 0;

	private void UpdateThread() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - last > 1000) {
					last = now;
					update();
				}
			}
		}.start();
	}

	private boolean wdown = false;
	private boolean adown = false;
	private boolean sdown = false;
	private boolean ddown = false;
	private boolean udown = false;
	private boolean dndown = false;
	private boolean ldown = false;
	private boolean rdown = false;

	private double moveSpeed = 10;

	private void update() {
		if (wdown && !sdown) {
			worldRotX.setAngle(worldRotX.getAngle() - 2);
		}
		if (sdown && !wdown) {
			worldRotX.setAngle(worldRotX.getAngle() - 2);
		}
		if (adown && !ddown) {
			worldRotY.setAngle(worldRotY.getAngle() + 2);
		}
		if (ddown && !adown) {
			worldRotY.setAngle(worldRotY.getAngle() - 2);
		}
		if (udown && !dndown) {
			
		}
		if (dndown && !udown) {
			
		}
		if (ldown && !rdown) {

		}
		if (rdown && !ldown) {

		}
	}

	@FXML
	private void handleKeyReleased(KeyEvent event) {
		switch (event.getCode()) {
		case W:
			wdown = false;
			break;
		case A:
			adown = false;
			break;
		case S:
			sdown = false;
			break;
		case D:
			ddown = false;
			break;
		default:
			break;
		}
	}

	@FXML
	private void handleKeyPress(KeyEvent event) {

		System.out.println(event.getCode().toString());
		switch (event.getCode()) {
		case W:
			wdown = true;
			break;
		case A:
			adown = true;
			break;
		case S:
			sdown = true;
			break;
		case D:
			ddown = true;
			break;
		default:
			break;
		}

	}

	private Group world;
	private Rotate worldRotX;
	private Rotate worldRotY;
	private Rotate worldRotZ;

	private Translate worldTransX;
	private Translate worldTransY;
	private Translate worldTransZ;

	@FXML
	private void initialize() throws IOException {

		StartingPlanetSelector.setItems(PlanetList);
		StartingPlanetSelector.setValue("Earth");

		DestinationPlanetSelector.setItems(PlanetList);
		DestinationPlanetSelector.setValue("Mars");

		camera = new PerspectiveCamera(true);
		camera.setFieldOfView(100);
		camera.setNearClip(1);
		camera.setFarClip(10000);

		subscene.setCamera(camera);

		Image top = new Image("images/skybox/Top.bmp");
		Image bottom = new Image("images/skybox/Bottom.bmp");
		Image right = new Image("images/skybox/Right.bmp");
		Image left = new Image("images/skybox/Left.bmp");
		Image front = new Image("images/skybox/Front.bmp");
		Image back = new Image("images/skybox/Back.bmp");
		Skybox skybox = new Skybox(top, bottom, left, right, front, back, 100, camera);

		world = createEnvironment();
		world.getChildren().add(skybox);
		subscene.setRoot(world);
		worldRotX = new Rotate(0, Rotate.X_AXIS);
		worldRotY = new Rotate(0, Rotate.Y_AXIS);
		worldRotZ = new Rotate(0, Rotate.Z_AXIS);

		worldTransX = new Translate();
		worldTransY = new Translate();
		worldTransZ = new Translate();

		world.getTransforms().addAll(worldRotY, worldRotX, worldRotZ, worldTransX, worldTransY, worldTransZ);

		UpdateThread();

	}

	private Group createEnvironment() {
		Group group = new Group();

		Box ground = new Box();
		ground.setHeight(10);
		ground.setWidth(1000);
		ground.setDepth(1000);

		ground.setTranslateX(-500);
		ground.setTranslateZ(-500);

		Box box = new Box(100, 100, 100);
		box.setTranslateY(10);

		group.getChildren().addAll(ground, box);

		return group;
	}

	@FXML
	private void toggleFullscreen() {
		Stage stage = (Stage) FullScreenButton.getScene().getWindow();
		stage.setFullScreen(!stage.isFullScreen());
	}

	@FXML
	private void toggleOrbitalElements() {
		OrbitalElements.setVisible(!OrbitalElements.isVisible());
	}

	@FXML
	private void quit() {
		System.exit(1);
	}
}
