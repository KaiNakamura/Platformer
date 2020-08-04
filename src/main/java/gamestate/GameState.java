package main.java.gamestate;

import main.java.Constants;
import main.java.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameState {
	protected Game game;
	protected GameStateType gameStateType;

	protected GameState(Game game, GameStateType gameStateType) {
		this.game = game;
		this.gameStateType = gameStateType;
	}
	
	public GameState(GameStateType gameStateType) {
		this(Game.getInstance(), gameStateType);
	}

	public abstract void init();

	public abstract void update(double dt);

	public void update() {
		update(Constants.MINIMUM_DELAY);
	}

	public abstract void draw(Graphics2D graphics);

	public abstract void keyPressed(KeyEvent e);

	public abstract void keyReleased(KeyEvent e);

	public GameStateType getGameStateType() {
		return gameStateType;
	}
}
