package main.java.entity.particles;

import java.awt.*;
import java.util.ArrayList;

public class ParticleEmitter {
	private ArrayList<Particle> particles;

	public ParticleEmitter() {
		particles = new ArrayList<>();
	}

	public void update(double dt) {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if (particle.shouldRemove()) {
				particles.remove(i);
			} else {
				particle.update();
			}
		}
	}

	public void draw(Graphics2D graphics) {
		for (Particle particle : particles) {
			particle.draw(graphics);
		}
	}

	public void add(Particle particle) {
		particles.add(particle);
	}
}
