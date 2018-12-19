package com.company.GameState;

import com.company.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStateManager
{
    private ArrayList<GameState> gameStates;
    private Constants.GameStateType currentStateType;

    public GameStateManager()
    {
        gameStates = new ArrayList<>();

        currentStateType = Constants.GameStateType.MENU;
        gameStates.add(new MenuState(this));
        gameStates.add(new LevelState(this));
    }

    public GameState getStateByType(Constants.GameStateType stateType)
    {
        for (GameState gameState : gameStates)
        {
            if (gameState.gameStateType == stateType)
            {
                return gameState;
            }
        }

        throw new IllegalArgumentException("GameState not found of GameStateType " + stateType);
    }

    public void setState(Constants.GameStateType stateType)
    {
        currentStateType = stateType;
    }

    public void update(double dt)
    {
        getStateByType(currentStateType).update(dt);
    }

    public void draw(Graphics2D g2)
    {
        getStateByType(currentStateType).draw(g2);
    }

    public void keyPressed(KeyEvent e)
    {
        getStateByType(currentStateType).keyPressed(e);
    }

    public void keyReleased(KeyEvent e)
    {
        getStateByType(currentStateType).keyReleased(e);
    }
}
