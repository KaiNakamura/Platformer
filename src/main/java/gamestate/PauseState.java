package main.java.gamestate;

import main.java.Constants;
import main.java.Constants.File;
import main.java.Constants.Key;
import main.java.audio.Audio;
import main.java.Game;
import main.java.tilemap.Background;

import java.awt.*;

public class PauseState extends GameState {
	private Background background;
	private Color color;
	private Font font;

	protected PauseState(Game game) {
		super(game, GameStateType.PAUSE);
		init();
	}

	public PauseState() {
		this(Game.getInstance());
	}

	@Override
	public void init() {
		try {
			background = new Background(new Color(0, 0, 0, 128));
			background.setVelocity(-0.1, 0);

			color = new Color(128, 0, 0);
			font = new Font("Arial", Font.PLAIN, 24);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(double dt) {
		if (Key.PAUSE.isPressed()) {
			game.getAudioPlayer().play(new Audio(File.MUSIC));
			game.setGameState(GameStateType.LEVEL);
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
		graphics.setColor(color);
		graphics.setFont(font);
		drawCenteredString(
			graphics,
			"Paused",
			font,
			new Rectangle(0, 0, Constants.WIDTH, Constants.HEIGHT)
		);
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
}
