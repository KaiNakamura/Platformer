package main.java;

import java.awt.event.KeyEvent;

public class Constants {
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;

	public static final int TILE_SIZE = 16;

	public static final int FPS = 60;
	public static final int MINIMUM_DELAY = 5;

	public static class Camera {
		public static final double FOLLOW = 0.1;
		public static final double PARALLAX = 0.1;
		public static final double SPEED = 2.0;
		public static final double OFFSET_X = 0;
		public static final double OFFSET_Y = -16;
		public static final double MIN_X = -32;
		public static final double MAX_X = 32;
		public static final double MIN_Y = -48;
		public static final double MAX_Y = 16;
	}

	public enum Direction {
		UP, RIGHT, DOWN, LEFT;

		public static Direction getOppositeDirection(Direction direction) {
			switch (direction) {
				case UP:
					return DOWN;
				case RIGHT:
					return LEFT;
				case DOWN:
					return UP;
				default:
					return RIGHT;
			}
		}
	}

	public enum File
	{
		NULL_SPRITE("sprites/null.png"),
		TILESET("tilesets/tileset.png"),
		BACKGROUND("backgrounds/background.png"),
		PLAYER_SPRITES("sprites/player/player.png"),
		PLAYER_REVOLVER_SPRITES("sprites/player/player_revolver.png"),
		REVOLVER_SPRITES("sprites/guns/revolver.png"),
		ENEMY_SPRITES("sprites/enemies/enemy.png"),
		BLOOD_SPRITES("sprites/particles/blood.png"),
		EXPERIENCE_SPRITES("sprites/particles/experience.png"),
		DOOR_SPRITES("sprites/door.png"),

		PLAYER_JUMP_AUDIO("audio/playerjump.wav"),
		PLAYER_LAND_AUDIO("audio/playerland.wav"),
		PLAYER_HIT_AUDIO("audio/playerhit.wav"),
		PLAYER_DIE_AUDIO("audio/playerdie.wav"),
		REVOLVER_SHOOT_AUDIO("audio/revolvershoot.wav"),
		ENEMY_HIT_AUDIO("audio/enemyhit.wav"),
		ENEMY_DIE_AUDIO("audio/enemydie.wav"),
		EXPERIENCE_AUDIO("audio/experience.wav"),
		MUSIC("audio/music.wav"),

		ALL_WALLS_1("maps/allwalls1.csv"),
		NO_WALLS_1("maps/nowalls1.csv"),
		TOP_1("maps/top1.csv"),
		RIGHT_1("maps/right1.csv"),
		BOTTOM_1("maps/bottom1.csv"),
		LEFT_1("maps/left1.csv"),
		TOP_LEFT_1("maps/topleft1.csv"),
		TOP_RIGHT_1("maps/topright1.csv"),
		BOTTOM_RIGHT_1("maps/bottomright1.csv"),
		BOTTOM_LEFT_1("maps/bottomleft1.csv"),
		NO_BOTTOM_1("maps/nobottom1.csv"),
		NO_LEFT_1("maps/noleft1.csv"),
		NO_TOP_1("maps/notop1.csv"),
		NO_RIGHT_1("maps/noright1.csv"),
		TOP_BOTTOM_1("maps/topbottom1.csv"),
		LEFT_RIGHT_1("maps/leftright1.csv");

		private String path;
		private java.io.File file;

		File(String path) {
			this.path = path;
			try {
				this.file = new java.io.File(
					getClass().getClassLoader().getResource(this.path).toURI()
				);
			} catch(Exception e) {
				System.out.println("File not found with path: " + path);
				e.printStackTrace();
			}
		}

		public String getPath() {
			return path;
		}

		public java.io.File getFile() {
			return file;
		}
	}

	public enum Key {
		LEFT(KeyEvent.VK_LEFT, KeyEvent.VK_A),
		RIGHT(KeyEvent.VK_RIGHT, KeyEvent.VK_D),
		UP(KeyEvent.VK_UP, KeyEvent.VK_W),
		DOWN(KeyEvent.VK_DOWN, KeyEvent.VK_S),
		JUMP(KeyEvent.VK_Z, KeyEvent.VK_SPACE),
		SHOOT(KeyEvent.VK_X),
		ESC(KeyEvent.VK_ESCAPE);

		private int[] codes;
		private boolean pressed, lastPressed;
		private boolean released;
		private boolean down;

		Key(int... codes) {
			this.codes = codes;
		}

		public int[] getCodes() {
			return codes;
		}

		public boolean equals(int code) {
			for (int i = 0; i < codes.length; i++) {
				if (code == codes[i]) {
					return true;
				}
			}
			return false;
		}

		public boolean isPressed() {
			return pressed;
		}

		public boolean isReleased() {
			return released;
		}

		public boolean isDown() {
			return down;
		}

		public boolean getLastPressed() {
			return lastPressed;
		}

		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}

		public void setReleased(boolean released) {
			this.released = released;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public void setLastPressed(boolean lastPressed) {
			this.lastPressed = lastPressed;
		}
	}
}
