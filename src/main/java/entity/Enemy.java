package main.java.entity;

import main.java.Game;
import main.java.Constants.File;
import main.java.audio.Audio;
import main.java.entity.guns.bullets.Bullet;
import main.java.entity.particles.Blood;
import main.java.entity.particles.Particle;
import main.java.tilemap.Tilemap;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	private static final int HITBOX_WIDTH = 10;
	private static final int HITBOX_HEIGHT = 11;

	private static final double MOVE_SPEED = 0.5;
	private static final double MAX_MOVE_SPEED = 2.5;
	private static final double STOP_SPEED = 0.1;
	private static final double GROUND_STOP_SPEED = 0.2;

	private static final double FALL_SPEED = 0.4;
	private static final double MAX_FALL_SPEED = 4.0;

	private static final int HEALTH = 2;

	private int health;
	private int maxHealth;
	private boolean facingRight;

	private boolean hurt;

	private Animation IDLE;
	private Animation RUNNING;
	private Animation HURT;
	
	private ArrayList<Particle> particles;

	protected Enemy(Game game, Tilemap tilemap) {
		super(game, tilemap);
	}
	
	public Enemy(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;

		hitboxWidth = HITBOX_WIDTH;
		hitboxHeight = HITBOX_HEIGHT;
		
		facingRight = Math.random() > 0.5;

		health = maxHealth = HEALTH;

		IDLE = new Animation(Sprites.ENEMY_IDLE);
		RUNNING = new Animation(Sprites.ENEMY_RUNNING);
		HURT = new Animation(Sprites.ENEMY_HURT);

		setAnimation(IDLE);

		particles = new ArrayList<>();
	}

	@Override
	public void update(double dt) {
		if (!dead) {
			super.update(dt);
			getNextPosition();

			for (Bullet bullet : tilemap.getPlayer().getGun().getBullets()) {
				if (!bullet.isHit() && intersects(bullet)) {
					health -= bullet.getDamage();
					bullet.setHit(true);
					hurt = true;
					game.getAudioPlayer().play(new Audio(File.ENEMY_HIT_AUDIO));
					game.setScreenShake(4, 100);
					game.delay(20);
				}
			}

			if (health <= 0) {
				dead = true;
				game.getAudioPlayer().play(new Audio(File.ENEMY_DIE_AUDIO));
				game.setScreenShake(8, 100);
				for (int i = 0; i < 10; i++) {
					Blood blood = new Blood(tilemap, x, y, dx, dy);
					particles.add(blood);
				}
			}

			if (hurt) {
				setAnimation(HURT);
				if (animation.isPlayedOnce()) {
					hurt = false;
				}
			} else if (left ^ right) {
				setAnimation(RUNNING);
			} else {
				setAnimation(IDLE);
			}

			animation.update();
		}

		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if (particle.shouldRemove()) {
				particles.remove(i);
			} else {
				particle.update();
			}
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		if (!dead) {
			setMapPosition();

			int drawX = (int) (x + xMap - animation.getWidth() / 2.0);
			int drawY = (int) (y + yMap - animation.getWidth() / 2.0);
			
			if (facingRight) {
				graphics.drawImage(
					animation.getImage(),
					drawX,
					drawY,
					null
				);
			} else {
				graphics.drawImage(
					animation.getImage(),
					drawX + animation.getWidth(),
					drawY,
					-animation.getWidth(),
					animation.getHeight(),
					null
				);
			}
		}

		for (Particle particle : particles) {
			particle.draw(graphics);
		}
	}

	private void getNextPosition() {
		// Movement
		if (
			(topLeftHit || topRightHit) ||
			(facingRight && !bottomRightHit) ||
			(!facingRight && !bottomLeftHit)
		) {
			facingRight = !facingRight;
			dx = -dx;
		}

		left = !facingRight;
		right = facingRight;

		if (left && !right) {
			dx -= MOVE_SPEED;
			if(dx < -MAX_MOVE_SPEED) {
				dx = -MAX_MOVE_SPEED;
			}
		} else if (right && !left) {
			dx += MOVE_SPEED;
			if(dx > MAX_MOVE_SPEED) {
				dx = MAX_MOVE_SPEED;
			}
		} else {
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
		}

		// Falling
		if (falling) {
			dy += FALL_SPEED;

			if (dy > MAX_FALL_SPEED) {
				dy = MAX_FALL_SPEED;
			}

			if (dy > 0) {
				jumping = false;
			}
		}
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void resetStance() {
		setLeft(false);
		setRight(false);
		setUp(false);
		setDown(false);
	}
}
