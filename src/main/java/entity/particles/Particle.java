package main.java.entity.particles;

import main.java.Game;
import main.java.entity.Entity;
import main.java.tilemap.Tilemap;

public abstract class Particle extends Entity {
	protected Particle(Game game, Tilemap tilemap) {
		super(game, tilemap);
	}
}
