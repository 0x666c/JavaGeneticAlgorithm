package car;

import java.awt.geom.Line2D;
import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.math.phys.Collider;
import net.x666c.glib.math.vector.Line;
import net.x666c.glib.math.vector.Point;
import net.x666c.glib.math.vector.Vector;

public class Wall {
	
	Line line;
	
	// -----------------------------------------------------------------------------
	
	Wall(float x1, float y1, float x2, float y2) {
		line = new Line(x1, y1, x2, y2);
	}
	
	// -----------------------------------------------------------------------------
	
	void draw(Renderer r) {
		r.fill();
		r.color(~0);
		r.line(line);
	}
	
	// -----------------------------------------------------------------------------
	
	void update() {
		
	}
	
	
	Point raycast(Line l) {
		final float x1 = line.x1;
		final float y1 = line.y1;
		
		final float x2 = line.x2;
		final float y2 = line.y2;
		
		final float x3 = l.x1;
		final float y3 = l.y1;
		
		final float x4 = l.x2;
		final float y4 = l.y2;
		
		final float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		
		if(den == 0)
			return null;
		
		final float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		final float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		
		if(t > 0 && t < 1 && u > 0) {
			final float p1 = x1 + t * (x2 - x1);
			final float p2 = y1 + t * (y2 - y1);
			
			return new Point(p1, p2);
		} else {
			return null;
		}
	}
}
