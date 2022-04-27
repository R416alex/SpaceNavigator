package main;

import org.fxyz3d.scene.Axes;

import java.util.ArrayList;

import org.fxyz3d.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Planet {

	public PhongMaterial material;

	public Sphere sphere;

	public Point3D location;



	public ArrayList<Point3D> Path;

	public int id;

	public Planet(double size, Point3D location, PhongMaterial material,
			int id) {

		this.id = id;
		sphere = new Sphere(size);
		sphere.setTranslateX(location.getX());
		sphere.setTranslateY(location.getY());
		sphere.setTranslateZ(location.getZ());
		sphere.setRotationAxis(new javafx.geometry.Point3D(0, 0, 1));
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
