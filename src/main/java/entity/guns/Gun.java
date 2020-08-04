package main.java.entity.guns;

import main.java.Constants;
import main.java.Game;
import main.java.entity.guns.bullets.Bullet;
import main.java.tilemap.Tilemap;

import java.awt.*;
import java.util.ArrayList;

public abstract class Gun {
	protected Game game;
	protected Tilemap tilemap;

	protected ArrayList<Bullet> bullets;

	protected double recoil;

	protected Gun(
		Game game,
		Tilemap tilemap		
	) {
		this.game = game;
		this.tilemap = tilemap;		
	}

	public void init() {
		bullets = new ArrayList<>();
	}
	
	public void update(double dt) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if (bullet.shouldRemove()) {
				bullets.remove(i);
			} else {
				bullet.update(dt);
			}
		}
	}

	public void update() {
		update(Constants.MINIMUM_DELAY);
	}

	public void draw(Graphics2D graphics) {
		for (Bullet bullet : bullets) {
			bullet.draw(graphics);
		}
	}

	public abstract void shoot(
		Constants.Direction direction,
		double x,
		double y
	);

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public double getRecoil() {
		return recoil;
	}
}
