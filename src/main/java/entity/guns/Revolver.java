package main.java.entity.guns;

import main.java.Constants;
import main.java.Game;
import main.java.Constants.Direction;
import main.java.audio.Audio;
import main.java.entity.guns.bullets.RevolverBullet;
import main.java.tilemap.Tilemap;

public class Revolver extends Gun {
	public static final double RECOIL = 0.75;
	public static final double SPREAD = 0.5;

	protected Revolver(Game game, Tilemap tilemap) {
		super(game, tilemap);
		recoil = RECOIL;
		init();	
	}

	public Revolver(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	public void shoot(Constants.Direction direction, double x, double y) {
		double xOffset = 0, yOffset = 0;
		switch (direction) {
			case RIGHT:
				xOffset = 9;
				yOffset = -1;
				break;
			case LEFT:
				xOffset = -9;
				yOffset = -1;
				break;
			case UP:
				xOffset = 0;
				yOffset = -10;
				break;
			case DOWN:
				xOffset = 0;
				yOffset = 8;
				break;
		}

		game.getAudioPlayer().play(new Audio(Constants.File.REVOLVER_SHOOT_AUDIO));
		game.setScreenShake(6, 100);
		
		double dx = 0, dy = 0;
		if (direction == Direction.RIGHT || direction == Direction.LEFT) {
			dy = Math.random() * SPREAD - SPREAD / 2.0;
		} else {
			dx = Math.random() * SPREAD - SPREAD / 2.0;	
		}

		bullets.add(new RevolverBullet(
			tilemap,
			direction,
			x + xOffset,
			y + yOffset,
			dx,
			dy
		));
	}
}
