package car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.input.Input;

public class Track {
	
	Car car;
	
	static List<Wall> walls = new ArrayList<Wall>();
	
	// -----------------------------------------------------------------------------
	
	Track() {
		car = new Car(400, 400);
		
		Collections.addAll(walls, new Wall(0, 0, 0, 800), new Wall(0, 0, 800, 800));
	}
	
	// -----------------------------------------------------------------------------
	
	void update() {
		for (Wall wall : walls) {
			wall.update();
		}
		
		car.update();
		
		if(Input.keyboard.key('w')) {
			car.move();
		}
		if (Input.keyboard.key('a')) {
			car.left();
		}
		if (Input.keyboard.key('s')) {
			car.brake();
		}
		if (Input.keyboard.key('d')) {
			car.right();
		}
	}
	
	// -----------------------------------------------------------------------------
	
	void draw(Renderer r) {
		for (Wall wall : walls) {
			wall.draw(r);
		}
		car.draw(r);
	}
	
}
