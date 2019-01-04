package com.company.GameState;

import com.company.Constants;
import com.company.Entity.Door;
import com.company.Entity.Player;
import com.company.TileMap.Background;
import com.company.TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelState extends GameState
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    private TileMap[] tileMaps;

    // Doors
    private Door[] doors;
    private boolean doorEntered;
    private Constants.TileMap newMap;

    private Background bg;
    private Constants.TileMap currentMap;
    private Player player;

    public LevelState(GameStateManager gsm)
    {
        super(gsm, Constants.GameStateType.LEVEL);
        init();
    }

    @Override
    public void init()
    {
        currentMap = Constants.TileMap.START_POINT;

        tileMaps = new TileMap[]{
                new TileMap(gamePanel, Constants.TileMap.START_POINT),
                new TileMap(gamePanel, Constants.TileMap.LEVEL_1),
                new TileMap(gamePanel, Constants.TileMap.LEVEL_2),
        };

        // Set up doors
        tileMaps[0].getDoors()[0].setDestination(tileMaps[1]);
        tileMaps[1].getDoors()[0].setDestination(tileMaps[2]);

        player = new Player(getCurrentMap(), gsm.getGamePanel());
        player.init();
    }

    @Override
    public void update(double dt)
    {
        // Check if door entered
        if (doorEntered && gamePanel.getDelay() == 0)
        {
            currentMap = newMap;
            doorEntered = false;
            player.resetStance();
        }

        for (int i = 0; i < getCurrentMap().getDoors().length; i++)
        {
            if (getCurrentMap().getDoors()[i].entered(player))
            {
                doorEntered = true;
                gamePanel.setDelay(500);
                newMap = getCurrentMap().getDoors()[i].getDestination().getTileMap();
                System.out.println("CHANGING TO: " + newMap);
            }
        }

        // Set player movement
        player.setLeft(Constants.Key.LEFT.isDown());
        player.setRight(Constants.Key.RIGHT.isDown());
        player.setUp(Constants.Key.UP.isDown());
        player.setDown(Constants.Key.DOWN.isDown());
        if (Constants.Key.DOWN.isPressed()) player.setInspecting(true);
        player.setJumping(Constants.Key.JUMP.isPressed());
        player.setJumpHeld(Constants.Key.JUMP.isDown());
        player.setShooting(Constants.Key.SHOOT.isPressed());

        // Update player
        if (!player.getTileMap().equals(getCurrentMap()))
        {
            player.setTileMap(getCurrentMap());
            player.setPosition(getCurrentMap().getPlayerX(), getCurrentMap().getPlayerY());
        }
        player.update();

        // Update tile map position
        double x = WIDTH / 2.0 - player.getX();
        double y = HEIGHT / 2.0 - player.getY();

        double dx = player.getLookX(), dy = player.getLookY();

        getCurrentMap().setPosition(x - dx, y - dy);

        // Reset keyPressed and keyReleased
        updateKeys();
    }

    @Override
    public void draw(Graphics2D g2)
    {
        // Draw Background
        getCurrentMap().drawBackground(g2);

        // Draw TileMap
        getCurrentMap().drawTiles(g2);

        // Draw player
        player.draw(g2);
    }

    private void updateKeys()
    {
        for (Constants.Key key : Constants.Key.values())
        {
            key.setPressed(false);
            key.setReleased(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        for (Constants.Key key : Constants.Key.values())
        {
            if (key.equals(code))
            {
                if(!key.isDown()) key.setPressed(true);
                key.setDown(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        for (Constants.Key key : Constants.Key.values())
        {
            if (key.equals(code))
            {
                key.setReleased(true);
                key.setDown(false);
            }
        }
    }

    private TileMap getCurrentMap()
    {
        for (int i = 0; i < tileMaps.length; i++)
        {
            if (tileMaps[i].getTileMap() == currentMap) return tileMaps[i];
        }

        throw new IllegalArgumentException("TileMap not found from Constants.TileMap, you did something really wrong if your're seeing this.");
    }

    public void setCurrentMap(Constants.TileMap currentMap)
    {
        this.currentMap = currentMap;
    }
}
