package main.java.gamestate;

import main.java.Constants;
import main.java.Constants.File;
import main.java.audio.Audio;
import main.java.Game;
import main.java.tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	public enum MenuChoice {
		NEW("New"), LOAD("Load"), QUIT("Quit");

		private String s;
		MenuChoice(String s) {
			this.s = s;
		}

		public String toString() {
			return s;
		}
	}

	private Background background;
	private Color titleColor;
	private Font titleFont, font;
	private MenuChoice menuChoice;

	protected MenuState(Game game) {
		super(game, GameStateType.MENU);
		init();
	}

	public MenuState() {
		this(Game.getInstance());
	}

	@Override
	public void init() {
		try {
			background = new Background(File.BACKGROUND, 1);
			background.setVelocity(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 12);

			menuChoice = MenuChoice.NEW;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(double dt) {
		background.update(dt);
	}

	@Override
	public void draw(Graphics2D graphics) {
		// Draw background
		background.draw(graphics);

		// Draw title
		graphics.setColor(titleColor);
		graphics.setFont(titleFont);
		drawCenteredString(
			graphics,
			"Game",
			titleFont,
			new Rectangle(0, 0, Constants.WIDTH, Constants.HEIGHT / 2)
		);

		// Draw menu choices
		graphics.setFont(font);
		for (MenuChoice menuChoice : MenuChoice.values()) {
			if (this.menuChoice == menuChoice) {
				graphics.setColor(Color.BLACK);
			} else {
				graphics.setColor(Color.RED);
			}
			drawCenteredString(
				graphics,
				menuChoice.toString(),
				font,
				new Rectangle(
					0,
					menuChoice.ordinal() * 15,
					Constants.WIDTH,
					Constants.HEIGHT
				)
			);
		}
	}

	public void drawCenteredString(
		Graphics2D graphics,
		String text,
		Font font,
		Rectangle rect
	) {
		// Get the FontMetrics
		FontMetrics metrics = graphics.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x =	rect.x +
				(rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text
		int y =	rect.y +
				((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		graphics.setFont(font);
		// Draw the String
		graphics.drawString(text, x, y);
	}

	public void select() {
		switch (menuChoice) {
			case NEW:
				game.setGameState(GameStateType.LEVEL);
				game.getAudioPlayer().play(new Audio(File.MUSIC, true));
				break;
			case LOAD:
				break;
			case QUIT:
				System.exit(0);
		}
	}

	public void moveChoice(int dir) {
		int newOrdinal =	(menuChoice.ordinal() - dir) %
							MenuChoice.values().length;

		if (newOrdinal == -1) {
			newOrdinal = MenuChoice.values().length - 1;
		}

		for (MenuChoice menuChoice : MenuChoice.values()) {
			if (menuChoice.ordinal() == newOrdinal) {
				this.menuChoice = menuChoice;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (Constants.Key.JUMP.equals(code)) {
			select();
		} else if (Constants.Key.UP.equals(code)) {
			moveChoice(1);
		} else if (Constants.Key.DOWN.equals(code)) {
			moveChoice(-1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
