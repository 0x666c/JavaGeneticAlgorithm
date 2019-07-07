package car;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.math.phys.Collider;
import net.x666c.glib.math.vector.Line;
import net.x666c.glib.math.vector.Point;
import net.x666c.glib.math.vector.Vector;
import static java.lang.Math.*;

public class Car {
	
	Path2D.Float collider;
	Collider pos;
	Vector vel, acc;
	
	float rotation = 0;
	
	float speed = 3;
	float steering = 5;
	float brakeFactor = 0.956f;
	
	int width = 10;
	int height = 20;
	
	// -----------------------------------------------------------------------------
	
	Car(float x, float y) {
		//bounds = new Line[] {new Line(400, 400, 400 + width, 400),new Line(410, 400, 400 + width, 400 + height),new Line(400 + width, 400 + height, 400, 400 + height),new Line(400, 400 + height, 400, 400)};
		
		pos = new Collider(x, y, width, height);
		collider = new Path2D.Float(pos);
		
		vel = Vector.create(1e-30f, 1e-30f);
		acc = Vector.zero();
		
	}
	
	// -----------------------------------------------------------------------------
	
	void draw(Renderer r) {
		float midX = pos.x()+width/2;
		float midY = pos.y()+height/2;
		
		/*r.color(Color.GREEN);
		r.line(midX, midY, midX + (vel.x * 50), midY + (vel.y * 50));
		r.color(Color.MAGENTA);
		r.line(midX, midY, midX + (acc.x * 50), midY + (acc.y * 50));*/
		
		if(collusion(r))
			r.color(Color.RED);
		else
			r.color(Color.GREEN);
		//r.g2d().rotate(Math.toRadians(rotation), midX, midY);
		
		r.rectangle(pos.x(), pos.y(), pos.width(), pos.height());
	}
	
	// -----------------------------------------------------------------------------
	
	void update() {
		//if(collusion()) {}

		pos.x += vel.x;
		pos.y += vel.y;
		
		acc = Vector.zero();
		
		brake();
		
		steering = (float) vel.magnitude() * 2f;
	}
	
	// -----------------------------------------------------------------------------
	
	boolean collusion(Renderer r) {
		final Line l1 = new Line(pos.x(), pos.y(), pos.x() + pos.width(), pos.y());
		final Line l2 = new Line(pos.x() + pos.width(), pos.y(), pos.x() + pos.width(), pos.y() + pos.height());
		final Line l3 = new Line(pos.x() + pos.width(), pos.y() + pos.height(), pos.x(), pos.y() + pos.height());
		final Line l4 = new Line(pos.x(), pos.y() + pos.height(), pos.x(), pos.y());
		for (Wall wall : Track.walls) {
			final Point p1 = wall.raycast(l1);
			final Point p2 = wall.raycast(l2);
			final Point p3 = wall.raycast(l3);
			final Point p4 = wall.raycast(l4);
			try {
				r.color(Color.RED);
				r.point(p1);
				r.color(Color.GREEN);
				r.point(p2);
				r.color(Color.BLUE);
				r.point(p3);
				r.color(Color.MAGENTA);
				r.point(p4);
			} catch (Exception e) {
			}
			if(false)
				return true;
		}
		return false;
	}
	
	// -----------------------------------------------------------------------------
	
	boolean moves = false;
	
	void move() {
		if(vel.magnitude() < speed) {
			Vector a = Vector.create(0, -0.1f);
			rotateVector(a, rotation);
			vel.add(a);
		}
	}
	
	// -----------------------------------------------------------------------------
	
	void brake() {
		if(vel.magnitude() > 0.0001)
			vel.scale(brakeFactor);
	}
	
	// -----------------------------------------------------------------------------
	
	void right() {
		rotateVector(vel, steering);
		rotation += steering;;//(rotation >= 360 ? -360 : steering);
	}
	
	// -----------------------------------------------------------------------------
	
	void left() {
		rotateVector(vel, -steering);
		rotation -= steering;
	}
	
	// -----------------------------------------------------------------------------
	
	void rotateVector(Vector v, float ang) {
		ang = (float) toRadians(ang);
		double x = (cos(ang) * v.x) - (sin(ang) * v.y);
		double y = (sin(ang) * v.x) + (cos(ang) * v.y);
		v.set((float)x, (float)y);
	}
	
}