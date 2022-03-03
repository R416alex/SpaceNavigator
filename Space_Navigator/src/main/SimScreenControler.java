package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SimScreenControler {

	ObservableList<String> PlanetList = FXCollections.observableArrayList("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto");
	
	@FXML
	private ChoiceBox<String> StartingPlanetSelector;
	
	@FXML
	private ChoiceBox<String> DestinationPlanetSelector;
	
	@FXML
	private Button FullScreenButton;
	
	@FXML
	private Button QuitButton;
	
	
	public SimScreenControler() {
	}
	
	@FXML
	private void initialize() {
		
		StartingPlanetSelector.setItems(PlanetList);
		StartingPlanetSelector.setValue("Earth");
		
		DestinationPlanetSelector.setItems(PlanetList);
		DestinationPlanetSelector.setValue("Mars");
	}
	
	@FXML
	private void toggleFullscreen() {
		Stage stage = (Stage) FullScreenButton.getScene().getWindow();
		stage.setFullScreen(!stage.isFullScreen());
	}
	
	@FXML
	private void quit() {
		System.exit(1);
	}
}
