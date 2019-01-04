package com.company.GameState;

import com.company.Constants;
import com.company.GamePanel;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentNavigableMap;

public abstract class GameState
{
    protected GamePanel gamePanel;
    protected GameStateManager gsm;
    protected Constants.GameStateType gameStateType;

    public GameState(GameStateManager gsm, Constants.GameStateType gameStateType)
    {
        this.gamePanel = gsm.getGamePanel();
        this.gsm = gsm;
        this.gameStateType = gameStateType;
    }

    public abstract void init();
    public abstract void update(double dt);
    public void update()
    {
        update(Constants.MINIMUM_DELAY);
    }
    public abstract void draw(Graphics2D g2);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    public Constants.GameStateType getGameStateType()
    {
        return gameStateType;
    }
}
