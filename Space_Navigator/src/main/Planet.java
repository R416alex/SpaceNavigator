package main;

import org.fxyz3d.scene.Axes;

import java.util.ArrayList;
import java.util.List;

import org.fxyz3d.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Planet {

	public PhongMaterial material;

	public Sphere sphere;

	public Point3D location;

	public Axes rotationAxis;

	public double rotationRate;
	public double rotationAngle;

	public ArrayList<Point3D> Path;

	public int id;

	public Planet(double size, Point3D location, double rotationRate, double rotationAngle, PhongMaterial material,
			int id) {

		this.id = id;
		sphere = new Sphere(size);
		sphere.setTranslateX(location.getX());
		sphere.setTranslateY(location.getY());
		sphere.setTranslateZ(location.getZ());
		sphere.setRotationAxis(new javafx.geometry.Point3D(0, 0, 1));
		sphere.setRotate(rotationAngle);
		this.rotationRate = rotationRate;
		this.rotationAngle = rotationAngle;
		this.material = material;
		this.location = location;
		sphere.setMaterial(material);

	}

	public double ang = 0;

	public void update(int step) {
		if (Path != null) {
			setLocation(Path.get(step));
		}
	}

	public Sphere getShape() {
		return sphere;
	}

	public void setLocation(Point3D point3d) {
		location = point3d;
		sphere.setTranslateX(location.getX());
		sphere.setTranslateY(location.getZ());
		sphere.setTranslateZ(location.getY());
	}

	public int getId() {
		return id;
	}

	public void setPath(ArrayList<Point3D> list) {
		Path = list;

	}

	public int getPathLength() {
		return Path.size();
	}

}
