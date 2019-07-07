package dots;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.x666c.glib.graphics.Renderer;
import net.x666c.glib.math.MathUtil;

public class Population {
	
	static Ellipse2D.Float goal = new Ellipse2D.Float(800 / 2f, 10, 8, 8);
	
	String seed;
	int seedHash;
	
	int size;
	Dot[] members;
	
	double totalFitness;
	int minSteps = 1000;
	
	double averageFitness = Float.NaN, bestFitness = Float.NaN, worstFitenss = Float.NaN;
	int generation = 0;
	float mutationRate = 0.01f;
	
	boolean showOnlyBest;
	
	// ------------------------------------------------------------
	
	Population(int size, String seed, boolean onlyBest) {
		this.showOnlyBest = onlyBest;
		this.seed = seed;
		this.seedHash = seed.hashCode();
		MathUtil.randomSeed(seedHash);
		
		members = new Dot[size];
		for (int i = 0; i < members.length; i++) {
			members[i] = new Dot();
		}
	}
	
	// ------------------------------------------------------------
	
	boolean everybodyDied() {
		for (int i = 0; i < members.length; i++) {
			if (!members[i].brain.dead && !members[i].brain.reached)
				return false;
		}
		return true;
	}

	// ------------------------------------------------------------
	
	void calculateFitness() {
		for (Dot dot : members) {
			dot.calculateFitness();
		}
	}
	
	// ------------------------------------------------------------
	
	void calculateFitenssSum() {
		totalFitness = 0;
		
		bestFitness = 0;
		worstFitenss = 0;
		for (Dot dot : members) {
			totalFitness += dot.fitness;
			if(dot.fitness < worstFitenss)
				worstFitenss = dot.fitness;
			if(dot.fitness > bestFitness)
				bestFitness = dot.fitness;
		}
		averageFitness = totalFitness / members.length;
	}
	
	// ------------------------------------------------------------
	
	void naturalSelection() {
		generation++;
		
		Dot[] newDots = new Dot[members.length];
		calculateFitenssSum();
		
		Dot bestDot = members[0];
		for (int i = 0; i < newDots.length; i++) {
			if(members[i].fitness > bestDot.fitness)
				bestDot = members[i];
		}
		
		if(bestDot.brain.reached)
			minSteps = bestDot.brain.step;
		
		newDots[0] = bestDot.makeBaby();
		newDots[0].bestDot = true;
		
		for (int i = 1; i < newDots.length; i++) {
			Dot dad = selectDad();
			newDots[i] = dad.makeBaby();
		}
		members = newDots;
	}
	
	// ------------------------------------------------------------

	void mutateBabies() {
		for (int i = 1; i < members.length; i++) {
			members[i].brain.mutate(mutationRate);
		}
	}
	
	// ------------------------------------------------------------
	
	Dot selectDad() {
		float fitnessAdd = 0;
		float random = (float) ThreadLocalRandom.current().nextDouble(totalFitness);
		
		for (int i = 0; i < members.length; i++) {
			fitnessAdd += members[i].fitness;
			if(fitnessAdd > random) {
				return members[i];
			}
		}
		
		
		// never
		System.out.println("oopsie");
		List<Dot> list = Arrays.asList(members);
		Collections.sort(list, (c1, c2) -> (int)Math.signum(c1.fitness - c2.fitness));
		
		return list.get(MathUtil.random(list.size() / 256));
	}
	
	// ------------------------------------------------------------
	
	void draw(Renderer r) {
		r.fill();
		r.color(Color.MAGENTA);
		r.oval(goal.x, goal.y, goal.width, goal.height);
		r.draw();
		r.color(0);
		r.oval(goal.x, goal.y, goal.width, goal.height);
		
		if(!showOnlyBest) 
			for (int i = 1; i < members.length; i++) {
				members[i].draw(r);
			}
		members[0].draw(r);
		
		r.text(String.format("Average fitness: %.16f", averageFitness), 10, 16);
		r.text(String.format("Best fitness: %.16f", bestFitness), 10, 16 * 2);
		r.text(String.format("Worst fitness: %.16f", worstFitenss), 10, 16 * 3);
		r.text("Generation: " + Integer.toString(generation), 10, 16 * 4);
		r.text("Seed: " + seed + "  /  " + seedHash, 10, 16 * 5);
		r.text("Mutation rate: " + Float.toString(mutationRate), 10, 16 * 6 + 2);
	}
	
	// ------------------------------------------------------------
	
	void update() {
		for (int i = 0; i < members.length; i++) {
			members[i].update();
			if(members[i].brain.step > minSteps)
				members[i].brain.dead = true;
		}
	}
	
	// ------------------------------------------------------------
	
	void onlyBest(boolean is) {
		showOnlyBest = is;
	}
	
}
