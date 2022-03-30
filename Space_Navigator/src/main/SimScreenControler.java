package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.fxyz3d.scene.Skybox;

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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

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
	private BorderPane BorderPane;

	@FXML
	private SubScene subscene;
	
	@FXML
	private DatePicker datePicker;

	public FPSCamera camera;

	private ArrayList<Planet> Planets;

	private Group world;
	
	public Calculator calculator;

	public SimScreenControler() {
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
		Skybox skybox = new Skybox(milkyway, 100000, camera.getCamera());
		world.getChildren().add(skybox);

		AmbientLight light = new AmbientLight();
		world.getChildren().add(light);

		new AnimationTimer() {
			@Override
			public void handle(long now) {

				long delta = now - last;
				if (delta > 16666666) {
					update(delta);
					last = now;
				}
			}
		}.start();

		 datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
	            getPlanetPos();
	        });
		Planets = new ArrayList<Planet>();
		PhongMaterial sunmaterial = new PhongMaterial();
		sunmaterial.setDiffuseMap(new Image("images/planets/sun.jpg"));
		Planets.add(new Planet(750, new javafx.geometry.Point3D(750* 3, 0, 0), 1/27, 7.25, sunmaterial, 0));

		PhongMaterial mercurymaterial = new PhongMaterial();
		mercurymaterial.setDiffuseMap(new Image("images/planets/mercury.jpg"));
		Planets.add(new Planet(75, new javafx.geometry.Point3D(750* 2, 0, 0), 1 / .24, 3, mercurymaterial,1));
		
		PhongMaterial venusmaterial = new PhongMaterial();
		venusmaterial.setDiffuseMap(new Image("images/planets/venus.jpg"));
		Planets.add(new Planet(150, new javafx.geometry.Point3D(750* 1, 0, 0), 1 / .24, 3, venusmaterial,2));
		
		PhongMaterial earthmaterial = new PhongMaterial();
		earthmaterial.setDiffuseMap(new Image("images/planets/earth.jpg"));
		Planets.add(new Planet(160, new javafx.geometry.Point3D(0, 0, 0), 1, 23.44, earthmaterial,3));

		PhongMaterial marsmaterial = new PhongMaterial();
		marsmaterial.setDiffuseMap(new Image("images/planets/mars.jpg"));
		Planets.add(new Planet(155, new javafx.geometry.Point3D(-750* 1, 0, 0), 1 / 1.02, 25, marsmaterial,4));

		PhongMaterial jupitermaterial = new PhongMaterial();
		jupitermaterial.setDiffuseMap(new Image("images/planets/jupiter.jpg"));
		Planets.add(new Planet(500, new javafx.geometry.Point3D(-750* 2, 0, 0), 1 / .24, 3, jupitermaterial,5));

		PhongMaterial saturnmaterial = new PhongMaterial();
		saturnmaterial.setDiffuseMap(new Image("images/planets/saturn.jpg"));
		Planets.add(new Planet(450, new javafx.geometry.Point3D(-750* 3, 0, 0), 1 / .24, 3, saturnmaterial,6));
		
		PhongMaterial uranusmaterial = new PhongMaterial();
		uranusmaterial.setDiffuseMap(new Image("images/planets/uranus.jpg"));
		Planets.add(new Planet(300, new javafx.geometry.Point3D(-750* 4, 0, 0), 1 / .24, 3, uranusmaterial,7));

		PhongMaterial neptunematerial = new PhongMaterial();
		neptunematerial.setDiffuseMap(new Image("images/planets/neptune.jpg"));
		Planets.add(new Planet(350, new javafx.geometry.Point3D(-750* 5, 0, 0), 1 / .24, 3, neptunematerial,8));
		
		for (Planet p : Planets) {
			world.getChildren().add(p.getShape());
		}

		subscene.setRoot(world);

	}

	private long last = 0;

	private void update(long delta) {
		for (Planet p : Planets) {
			p.update(delta);
		}
	}
	
	@FXML
	private void getPlanetPos() {
		LocalDate date = datePicker.getValue();
		
		try {
			Planets.get(2).setLocation(calculator.PlanetPosition(2, date.getYear(), date.getMonthValue(),date.getDayOfMonth(),1,1,1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(Planet p: Planets) {
//			if(p.getId() != 0) {
//			try {
//				p.setLocation(calculator.PlanetPosition(p.getId(), date.getYear(), date.getMonthValue(),date.getDayOfMonth(),1,1,1));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			}
//		}
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
