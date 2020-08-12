package main.java.entity;

import main.java.Game;
import main.java.tilemap.Tilemap;

public class Door extends Entity {
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	
	private boolean entered;

	protected Door(Game game, Tilemap tilemap) {
		super(game, tilemap);
	}

	public Door(Tilemap tilemap) {
		this(Game.getInstance(), tilemap);
	}

	@Override
	public void init() {
		width = WIDTH;
		height = HEIGHT;

		setAnimation(new Animation(Sprites.DOOR));
	}

	@Override
	public void update(double dt) {
		Player player = tilemap.getPlayer();
		if (intersects(player) && player.isInspecting()) {
			entered = true;
		}
	}

	public boolean isEntered() {
		return entered;
	}
}
