package main.java.entity;

import main.java.Constants.Camera;
import main.java.Constants.Direction;
import main.java.Constants.File;
import main.java.Constants.Key;
import main.java.Game;
import main.java.audio.Audio;
import main.java.entity.guns.Gun;
import main.java.entity.guns.Revolver;
import main.java.entity.particles.Blood;
import main.java.entity.particles.ParticleEmitter;
import main.java.tilemap.Tilemap;

import java.awt.*;

public class Player extends Entity {
	private static final int WIDTH = 12;
	private static final int HEIGHT = 16;

	private static final double MOVE_SPEED = 0.5;
	private static final double MAX_MOVE_SPEED = 2.5;
	private static final double STOP_SPEED = 0.1;
	private static final double GROUND_STOP_SPEED = 0.2;

	private static final double JUMP_START = 5.5;
	private static final double JUMP_STOP = 0.1;
	private static final double FALL_SPEED = 0.4;
	private static final double JUMP_FALL_SPEED = 0.25;
	private static final double MAX_FALL_SPEED = 4.0;

	private static final int HEALTH = 5;
	private static final double INVINCIBILITY_TIME = 1000;

	private int health, maxHealth;
	private int experience;

	private double cameraX, cameraY;

	private Gun gun;

	private boolean shooting, inspecting;

	private boolean hurt;
	private long hurtTimer;

	private boolean facingRight;

	private Animation IDLE;
	private Animation RUNNING;
	private Animation JUMPING;
	private Animation FALLING;
	private Animation LOOK_UP;
	private Animation LOOK_UP_RUNNING;
	private Animation LOOK_UP_JUMPING;
	private Animation LOOK_UP_FALLING;
	private Animation INSPECT;
	private Animation LOOK_DOWN_JUMPING;
	private Animation LOOK_DOWN_FALLING;

	private ParticleEmitter particleEmitter;

	protected Player(Game game, Tilemap tilemap) {
		super(game, tilemap);
	}
	
	public Player(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;
		
		facingRight = true;

		health = maxHealth = HEALTH;

		cameraX = facingRight ? Camera.MAX_X : Camera.MIN_X;
		cameraY = Camera.OFFSET_Y;

		gun = new Revolver(tilemap);

		IDLE = new Animation(Sprites.PLAYER_REVOLVER_IDLE);
		RUNNING = new Animation(Sprites.PLAYER_REVOLVER_RUNNING);
		JUMPING = new Animation(Sprites.PLAYER_REVOLVER_JUMPING);
		FALLING = new Animation(Sprites.PLAYER_REVOLVER_FALLING);
		LOOK_UP = new Animation(Sprites.PLAYER_REVOLVER_LOOK_UP);
		LOOK_UP_RUNNING = new Animation(
			Sprites.PLAYER_REVOLVER_LOOK_UP_RUNNING
		);
		LOOK_UP_JUMPING = new Animation(
			Sprites.PLAYER_REVOLVER_LOOK_UP_JUMPING
		);
		LOOK_UP_FALLING = new Animation(
			Sprites.PLAYER_REVOLVER_LOOK_UP_FALLING
		);
		INSPECT = new Animation(Sprites.PLAYER_REVOLVER_INSPECT);
		LOOK_DOWN_JUMPING = new Animation(
			Sprites.PLAYER_REVOLVER_LOOK_DOWN_JUMPING
		);
		LOOK_DOWN_FALLING = new Animation(
			Sprites.PLAYER_REVOLVER_LOOK_DOWN_FALLING
		);

		setAnimation(IDLE);

		particleEmitter = new ParticleEmitter();
	}

