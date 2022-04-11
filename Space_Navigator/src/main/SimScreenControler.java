package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.scene.Skybox;
import org.fxyz3d.shapes.composites.PolyLine3D;
import org.fxyz3d.shapes.composites.PolyLine3D.LineType;

import com.sun.prism.paint.Color;

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

		
		

		 
		Planets = new ArrayList<Planet>();
		PhongMaterial sunmaterial = new PhongMaterial();
		sunmaterial.setDiffuseMap(new Image("images/planets/sun.jpg"));
		Planets.add(new Planet(750, new Point3D(0, 0, 0), 1/27, 7.25, sunmaterial, 0));

		PhongMaterial mercurymaterial = new PhongMaterial();
		mercurymaterial.setDiffuseMap(new Image("images/planets/mercury.jpg"));
		Planets.add(new Planet(75, new Point3D(750* 2, 0, 0), 1 / .24, 3, mercurymaterial,1));
		
		PhongMaterial venusmaterial = new PhongMaterial();
		venusmaterial.setDiffuseMap(new Image("images/planets/venus.jpg"));
		Planets.add(new Planet(150, new Point3D(750* 1, 0, 0), 1 / .24, 3, venusmaterial,2));
		
		PhongMaterial earthmaterial = new PhongMaterial();
		earthmaterial.setDiffuseMap(new Image("images/planets/earth.jpg"));
		Planets.add(new Planet(160, new Point3D(0, 0, 0), 1, 23.44, earthmaterial,3));

		PhongMaterial marsmaterial = new PhongMaterial();
		marsmaterial.setDiffuseMap(new Image("images/planets/mars.jpg"));
		Planets.add(new Planet(155, new Point3D(-750* 1, 0, 0), 1 / 1.02, 25, marsmaterial,4));

		PhongMaterial jupitermaterial = new PhongMaterial();
		jupitermaterial.setDiffuseMap(new Image("images/planets/jupiter.jpg"));
		Planets.add(new Planet(500, new Point3D(-750* 2, 0, 0), 1 / .24, 3, jupitermaterial,5));

		PhongMaterial saturnmaterial = new PhongMaterial();
		saturnmaterial.setDiffuseMap(new Image("images/planets/saturn.jpg"));
		Planets.add(new Planet(450, new Point3D(-750* 3, 0, 0), 1 / .24, 3, saturnmaterial,6));
		
		PhongMaterial uranusmaterial = new PhongMaterial();
		uranusmaterial.setDiffuseMap(new Image("images/planets/uranus.jpg"));
		Planets.add(new Planet(300, new Point3D(-750* 4, 0, 0), 1 / .24, 3, uranusmaterial,7));

		PhongMaterial neptunematerial = new PhongMaterial();
		neptunematerial.setDiffuseMap(new Image("images/planets/neptune.jpg"));
		Planets.add(new Planet(350, new Point3D(-750* 5, 0, 0), 1 / .24, 3, neptunematerial,8));
		
		for (Planet p : Planets) {
			world.getChildren().add(p.getShape());
		}

		subscene.setRoot(world);
		datePicker.setValue(LocalDate.now());
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            getPlanetPos();
        });
	
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
	}

	private long last = 0;

	private boolean calculate = false;
	private void update(long delta) {
		for (Planet p : Planets) {
			p.update(delta);
			if(calculate) {
				datePicker.setValue(datePicker.getValue().plusDays(1));
			}
		}
	}
	
	@FXML
	private void getPlanetPos() {
		LocalDate date = datePicker.getValue();
		
		for(Planet p: Planets) {
			if(p.getId() != 0 && calculator.engine != null) {
			try {
				System.out.println();
				p.setLocation(calculator.PlanetPosition(p.getId(), date.getYear(), date.getMonthValue(),date.getDayOfMonth(),1,1,1, 0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	
	@FXML
	private void calculate() {
		double planetid1 =2;
		double planetid2 = 4;
		double year1 = 2022;;
		double year2 = 2024;
		double month1 = 1;
		double month2 = 11;
		double day1 = 1;
		double day2 = 2;
		double hour1 =1;
		double hour2 =1;
		double minute1 = 1;
		double minute2 = 1;
		double second1 = 1;
		double second2 = 1;
		double altitude1 = 600;
		double altitude2 = 600;
	//    calculate = true;
		try {
			List<Point3D> points = calculator.Trajectory(planetid1,planetid2, year1, year2,month1,month2, day1, day2, hour1,hour2, minute1,minute2, second1, second2,  altitude1,altitude2, 1);
			PolyLine3D trajectory = new PolyLine3D(points, 25f, javafx.scene.paint.Color.RED, LineType.TRIANGLE);
			world.getChildren().add(trajectory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
