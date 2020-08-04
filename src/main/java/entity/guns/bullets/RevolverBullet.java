package main.java.entity.guns.bullets;

import main.java.Constants;
import main.java.Game;
import main.java.entity.Animation;
import main.java.entity.Sprites;
import main.java.tilemap.Tilemap;

public class RevolverBullet extends Bullet {
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	private static final int HITBOX_WIDTH = 10;
	private static final int HITBOX_HEIGHT = 6;

	private static final double MOVE_SPEED = 8;
	private static final double DAMAGE = 1;

	private Animation NULL;
	private Animation FIRED;
	private Animation MOVING;
	private Animation HIT;

	protected RevolverBullet(
		Game game,
		Tilemap tilemap,
		Constants.Direction direction,
		double x,
		double y,
		double dx,
		double dy
	) {
		super(game, tilemap, direction, x, y, dx, dy);
		damage = DAMAGE;
		init();
	}

	public RevolverBullet(
		Tilemap tilemap,
		Constants.Direction direction,
		double x,
		double y,
		double dx,
		double dy
	) {
		this(Game.getInstance(), tilemap, direction, x, y, dx, dy);
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;

		hitboxWidth = HITBOX_WIDTH;
		hitboxHeight = HITBOX_HEIGHT;

		NULL = new Animation(Sprites.NULL);
		FIRED = new Animation(Sprites.REVOLVER_BULLET_FIRED);
		MOVING = new Animation(Sprites.REVOLVER_BULLET_MOVING);
		HIT = new Animation(Sprites.REVOLVER_BULLET_HIT);

		setAnimation(MOVING);

		switch (direction) {
			case RIGHT:
				dx += MOVE_SPEED;
				break;
			case LEFT:
				dx -= MOVE_SPEED;
				break;
			case UP:
				dy -= MOVE_SPEED;
				break;
			default:
				dy += MOVE_SPEED;
				break;
		}
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		if (hit) {
			if (animation.isPlayedOnce()) {
				shouldRemove = true;
			} else {
				setAnimation(HIT);
			}
		} else {
			setAnimation(MOVING);	
		}

		if (shouldRemove) {
			setAnimation(NULL);
		}

		animation.update();
	}
}
