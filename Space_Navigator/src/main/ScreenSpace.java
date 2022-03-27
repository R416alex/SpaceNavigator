package main;

import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;

public class ScreenSpace{
	
	
	private SubScene camScene;
	private PerspectiveCamera camera;
	
	
	public ScreenSpace(Parent root) {
		camera = new PerspectiveCamera();
		camera.setFieldOfView(100);
		camera.setNearClip(1);
		camera.setFarClip(1000);
		
		camScene = new SubScene(root, 1080,570);
		
		camScene.setCamera(camera);
		
		Image top = new Image(this.getClass().getResource("images/skybox/top").getPath());
		Skybox skybox = new Skybox(top, top, top, top, top, top, 100, camera);
		
	}

	double scale = 1/10000000;

	private Planet[] planets;

}
