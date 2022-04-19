package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.scene.Skybox;
import org.fxyz3d.shapes.composites.PolyLine3D;
import org.fxyz3d.shapes.composites.PolyLine3D.LineType;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class SimScreenController {

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
	private Button calculateButton;

	@FXML
	private Button resetButton;

	@FXML
	private Button playButton;

	@FXML
	private Button pauseButton;

	@FXML
	private GridPane controls;

	@FXML
	private GridPane startAltError;

	@FXML
	private GridPane endAltError;

	@FXML
	private GridPane startDateError;

	@FXML
	private GridPane endDateError;

	@FXML
	private GridPane matlabError;

	@FXML
	private BorderPane BorderPane;

	@FXML
	private SubScene subscene;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private TextField startAlt;

	@FXML
	private TextField endAlt;

	@FXML
	private Slider speedSlider;

	@FXML
	private Label speedText;

	@FXML
	private Label deltaV;

	@FXML
	private Label timeOfFlight;
	
	private Sphere rocket;

	public FPSCamera camera;
	
	private List<Point3D> trajectorypoints;

	private ArrayList<Planet> Planets;

	private Group world;

	public Calculator calculator;

	private AnimationTimer timer;

	private PolyLine3D trajectory;

	private double playspeed;

	public SimScreenController() {
	}

	public void setCalculator(Calculator calc) {
		calculator = calc;
	}

	@FXML
	private void initialize() throws IOException {

		StartingPlanetSelector.setItems(PlanetList);
		StartingPlanetSelector.setValue("Earth");

		DestinationPlanetSelector.setItems(PlanetList);
		DestinationPlanetSelector.setValue("Mars");

		world = new Group();

		subscene = new SubScene(world, 1080, 570, true, SceneAntialiasing.BALANCED);
		BorderPane.setCenter(subscene);

		camera = new FPSCamera();
		subscene.setCamera(camera.getCamera());

		Image milkyway = new Image("images/skybox/MilkyWay.png");
		Skybox skybox = new Skybox(milkyway, 75000, camera.getCamera());
		world.getChildren().add(skybox);

		AmbientLight light = new AmbientLight();
		world.getChildren().add(light);

		Planets = new ArrayList<Planet>();
		PhongMaterial sunmaterial = new PhongMaterial();
		sunmaterial.setDiffuseMap(new Image("images/planets/sun.jpg"));
		Planets.add(new Planet(750, new Point3D(0, 0, 0), 1 / 27, 7.25, sunmaterial, 0));

		PhongMaterial mercurymaterial = new PhongMaterial();
		mercurymaterial.setDiffuseMap(new Image("images/planets/mercury.jpg"));
		Planets.add(new Planet(75, new Point3D(750 * 2, 0, 0), 1 / .24, 3, mercurymaterial, 1));

		PhongMaterial venusmaterial = new PhongMaterial();
		venusmaterial.setDiffuseMap(new Image("images/planets/venus.jpg"));
		Planets.add(new Planet(150, new Point3D(750 * 1, 0, 0), 1 / .24, 3, venusmaterial, 2));

		PhongMaterial earthmaterial = new PhongMaterial();
		earthmaterial.setDiffuseMap(new Image("images/planets/earth.jpg"));
		Planets.add(new Planet(160, new Point3D(0, 0, 0), 1, 23.44, earthmaterial, 3));

		PhongMaterial marsmaterial = new PhongMaterial();
		marsmaterial.setDiffuseMap(new Image("images/planets/mars.jpg"));
		Planets.add(new Planet(155, new Point3D(-750 * 1, 0, 0), 1 / 1.02, 25, marsmaterial, 4));

		PhongMaterial jupitermaterial = new PhongMaterial();
		jupitermaterial.setDiffuseMap(new Image("images/planets/jupiter.jpg"));
		Planets.add(new Planet(500, new Point3D(-750 * 2, 0, 0), 1 / .24, 3, jupitermaterial, 5));

		PhongMaterial saturnmaterial = new PhongMaterial();
		saturnmaterial.setDiffuseMap(new Image("images/planets/saturn.jpg"));
		Planets.add(new Planet(450, new Point3D(-750 * 3, 0, 0), 1 / .24, 3, saturnmaterial, 6));

		PhongMaterial uranusmaterial = new PhongMaterial();
		uranusmaterial.setDiffuseMap(new Image("images/planets/uranus.jpg"));
		Planets.add(new Planet(300, new Point3D(-750 * 4, 0, 0), 1 / .24, 3, uranusmaterial, 7));

		PhongMaterial neptunematerial = new PhongMaterial();
		neptunematerial.setDiffuseMap(new Image("images/planets/neptune.jpg"));
		Planets.add(new Planet(350, new Point3D(-750 * 5, 0, 0), 1 / .24, 3, neptunematerial, 8));

		PhongMaterial plutomaterial = new PhongMaterial();
		plutomaterial.setDiffuseMap(new Image("images/planets/pluto.jpg"));
		Planets.add(new Planet(100, new Point3D(-750 * 5, 0, 0), 1 / .24, 3, plutomaterial, 9));

		PhongMaterial rocketmaterial = new PhongMaterial();
		rocketmaterial.setDiffuseColor(Color.WHITE);
		rocket = new Sphere();
		rocket.setRadius(35);
		rocket.setMaterial(rocketmaterial);
		
		for (Planet p : Planets) {
			world.getChildren().add(p.getShape());
		}

		subscene.setRoot(world);

		startDatePicker.setValue(LocalDate.now());
		startDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			getPlanetPos();
		});

		playspeed = 1;
		speedSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
			String str = String.format("%.2f", newValue);
			playspeed = Double.valueOf(str);
			speedText.setText("Play Speed: " + str + "x");
		});

	}

	@FXML
	public void getPlanetPos() {
		LocalDate date = startDatePicker.getValue();
		for (Planet p : Planets) {
			if (p.getId() != 0 && calculator.engine != null) {
				try {
					p.setLocation(calculator.PlanetPosition(p.getId(), date.getYear(), date.getMonthValue(),
							date.getDayOfMonth(), 1, 1, 1, 0));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int step = 0;

	private long last = 0;

	@FXML
	private void calculate() {

		if (checkInputs()) {
			double planetid1 = stringToID(StartingPlanetSelector.getValue());
			double planetid2 = stringToID(DestinationPlanetSelector.getValue());
			double year1 = startDatePicker.getValue().getYear();
			double year2 = endDatePicker.getValue().getYear();
			double month1 = startDatePicker.getValue().getMonthValue();
			double month2 = endDatePicker.getValue().getMonthValue();
			double day1 = startDatePicker.getValue().getDayOfMonth();
			double day2 = endDatePicker.getValue().getDayOfMonth();
			double hour1 = 1;
			double hour2 = 1;
			double minute1 = 1;
			double minute2 = 1;
			double second1 = 1;
			double second2 = 1;
			double altitude1 = 600;
			double altitude2 = 600;
			try {
				ArrayList<Point3D>[] planetPaths = new ArrayList[9];
				planetPaths[0] = calculator.planetPath(1, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[1] = calculator.planetPath(2, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[2] = calculator.planetPath(3, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[3] = calculator.planetPath(4, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[4] = calculator.planetPath(5, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[5] = calculator.planetPath(6, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[6] = calculator.planetPath(7, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[7] = calculator.planetPath(8, startDatePicker.getValue(), endDatePicker.getValue());
				planetPaths[8] = calculator.planetPath(9, startDatePicker.getValue(), endDatePicker.getValue());
				for (int i = 0; i < planetPaths.length; i++) {
					Planets.get(i + 1).setPath(planetPaths[i]);
				}

				Object[] output = calculator.Trajectory(planetid1, planetid2, year1, year2, month1, month2, day1, day2,
						hour1, hour2, minute1, minute2, second1, second2, altitude1, altitude2, 1);

			    trajectorypoints = (List<Point3D>) output[0];

				String str = String.format("%.2f", output[1]);
				deltaV.setText("Required ΔV: " + str + " (Km/s)");
				str = String.format("%.2f", output[2]);
				timeOfFlight.setText("Time of Flight: " + str + " (Days)");

				trajectory = new PolyLine3D(trajectorypoints, 25f, javafx.scene.paint.Color.RED, LineType.TRIANGLE);
				world.getChildren().add(trajectory);
				for (Planet p : Planets) {
					if (p.getId() != 0) {
						p.setPath(
								calculator.planetPath(p.getId(), startDatePicker.getValue(), endDatePicker.getValue()));
					}
				}

				rocket.setTranslateX(trajectorypoints.get(0).getX());
				rocket.setTranslateY(trajectorypoints.get(0).getY());
				rocket.setTranslateZ(trajectorypoints.get(0).getZ());
				world.getChildren().add(rocket);
				calculateButton.setVisible(false);
				resetButton.setVisible(true);
				playButton.setVisible(true);
			} catch (Exception e) {
				matlabError.setVisible(true);
			}
		}
	}


	@FXML
	private void play() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				long delta = now - last;
				if (delta > 16666666) {
					for (Planet p : Planets) {
						p.update(step);
						rocket.setTranslateX(trajectorypoints.get(step).getX());
						rocket.setTranslateY(trajectorypoints.get(step).getY());
						rocket.setTranslateZ(trajectorypoints.get(step).getZ());
						
					}
					step = (int) Math.floor(step + playspeed);
					if (step >= Planets.get(1).getPathLength() - 1 || step >= trajectorypoints.size()-1) {
						this.stop();
					}
					last = now;
				}
			}
		};
		playButton.setVisible(false);
		pauseButton.setVisible(true);
		timer.start();
	}

	@FXML
	private void pause() {
		timer.stop();
		pauseButton.setVisible(false);
		playButton.setVisible(true);
	}

	private boolean checkInputs() {
		if (startDatePicker.getValue() == null) {
			startDateError.setVisible(true);
			return false;
		} else if (endDatePicker.getValue() == null) {
			endDateError.setVisible(true);
			return false;
		} else {
			try {
				double d = Double.parseDouble(startAlt.getText());
				if (d <= 0) {
					startAltError.setVisible(true);
					return false;
				}
			} catch (Exception e) {
				startAltError.setVisible(true);
				return false;
			}
			try {
				double d = Double.parseDouble(endAlt.getText());
				if (d <= 0) {
					endAltError.setVisible(true);
					return false;
				}
			} catch (Exception e) {
				endAltError.setVisible(true);
				return false;
			}
		}
		return true;
	}

	@FXML
	private void clearErrors() {
		startAltError.setVisible(false);
		endAltError.setVisible(false);
		startDateError.setVisible(false);
		endDateError.setVisible(false);
		matlabError.setVisible(false);
	}

	@FXML
	private void reset() {
		if (timer != null) {
			timer.stop();
		}
		step = 0;
		for (Planet p : Planets) {
			p.update(0);
		}
		world.getChildren().remove(rocket);
		world.getChildren().remove(trajectory);
		resetButton.setVisible(false);
		playButton.setVisible(false);
		pauseButton.setVisible(false);
		calculateButton.setVisible(true);
	}

	private double stringToID(String value) {
		value = value.toLowerCase();
		switch (value) {
		case "mercury":
			return 1;
		case "venus":
			return 2;
		case "earth":
			return 3;
		case "mars":
			return 4;
		case "jupiter":
			return 5;
		case "saturn":
			return 6;
		case "uranus":
			return 7;
		case "neptune":
			return 8;
		case "pluto":
			return 9;
		default:
			return -1;
		}
	}

	@FXML
	private void toggleFullscreen() {
		Stage stage = (Stage) FullScreenButton.getScene().getWindow();
		stage.setFullScreen(!stage.isFullScreen());
	}

	@FXML
	private void toggleControls() {
		controls.setVisible(!controls.isVisible());
	}

	@FXML
	private void quit() {
		System.exit(1);
	}

	public void focusSubscene() {
		subscene.requestFocus();
	}
}
