package main.java.gamestate;

import java.awt.*;
import java.util.ArrayList;

import main.java.Constants;
import main.java.Constants.File;
import main.java.Constants.Key;
import main.java.Game;
import main.java.entity.Door;
import main.java.entity.Entity;
import main.java.entity.Player;
import main.java.tilemap.Background;
import main.java.tilemap.Level;
import main.java.tilemap.Tilemap;
import main.java.tilemap.Tileset;

public class LevelState extends GameState {
	private static final double DEATH_TIME = 2000;

	private Tilemap tilemap;
	
	private ArrayList<Entity> entities;
	private Player player;
	private ArrayList<Door> doors;

	private long deathTimer;

	protected LevelState(Game game) {
		super(game, GameStateType.LEVEL);
		init();
	}

	public LevelState() {
		this(Game.getInstance());
	}

	@Override
	public void init() {
		tilemap = new Level(
			new Tileset(File.TILESET), new Background(File.BACKGROUND)
		);

		entities = new ArrayList<>();
		player = tilemap.getPlayer();
		doors = tilemap.getDoors();

		entities.addAll(tilemap.getDoors());
		entities.add(player);
		entities.addAll(tilemap.getEnemies());

		for (Entity entity : entities) {
			entity.init();
		}

		setTilemapPosition(false);
	}

	@Override
	public void update(double dt) {
		if (Key.ESC.isPressed()) {
			game.getAudioPlayer().stop(File.MUSIC);
			game.setGameState(GameStateType.PAUSE);
		}

		if (player.isDead()) {
			long elapsed = (System.nanoTime() - deathTimer) / 1000000;
			if (elapsed > DEATH_TIME) {
				game.setGameState(GameStateType.DEATH);
			}
		} else {
			deathTimer = System.nanoTime();
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.shouldRemove()) {
				entities.remove(i);
			} else {
				entity.update();
			}
		}

		for (Door door : doors) {
			if (door.isEntered()) {
				init();
			}
		}

		setTilemapPosition(true);
	}

	@Override
	public void draw(Graphics2D graphics) {
		tilemap.drawBackground(graphics);
		for (Entity entity : entities) {
			entity.draw(graphics);
		}
		tilemap.drawTiles(graphics);
	}

	private void setTilemapPosition(boolean smooth) {
		tilemap.setPosition(
			(int) (
				Constants.WIDTH / 2.0 - player.getX() - player.getCameraX()
			),
			(int) (
				Constants.HEIGHT / 2.0 - player.getY() - player.getCameraY()
			),
			smooth
		);
	}
}
