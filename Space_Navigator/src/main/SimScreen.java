package main;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SimScreen extends Pane {

	private double height;
	private double width;

	private BorderPane borderPane;
	private Pane mainPane;
	private Pane rightPane;
	private Pane bottomPane;
	
	private Stage mainStage;

	public SimScreen(double h, double w, Stage mainStage) {
		height = h;
		width = w;
		this.mainStage = mainStage;
		initNodes();

	}

	private void initNodes() {
		borderPane = new BorderPane();

		initRight();
		initBottom();
		initMain();

		borderPane.setCenter(mainPane);
		borderPane.setRight(rightPane);
		borderPane.setBottom(bottomPane);
		borderPane.setPrefSize(width, height);

		this.getChildren().add(borderPane);

	}


	private void initRight() {
		rightPane = new Pane();
		rightPane.setPrefSize(width / 5, height);
		rightPane.setStyle("-fx-background-color: #34eb34; ");
		rightPane.getChildren().addAll();

	}

	private void initBottom() {
		bottomPane = new Pane();
		bottomPane.setPrefSize(width, height / 5);
		bottomPane.setStyle("-fx-background-color: #0084ff; ");

		Button quit = new Button("Quit");
		quit.setPrefSize(100, 50);
		quit.setLayoutX((width / 2) - (quit.getPrefWidth() / 2));
		quit.setLayoutY(bottomPane.getPrefHeight() / 2.25);
		quit.setOnAction(e -> {
			mainStage.close();
		});

		bottomPane.getChildren().addAll(quit);
	}

	private void initMain() {
		mainPane = new Pane();
		mainPane.setPrefSize(width - rightPane.getPrefWidth(), height - bottomPane.getPrefHeight());

		BackgroundImage BI = new BackgroundImage(new Image(getClass().getResourceAsStream("/background.jpg")),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(height - bottomPane.getPrefHeight(), width - rightPane.getPrefWidth(), false, false,
						true, true));
		mainPane.setBackground(new Background(BI));

	}
}
