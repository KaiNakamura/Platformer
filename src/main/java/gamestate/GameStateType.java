package main.java.gamestate;

import java.util.function.Supplier;

public enum GameStateType {
	MENU(MenuState::new),
	LEVEL(LevelState::new);

	private Supplier<GameState> gameState;

	GameStateType(Supplier<GameState> gameState) {
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState.get();	
	}
}
