package main;

import org.fxyz3d.scene.Axes;

import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Planet {

	public PhongMaterial material;

	public Sphere sphere;

	public Point3D location;

	public Axes rotationAxis;

	public double rotationRate;
	public double rotationAngle;

	public Planet(double size, Point3D location, double rotationRate, double rotationAngle, PhongMaterial material) {

		sphere = new Sphere(size);
		sphere.setTranslateX(location.getX());
		sphere.setTranslateY(location.getY());
		sphere.setTranslateZ(location.getZ());
		sphere.setRotationAxis(new Point3D(0, 0, 1));
		sphere.setRotate(rotationAngle);
		this.rotationRate = rotationRate;
		this.rotationAngle = rotationAngle;
		this.material = material;
		sphere.setMaterial(material);

	}

	public double ang = 0;

	public void update(long delta) {
		ang = (rotationRate * (delta / 16666666));
		Rotate r = new Rotate(ang, new javafx.geometry.Point3D(0, 1, 0));
		sphere.getTransforms().add(r);
	}

	public Sphere getShape() {
		return sphere;
	}

}
