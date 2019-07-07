package dots;

import static net.x666c.glib.math.MathUtil.map;
import static net.x666c.glib.math.MathUtil.random;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.math.phys.Collider;
import net.x666c.glib.math.vector.Vector;

public class Dot {
	
	int dotSize = 3;
	
	Vector pos;
	Vector vel, acc;
	
	Brain brain;
	
	Color innerColor, outColor;
	
	double fitness;
	
	boolean bestDot = false;
	
	// ------------------------------------------------------------
	
	Dot() {
		//pos = Vector.create(map(random(), 0, 1, 50, 800 - 50), map(random(), 0, 1, 650, 800 - 50));
		pos = Vector.create(800 / 2, 780);
		vel = Vector.zero();
		acc = Vector.zero();
		
		brain = new Brain(1000);
		
		innerColor = Color.BLACK;
		outColor = Color.BLACK;
	}
	
	// ------------------------------------------------------------
	
	void draw(Renderer r) {
		if (!bestDot) {
			r.fill();
			r.color(innerColor);
			r.circle(pos.x, pos.y, dotSize);
			r.draw();
			r.color(0);
			r.circle(pos.x, pos.y, dotSize);
		} else {
			r.fill();
			r.color(Color.ORANGE);
			r.circle(pos.x, pos.y, dotSize+2);
			r.draw();
			r.color(0);
			r.stroke(2);
			r.circle(pos.x, pos.y, dotSize+2);
			r.stroke(1);
		}
	}
	
	// ------------------------------------------------------------
	
	void update() {
		if(!inBounds())
			brain.dead = true;
		
		if(!brain.dead && !brain.reached) {
			acc = brain.nextMove();
			vel.add(acc);
			vel.limit(5);
			pos.add(vel);
		}
		
		if(Population.goal.intersects(pos.x, pos.y, dotSize*2, dotSize*2)) {
			brain.reached = true;
		}
		
		colorize();
	}
	
	// ------------------------------------------------------------
	
	void calculateFitness() {
		if(brain.reached) {
			fitness = 1d/16d + 10000d/(double)(brain.step * brain.step);
		} else {
			double distance = Point2D.distance(0, pos.y, 0, Population.goal.y);
			fitness = 1d / (distance * distance); 
		}
	}
	
	// ------------------------------------------------------------
	
	Dot makeBaby() {
		Dot baby = new Dot();
		baby.brain = this.brain.copy();
		return baby;
	}
	
	// ------------------------------------------------------------
	
	void colorize() {
		int r = (int)map(brain.moves.length - brain.step, brain.moves.length, 0, 0, 255);
		int g = (int)map(brain.moves.length - brain.step, brain.moves.length, 0, 255, 0);
		innerColor = new Color(r, g, 0);
		
		if(brain.dead) {
			outColor = Color.RED;
		} else if(brain.reached) {
			outColor = Color.BLACK;
			innerColor = new Color(Color.HSBtoRGB(0.16f, 1, (1f / 255f) * innerColor.getGreen()));
		}
	}
	
	// ------------------------------------------------------------
	
	boolean inBounds() {
		boolean bounds1 = pos.x < 800-dotSize*2 && pos.x > 0+dotSize && pos.y < 800-dotSize*2 && pos.y > 0+dotSize;
		boolean collidesWithObstacle = LearningDots.collidesWithObstacle(this);
		
		return bounds1 && !collidesWithObstacle;
	}
}
