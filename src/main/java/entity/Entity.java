package main.java.entity;

import main.java.Constants;
import main.java.Game;
import main.java.tilemap.TileType;
import main.java.tilemap.Tilemap;

import java.awt.*;

public abstract class Entity {
	protected Game game;

	protected Tilemap tilemap;
	protected double xMap, yMap;

	protected double x, y, dx, dy;
	protected int width, height;

	protected int hitboxWidth, hitboxHeight;
	protected boolean topLeftHit, topRightHit, bottomLeftHit, bottomRightHit;

	protected Animation animation;

	protected boolean dead, shouldRemove;
	protected boolean left, right, up, down, jumping, falling, landing;

	protected Entity(Game game, Tilemap tilemap) {
		this.game = game;
		this.tilemap = tilemap;
	}

	public void init() {
		
	}

	public void update(double dt) {
		int currentCol = (int) x / Constants.TILE_SIZE;
		int currentRow = (int) y / Constants.TILE_SIZE;

		double xNext = x + dx;
		double yNext = y + dy;

		double xTemp = x;
		double yTemp = y;

		landing = false;

		// Check up and down
		calculateCornerHits(x, yNext);

		if (dy < 0) {
			// Top corners solid
			if (topLeftHit || topRightHit) {
				dy = 0;
				yTemp = currentRow * Constants.TILE_SIZE + hitboxHeight / 2.0;
			} else {
				yTemp += dy;
			}
		} else if (dy > 0) {
			// Bottom corners solid
			if (bottomLeftHit || bottomRightHit) {
				dy = 0;
				if (falling) {
					falling = false;
					landing = true;
				}
				yTemp = (currentRow + 1) * Constants.TILE_SIZE - hitboxHeight / 2.0;
			} else {
				yTemp += dy;
			}
		}

		// Check left and right
		calculateCornerHits(xNext, y);

		if (dx < 0) {
			if (topLeftHit || bottomLeftHit) {
				dx = 0;
				xTemp = currentCol * Constants.TILE_SIZE + hitboxWidth / 2.0;
			} else {
				xTemp += dx;
			}
		} else if (dx > 0) {
			if (topRightHit || bottomRightHit) {
				dx = 0;
				xTemp = (currentCol + 1) * Constants.TILE_SIZE - hitboxWidth / 2.0;
			}
			else {
				xTemp += dx;
			}
		}

		if (!falling) {
			calculateCornerHits(x, yNext + 1);
			if (!bottomLeftHit && !bottomRightHit) {
				falling = true;
			}
		}

		calculateCornerHits(xNext, yNext + 1);

		x = xTemp;
		y = yTemp;
	}

	public void update() {
		update(Constants.MINIMUM_DELAY);
	}

	public void draw(Graphics2D graphics) {
		setMapPosition();

		int drawX = (int) (x + xMap - animation.getWidth() / 2.0);
		int drawY = (int) (y + yMap - animation.getWidth() / 2.0);

		graphics.drawImage(
			animation.getImage(),
			drawX,
			drawY,
			null
		);
	}

	public boolean intersects(Entity o) {
		Rectangle thisRect = getRectangle();
		Rectangle otherRect = o.getRectangle();
		return thisRect.intersects(otherRect);
	}

	protected void calculateCornerHits(double x, double y) {
		int left = (int)(x - hitboxWidth / 2.0) / Constants.TILE_SIZE;
		int right = (int)(x + hitboxWidth / 2.0 - 1) / Constants.TILE_SIZE;
		int top = (int)(y - hitboxHeight / 2.0) / Constants.TILE_SIZE;
		int bottom = (int)(y + hitboxHeight / 2.0 - 1) / Constants.TILE_SIZE;

		topLeftHit = tilemap.getTileType(top, left) == TileType.TERRAIN;
		topRightHit = tilemap.getTileType(top, right) == TileType.TERRAIN;
		bottomLeftHit = tilemap.getTileType(bottom, left) == TileType.TERRAIN;
		bottomRightHit = tilemap.getTileType(bottom, right) == TileType.TERRAIN;
	}

	public boolean isOnScreen() {
		double xFinal = x + xMap;
		double yFinal = y + yMap;

		return !(	xFinal + width < 0 ||
					xFinal - width > Constants.WIDTH ||
					yFinal + height < 0 ||
					yFinal - height > Constants.HEIGHT
		);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getHitboxWidth() {
		return hitboxWidth;
	}

	public int getHitboxHeight() {
		return hitboxHeight;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, hitboxWidth, hitboxHeight);
	}

	public boolean isMovingLeft() {
		return left;
	}

	public boolean isMovingRight() {
		return right;
	}

	public boolean isMovingUp() {
		return up;
	}

	public boolean isMovingDown() {
		return down;
	}

	public boolean isFalling() {
		return falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public boolean isLanding() {
		return landing;
	}
	
	public boolean isDead() {
		return dead;
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public Tilemap getTilemap() {
		return tilemap;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setPosition(double x, double y) {
		setX(x);
		setY(y);
	}

	public void setVelocity(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setLanding(boolean landing) {
		this.landing = landing;
	}

	public void setTilemap(Tilemap tilemap) {
		this.tilemap = tilemap;
	}

	public void setMapPosition() {
		xMap = tilemap.getX();
		yMap = tilemap.getY();
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}
