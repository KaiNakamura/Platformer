// TODO:
// Entity refactor
// Hitbox class?
// dt
// Pausing
// Experience orbs
// Enemy superclass
// Gui
// Winning
// Restart prompt
// Remove xMap and yMap from Entity
// More enemies
// Depth first search map generation
// Smoke particles
// Refactor some speeds to accelerations
// Experience pitch up

package main.java;

import main.java.Constants.Key;
import main.java.audio.AudioPlayer;
import main.java.gamestate.*;
import main.java.gamestate.GameStateType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable, KeyListener {
	private static Game instance = null;
	private Thread thread;
	private BufferedImage image;
	private Graphics2D graphics;
	private GameState[] gameStates;
	private GameState gameState;
	private AudioPlayer audioPlayer;
	private boolean running;
	private long targetTime = (long) 1000 / (long) Constants.FPS;
	private long delay = 0;
	private int screenShake;
	private long screenShakeStartTime, screenShakeDuration;
	
	public Game() {
		setPreferredSize(new Dimension(
			Constants.WIDTH * Constants.SCALE,
			Constants.HEIGHT * Constants.SCALE
		));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void init() {
		image = new BufferedImage(
			Constants.WIDTH,
			Constants.HEIGHT,
			BufferedImage.TYPE_INT_RGB
		);

		graphics = (Graphics2D) image.getGraphics();
		graphics.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_OFF
		);

		running = true;

		audioPlayer = new AudioPlayer();

		gameStates = new GameState[] {
			new MenuState(),
			new LevelState()
		};
		gameState = gameStates[0];
	}

	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		while (running) {
			start = System.nanoTime();
			update(System.nanoTime() - start);
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000 + delay;
			if (wait < 0) {
				wait = Constants.MINIMUM_DELAY;
			}

			try {
				Thread.sleep(wait);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			delay = 0;
		}
	}

	private void update(double dt) {
		gameState.update(dt);
		audioPlayer.update();
		resetKeys();
	}

	private void draw() {
		gameState.draw(graphics);
	}
	
	private void drawToScreen() {
		if (System.nanoTime() - screenShakeStartTime > screenShakeDuration) {
			screenShake = 0;
		}

		Graphics g = getGraphics();
		g.drawImage(
			image,
			(int) ((2 * Math.random() - 1) * screenShake),
			(int) ((2 * Math.random() - 1) * screenShake),
			Constants.WIDTH * Constants.SCALE,
			Constants.HEIGHT * Constants.SCALE,
			null
		);
		g.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		for (Key key : Key.values()) {
			if (key.equals(code)) {
				if(!key.isDown()) {
					key.setPressed(true);
				}
				key.setDown(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		for (Key key : Key.values()) {
			if (key.equals(code)) {
				key.setReleased(true);
				key.setDown(false);
			}

		}
	}

	public void resetKeys() {
		for (Key key : Key.values()) {
			key.setPressed(false);
			key.setReleased(false);
		}
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void delay(long delay) {
		this.delay += delay;
	}

	public void setScreenShake(int screenShake, long duration) {
		this.screenShake = screenShake;
		screenShakeDuration = duration * 1000000;
		screenShakeStartTime = System.nanoTime();
	}

	public void setGameState(GameStateType gameStateType) {
		for (GameState gameState : gameStates) {
			if (gameState.getGameStateType() == gameStateType) {
				this.gameState = gameState;
				return;
			}
		}

		throw new IllegalArgumentException(
			"GameState not found of GameStateType " + gameStateType
		);
	}
	
	public GameState getGameState() {
		return gameState;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
}
