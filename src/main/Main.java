package main;
import car.LearningCar;
import dots.LearningDots;
import net.x666c.glib.GFrame;

public class Main {
	
	static App current = new LearningCar();
	
	public static void main(String[] args) {
		GFrame f = new GFrame(78, current.updates, current::draw, current::update);
		f.setSize(800, 800);
		current.setup(f);
		f.start();
	}

}