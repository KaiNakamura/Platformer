package main.java.entity.guns.bullets;

import main.java.Constants;
import main.java.Game;
import main.java.entity.Entity;
import main.java.tilemap.Tilemap;

import java.awt.*;

public abstract class Bullet extends Entity {
	protected Constants.Direction direction;
	protected boolean fired, hit;
	protected int damage;

	protected Bullet(
		Game game,
		Tilemap tilemap,
		Constants.Direction direction,
		double x,
		double y,
		double dx,
		double dy
	) {
		super(game, tilemap);
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		fired = false;
		hit = false;
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		if (hit) {
			dx = 0;
			dy = 0;
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		setMapPosition();

		int drawX = (int) (x + xMap - animation.getWidth() / 2.0);
		int drawY = (int) (y + yMap - animation.getWidth() / 2.0);

		switch (direction) {
			case RIGHT:
				graphics.drawImage(animation.getImage(), drawX, drawY, null);
				break;
			case LEFT:
				graphics.drawImage(
					animation.getImage(),
					drawX + animation.getWidth(),
					drawY,
					-animation.getWidth(),
					animation.getHeight(),
					null
				);
				break;
			case UP:
				graphics.translate(drawX, drawY);
				graphics.rotate(-Math.PI / 2);
				graphics.drawImage(
					animation.getImage(),
					-animation.getWidth(),
					0,
					null
				);
				graphics.rotate(Math.PI / 2);
				graphics.translate(-drawX, -drawY);
				break;
			default:
				graphics.translate(drawX, drawY);
				graphics.rotate(Math.PI / 2);
				graphics.drawImage(
					animation.getImage(),
					0,
					-animation.getHeight(),
					null);
				graphics.rotate(-Math.PI / 2);
				graphics.translate(-drawX, -drawY);
				break;
		}
	}

	@Override
	protected void calculateCornerHits(double x, double y) {
		super.calculateCornerHits(x, y);
		if (hit()) {
			hit = true;
		}
	}

	public Constants.Direction getDirection() {
		return direction;
	}

	public boolean isHit() {
		return hit;
	}

	public int getDamage() {
		return damage;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
}
