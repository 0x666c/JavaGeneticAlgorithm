package dots;

import java.awt.Color;

import javax.sql.PooledConnection;

import main.App;
import net.x666c.glib.GFrame;
import net.x666c.glib.graphics.Renderer;

public class LearningDots extends App {
	
	{
		updates = 100;
		seed = "abracadabra";
		onlyBest = true;
	}
	
	static Obstacle[] obstacles = {
			new Obstacle(350, 600, 10, 200),
			new Obstacle(450, 600, 10, 200),
			
			new Obstacle(360, 600, 30, 10),
			new Obstacle(420, 600, 30, 10),
			
			new Obstacle(100, 500, 600, 10),
			
			new Obstacle(0, 300, 350, 10),
			new Obstacle(450, 300, 350, 10),
		};
	
	// ------------------------------------------------------------
	
	Population population;
	String seed = "cooldude";
	boolean onlyBest;
	
	public void setup(GFrame frame) {
		frame.onKeyPress((char)27, () -> System.exit(0));
		frame.onKeyPress('q', () -> frame.setUps(5000));
		frame.onKeyPress('w', () -> frame.setUps(60));
		frame.onKeyPress('b', () -> population.onlyBest(!population.showOnlyBest));
		population = new Population(2000, seed, onlyBest);
		
		frame.background(Color.DARK_GRAY);
	}
	
	// ------------------------------------------------------------

	public void update() {
		if (population.everybodyDied()) {
			// Do magic stuff
			System.out.println("Everyone died");
			long t1 = System.nanoTime();
			population.calculateFitness();
			long t2 = System.nanoTime();
			population.naturalSelection();
			long t3 = System.nanoTime();
			population.mutateBabies();
			long t4 = System.nanoTime();
			
			System.out.println("Evolving...");
			System.out.printf("Fitness calc took: %.6f\n", (t2 - t1) / 1e6);
			System.out.printf("Natural selection took: %.6f\n", (t3 - t2) / 1e6);
			System.out.printf("Mutation took: %.6f\n", (t4 - t3) / 1e6);
			
		} else {
			population.update();
		}
		
	}
	
	// ------------------------------------------------------------
	
	public void draw(Renderer r) {
		population.draw(r);
		
		r.fill();
		r.color(Color.BLUE);
		
		for (Obstacle obstacle : obstacles) {
			obstacle.draw(r);
		}
	}
	
	// ------------------------------------------------------------
	
	static boolean collidesWithObstacle(Dot dot) {
		for (int i = 0; i < obstacles.length; i++) {
			if(obstacles[i].collider.intersects(dot.pos.x, dot.pos.y, dot.dotSize * 2, dot.dotSize * 2)) {
				return true;
			}
		}
		return false;
	}
}