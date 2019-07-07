package dots;
import java.util.Random;

import net.x666c.glib.math.MathUtil;
import net.x666c.glib.math.vector.Vector;
public class Brain {
	
	Vector[] moves;
	int step;
	
	boolean dead = false;
	boolean reached = false;
	
	// ------------------------------------------------------------
	
	Brain(int len) {
		moves = new Vector[len];
		for (int i = 0; i < this.moves.length; i++) {
			float a = MathUtil.random((float) (Math.PI * 2));
			moves[i] = Vector.fromAngle(a);
		}
	}
	
	// ------------------------------------------------------------
	
	Vector nextMove() {
		Vector r = moves[step++];
		if(step >= moves.length)
			dead = true;
		return r;
	}
	
	// ------------------------------------------------------------
	
	Brain copy() {
		Brain brain = new Brain(moves.length);
		for (int i = 0; i < brain.moves.length; i++) {
			brain.moves[i] = this.moves[i].clone();
		}
		return brain;
	}
	
	// ------------------------------------------------------------
	
	void mutate(float probability) {
		for (int i = 0; i < moves.length; i++) {
			if(MathUtil.random() <= probability) {
				moves[i] = Vector.random();
			}
		}
	}
	
}
