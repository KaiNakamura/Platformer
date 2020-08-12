package main.java.entity;

import main.java.Constants;
import main.java.Constants.File;

public enum Sprites {
	NULL(0, 1, 0, File.NULL_SPRITE),

	PLAYER_IDLE(0, 1, 0, File.PLAYER_SPRITES),
	PLAYER_RUNNING(1, 3, 100, File.PLAYER_SPRITES),
	PLAYER_JUMPING(2, 1, 0, File.PLAYER_SPRITES),
	PLAYER_FALLING(3, 1, 0, File.PLAYER_SPRITES),
	PLAYER_LOOK_UP(4, 1, 0, File.PLAYER_SPRITES),
	PLAYER_LOOK_UP_RUNNING(5, 3, 100, File.PLAYER_SPRITES),
	PLAYER_LOOK_UP_JUMPING(6, 1, 0, File.PLAYER_SPRITES),
	PLAYER_LOOK_UP_FALLING(7, 1, 0, File.PLAYER_SPRITES),
	PLAYER_INSPECT(8, 1, 0, File.PLAYER_SPRITES),
	PLAYER_LOOK_DOWN_JUMPING(9, 1, 0, File.PLAYER_SPRITES),
	PLAYER_LOOK_DOWN_FALLING(10, 1, 0, File.PLAYER_SPRITES),

	PLAYER_REVOLVER_IDLE(0, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_RUNNING(1, 3, 100, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_JUMPING(2, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_FALLING(3, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_UP(4, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_UP_RUNNING(5, 3, 100, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_UP_JUMPING(6, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_UP_FALLING(7, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_INSPECT(8, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_DOWN_JUMPING(9, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),
	PLAYER_REVOLVER_LOOK_DOWN_FALLING(10, 1, 0, 48, 48, File.PLAYER_REVOLVER_SPRITES),

	REVOLVER_BULLET_MOVING(1, 1, 0, File.REVOLVER_SPRITES),
	REVOLVER_BULLET_FIRED(2, 1, 50, File.REVOLVER_SPRITES),
	REVOLVER_BULLET_HIT(3, 3, 50, true, File.REVOLVER_SPRITES),

	ENEMY_IDLE(0, 1, 0, 16, 16, File.ENEMY_SPRITES),
	ENEMY_RUNNING(1, 3, 100, 16, 16, File.ENEMY_SPRITES),
	ENEMY_HURT(2, 1, 50, 16, 16, File.ENEMY_SPRITES),

	BLOOD(0, 1, 0, 16, 16, File.BLOOD_SPRITES),
	EXPERIENCE(0, 1, 0, 16, 16, File.EXPERIENCE_SPRITES),

	DOOR(0, 1, 0, 16, 16, File.DOOR_SPRITES);

	private int row;
	private int numFrames;
	private long delay;
	private int width, height;
	private boolean playOnce;
	private File file;

	Sprites(
		int row,
		int numFrames,
		long delay,
		int width,
		int height,
		boolean playOnce,
		File file
	) {
		this.row = row;
		this.numFrames = numFrames;
		this.delay = delay;
		this.width = width;
		this.height = height;
		this.playOnce = playOnce;
		this.file = file;
	}

	Sprites(
		int row,
		int numFrames,
		long delay,
		int width,
		int height,
		File file
	) {
		this(
			row,
			numFrames,
			delay,
			width,
			height,
			false,
			file
		);
	}

	Sprites(
		int row,
		int numFrames,
		long delay,
		boolean playOnce,
		File file
	) {
		this(
			row,
			numFrames,
			delay,
			Constants.TILE_SIZE,
			Constants.TILE_SIZE,
			playOnce,
			file
		);
	}

	Sprites(int row, int numFrames, long delay, File file) {
		this(
			row,
			numFrames,
			delay,
			Constants.TILE_SIZE,
			Constants.TILE_SIZE,
			file
		);
	}

	public int getRow() {
		return row;
	}

	public int getNumFrames() {
		return numFrames;
	}

	public long getDelay() {
		return delay;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean shouldPlayOnce() {
		return playOnce;
	}

	public File getFile() {
		return file;
	}
}
