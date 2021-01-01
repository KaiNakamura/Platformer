package main.java.gamestate;

import main.java.Constants;
import main.java.Constants.File;
import main.java.Constants.Key;
import main.java.audio.Audio;
import main.java.Game;
import main.java.tilemap.Background;

import java.awt.*;

public class DeathState extends GameState {
	public enum DeathChoice {
		RETRY("Retry?"), QUIT("Quit");

		private String s;
		DeathChoice(String s) {
			this.s = s;
		}

		public String toString() {
			return s;
		}
	}

	private Background background;
	private Color titleColor, selectedColor, color;
	private Font titleFont, font;
	private DeathChoice deathChoice;

	protected DeathState(Game game) {
		super(game, GameStateType.DEATH);
		init();
	}

	public DeathState() {
		this(Game.getInstance());
	}

	@Override
	public void init() {
		try {
			background = new Background(new Color(0, 0, 0, 128));
			background.setVelocity(-0.1, 0);

			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Arial", Font.PLAIN, 48);

			selectedColor = Color.WHITE;
			color = Color.RED;
			font = new Font("Arial", Font.PLAIN, 12);
			
			deathChoice = DeathChoice.RETRY;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(double dt) {
		if (Key.JUMP.isPressed()) {
			select();
		} else if (Key.UP.isPressed()) {
			moveChoice(1);
		} else if (Key.DOWN.isPressed()) {
			moveChoice(-1);
		}

		background.update(dt);
	}

	@Override
	public void draw(Graphics2D graphics) {
		// Draw game
		game.getGameState(GameStateType.LEVEL).draw(graphics);

		// Draw background
		background.draw(graphics);

		// Draw title
		graphics.setColor(titleColor);
		graphics.setFont(titleFont);
		drawCenteredString(
			graphics,
			"You Died",
			titleFont,
			new Rectangle(0, 0, Constants.WIDTH, Constants.HEIGHT / 2)
		);

		// Draw choices
		graphics.setFont(font);
		for (DeathChoice deathChoice : DeathChoice.values()) {
			if (this.deathChoice == deathChoice) {
				graphics.setColor(selectedColor);
			} else {
				graphics.setColor(color);
			}
			drawCenteredString(
				graphics,
				deathChoice.toString(),
				font,
				new Rectangle(
					0,
					deathChoice.ordinal() * 15,
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
		switch (deathChoice) {
			case RETRY:
				game.setGameState(GameStateType.LEVEL);
				game.getGameState().init();
				game.getAudioPlayer().play(new Audio(File.MUSIC, true));
				break;
			case QUIT:
				game.setGameState(GameStateType.MENU);
		}
	}

	public void moveChoice(int dir) {
		int newOrdinal =	(deathChoice.ordinal() - dir) %
							DeathChoice.values().length;

		if (newOrdinal == -1) {
			newOrdinal = DeathChoice.values().length - 1;
		}

		for (DeathChoice deathChoice : DeathChoice.values()) {
			if (deathChoice.ordinal() == newOrdinal) {
				this.deathChoice = deathChoice;
			}
		}
	}
}
