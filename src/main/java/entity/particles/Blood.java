package main.java.entity.particles;

import main.java.Game;
import main.java.entity.Animation;
import main.java.entity.Sprites;
import main.java.tilemap.Tilemap;

public class Blood extends Particle {
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	private static final int HITBOX_WIDTH = 2;
	private static final int HITBOX_HEIGHT = 2;

	private static final double FALL_SPEED = 0.2;
	private static final double MAX_FALL_SPEED = 2.0;
	private static final double STOP_SPEED = 0.0;
	private static final double GROUND_STOP_SPEED = 0.1;

	protected Blood(Game game, Tilemap tilemap) {
		super(game, tilemap);
		init();
	}

	public Blood(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	public Blood(Tilemap tilemap, double x, double y, double dx, double dy) {
		this(Game.getInstance(), tilemap);
		setPosition(x, y);
		dx += dx;
		dy += dy;
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;

		hitboxWidth = HITBOX_WIDTH;
		hitboxHeight = HITBOX_HEIGHT;

		setAnimation(new Animation(Sprites.BLOOD));

		dy -= Math.random() * 3;
		dx += Math.random();
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		if (dx > 0) {
			if (bottomLeftHit || bottomRightHit) {
				dx -= GROUND_STOP_SPEED;
			} else {
				dx -= STOP_SPEED;
			}

			if (dx < 0) {
				dx = 0;
			}
		} else if (dx < 0) {
			if (bottomLeftHit || bottomRightHit) {
				dx += GROUND_STOP_SPEED;
			} else {
				dx += STOP_SPEED;
			}

			if (dx > 0) {
				dx = 0;
			}
		}

		if (falling) {
			dy += FALL_SPEED;

			if (dy > MAX_FALL_SPEED) {
				dy = MAX_FALL_SPEED;
			}
		}
	}
}
