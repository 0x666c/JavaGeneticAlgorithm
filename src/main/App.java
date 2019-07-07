package main;

import net.x666c.glib.GFrame;
import net.x666c.glib.graphics.Renderer;

public abstract class App {
	
	protected int updates = 60;
	
	public abstract void setup(GFrame frame);
	public abstract void update();
	public abstract void draw(Renderer r);

}