	@Override
	public void update(double dt) {
		if (!dead) {
			super.update(dt);
			getNextPosition();
			getNextCameraPosition();

			if (hurt) {
				long elapsed = (System.nanoTime() - hurtTimer) / 1000000;
				if (elapsed > INVINCIBILITY_TIME) {
					hurt = false;
				}
			} else {
				for (Enemy enemy : tilemap.getEnemies()) {
					if (!enemy.isDead() && intersects(enemy)) {
						health -= enemy.getDamage();
						if (health <= 0) {
							dead = true;
							game.getAudioPlayer().play(
								new Audio(File.PLAYER_DIE_AUDIO)
							);
							game.getAudioPlayer().stop(File.MUSIC);
							game.setScreenShake(8, 300);
							game.delay(30);
							for (int i = 0; i < 20; i++) {
								Blood blood = new Blood(tilemap, x, y, dx, dy);
								particleEmitter.add(blood);
							}
						} else {
							hurt = true;
							hurtTimer = System.nanoTime();
							game.getAudioPlayer().play(
								new Audio(File.PLAYER_HIT_AUDIO)
							);
							game.setScreenShake(8, 100);
							game.delay(25);
						}
					}
				}
			}

			if (shooting) {
				Direction direction;
				if (up) {
					direction = Direction.UP;
				} else if (down && falling) {
					direction = Direction.DOWN;
				} else {
					if (facingRight) {
						direction = Direction.RIGHT;
						dx -= gun.getRecoil();
					} else {
						direction = Direction.LEFT;
						dx += gun.getRecoil();
					}
				}
				gun.shoot(direction, x, y);
			}

			// Set animation
			if (right && !left) {
				facingRight = true;
			} else if (left && !right) {
				facingRight = false;
			}

			if (left || right || up || jumping || falling || shooting) {
				inspecting = false;
			}

			if (falling) {
				if (jumping) {
					if (up) {
						setAnimation(LOOK_UP_JUMPING);
					} else if (down) {
						setAnimation(LOOK_DOWN_JUMPING);
					} else {
						setAnimation(JUMPING);
					}
				} else {
					if (up) {
						setAnimation(LOOK_UP_FALLING);
					} else if (down) {
						setAnimation(LOOK_DOWN_FALLING);
					} else {
						setAnimation(FALLING);
					}
				}
			} else if (left ^ right) {
				if (up) {
					setAnimation(LOOK_UP_RUNNING);
				} else {
					setAnimation(RUNNING);
				}
			} else if (up) {
				setAnimation(LOOK_UP);
			} else if (inspecting) {
				setAnimation(INSPECT);
			} else {
				setAnimation(IDLE);
			}

			animation.update();
		}

		gun.update(dt);
		particleEmitter.update(dt);
	}

	@Override
	public void draw(Graphics2D graphics) {
		if (!dead) {
			if (hurt) {
				long elapsed = (System.nanoTime() - hurtTimer) / 1000000;
				if (elapsed / 100 % 2 == 0) {
					return;
				}
			}

			int drawX = (int) (x + tilemap.getX() - animation.getWidth() / 2.0);
			int drawY = (int) (y + tilemap.getY() - animation.getWidth() / 2.0);

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

		gun.draw(graphics);
		particleEmitter.draw(graphics);
	}

	private void getNextPosition() {
		left = Key.LEFT.isDown();
		right = Key.RIGHT.isDown();
		up = Key.UP.isDown();
		down = Key.DOWN.isDown();
		jumping = Key.JUMP.isDown();
		shooting = Key.SHOOT.isPressed();

		if (Key.DOWN.isPressed()) {
			inspecting = true;
		}

		// Movement
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
		}

		// Jumping
		if (jumping && !falling) {
			dy = -JUMP_START;
			falling = true;
			game.getAudioPlayer().play(new Audio(File.PLAYER_JUMP_AUDIO));
		}

		// Falling
		if (falling) {
			if (jumping) {
				dy += JUMP_FALL_SPEED;
			} else {
				dy += FALL_SPEED;
			}

			if (dy < 0 && !jumping) {
				dy += JUMP_STOP;
			}

			if (dy > MAX_FALL_SPEED) {
				dy = MAX_FALL_SPEED;
			}

			if (dy > 0) {
				jumping = false;
			}
		}

		// Landing
		if (landing) {
			game.getAudioPlayer().play(new Audio(File.PLAYER_LAND_AUDIO));
		}
	}

	private void getNextCameraPosition() {
		if (up) {
			cameraY -= Camera.SPEED;
		} else if (down && falling || inspecting) {
			cameraY += Camera.SPEED;
		} else {
			if (facingRight) {
				cameraX += Camera.SPEED;
			} else {
				cameraX -= Camera.SPEED;
			}

			if (cameraY > Camera.OFFSET_Y) {
				cameraY -= Camera.SPEED;
			} else if (cameraY < Camera.OFFSET_Y) {
				cameraY += Camera.SPEED;
			}
		}

		if (cameraX > Camera.MAX_X) {
			cameraX = Camera.MAX_X;
		} else if (cameraX < Camera.MIN_X) {
			cameraX = Camera.MIN_X;
		}
		if (cameraY > Camera.MAX_Y) {
			cameraY = Camera.MAX_Y;
		} else if (cameraY < Camera.MIN_Y) {
			cameraY = Camera.MIN_Y;
		}
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getExperience() {
		return experience;
	}

	public double getCameraX() {
		return cameraX;
	}

	public double getCameraY() {
		return cameraY;
	}

	public Gun getGun() {
		return gun;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public boolean isInspecting() {
		return inspecting;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void addExperience(int experience) {
		this.experience += experience;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;	
	}

	public void setInspecting(boolean inspecting) {
		this.inspecting = inspecting;
	}

	public void resetStance() {
		setLeft(false);
		setRight(false);
		setUp(false);
		setDown(false);
		setInspecting(false);
		setJumping(false);
	}
}
