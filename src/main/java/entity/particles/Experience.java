package main.java.entity.particles;

import main.java.Game;
import main.java.Constants.File;
import main.java.audio.Audio;
import main.java.entity.Animation;
import main.java.entity.Player;
import main.java.entity.Sprites;
import main.java.tilemap.Tilemap;

public class Experience extends Particle {
	private static final int WIDTH = 4;
	private static final int HEIGHT = 4;

	private static final double STOP_SPEED = 0.02;
	private static final double GROUND_STOP_SPEED = 0.1;

	private static final double COLLECTION_DISTANCE = 64;
	private static final double COLLECTION_SPEED = 3.0;
	private static final double COLLECTION_TIME = 500;

	private int value;

	private boolean collectable;
	private long collectTimer;

	protected Experience(Game game, Tilemap tilemap) {
		super(game, tilemap);
		init();
	}

	public Experience(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	public Experience(Tilemap tilemap, double x, double y, double dx, double dy) {
		this(Game.getInstance(), tilemap);
		setPosition(x, y);
		dx += dx;
		dy += dy;
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;

		setAnimation(new Animation(Sprites.EXPERIENCE));

		dy -= Math.random();
		dx += Math.random() * 2 - 1;

		collectTimer = System.nanoTime();

		value = 1;
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		double xDistance = tilemap.getPlayer().getX() - x;
		double yDistance = tilemap.getPlayer().getY() - y;
		double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

		long elapsed = (System.nanoTime() - collectTimer) / 1000000;
		if (elapsed > COLLECTION_TIME) {
			collectable = true;
		}

		if (collectable && distance < COLLECTION_DISTANCE) {
			dx = COLLECTION_SPEED * xDistance / distance;
			dy = COLLECTION_SPEED * yDistance / distance;

			Player player = tilemap.getPlayer();
			if (intersects(player)) {
				player.addExperience(value);
				game.getAudioPlayer().play(new Audio(File.EXPERIENCE_AUDIO));
				shouldRemove = true;
			}
		}

		if (dx > 0) {
			if (bottomHit()) {
				dx -= GROUND_STOP_SPEED;
			} else {
				dx -= STOP_SPEED;
			}

			if (dx < 0) {
				dx = 0;
			}
		} else if (dx < 0) {
			if (bottomHit()) {
				dx += GROUND_STOP_SPEED;
			} else {
				dx += STOP_SPEED;
			}

			if (dx > 0) {
				dx = 0;
			}
		}

		if (dy > 0) {
			dy -= STOP_SPEED;

			if (dy < 0) {
				dy = 0;
			}
		} else if (dy < 0) {
			dy += STOP_SPEED;

			if (dy > 0) {
				dy = STOP_SPEED;
			}
		}
	}
}
