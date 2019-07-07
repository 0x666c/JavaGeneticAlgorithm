package dots;

import java.awt.Color;

import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.math.phys.Collider;

public class Obstacle {
	
	Collider collider;
	
	Color color1 = Color.BLACK, color2 = Color.YELLOW;
	
	// ------------------------------------------------------------
	
	Obstacle(float x, float y, float width, float height) {
		collider = new Collider(x, y, width, height);
	}
	
	// ------------------------------------------------------------
	
	void draw(Renderer r) {
		r.fill();
		float step = (float) collider.width / 16f;
		boolean switchh = true;
		for (int i = 0; i < 16; i++) {
			if(switchh) {
				r.color(color1);
				switchh = false;
			} else {
				r.color(color2);
				switchh = true;
			}
			r.rectangle(collider.x() + step * i, collider.y(), collider.width() / 16f, collider.height());
		}
		r.color(0);
		r.draw();
		r.rectangle(collider.x(), collider.y(), collider.width(), collider.height());
	}
	
	// ------------------------------------------------------------
	
}