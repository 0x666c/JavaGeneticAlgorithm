package car;

import static java.lang.Math.*;
import java.awt.Color;
import main.App;
import net.x666c.glib.GFrame;
import net.x666c.glib.graphics.Renderer;

public class LearningCar extends App {
	
	Track track;
	
	// -----------------------------------------------------------------------------
	
	public void setup(GFrame frame) {
		track = new Track();
		
		frame.background(Color.GRAY);
	}
	
	// -----------------------------------------------------------------------------
	
	public void update() {
		track.update();
	}
	
	// -----------------------------------------------------------------------------
	
	public void draw(Renderer r) {
		track.draw(r);
	}
}
