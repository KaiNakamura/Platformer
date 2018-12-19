package com.company.GameState;

import com.company.Constants;
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
    private Background bg;
    private int currentMap;
    private Player player;

    public LevelState(GameStateManager gsm)
    {
        super(gsm, Constants.GameStateType.LEVEL);
        init();
    }

    @Override
    public void init()
    {
        currentMap = 0;

        tileMaps = new TileMap[]{
                new TileMap(Constants.File.TILESET, "/Maps/start.map", "/Backgrounds/bg.png"),
                new TileMap(Constants.File.TILESET, "/Maps/level1.map", "/Backgrounds/bg.png"),
                new TileMap(Constants.File.TILESET, "/Maps/level2.map", "/Backgrounds/bg.png"),

        };

        player = new Player(tileMaps[currentMap]);
        player.init();
    }

    @Override
    public void update(double dt)
    {
        // Set player movement
        player.setLeft(Constants.Key.LEFT.isDown());
        player.setRight(Constants.Key.RIGHT.isDown());
        player.setUp(Constants.Key.UP.isDown());
        player.setDown(Constants.Key.DOWN.isDown());
        player.setJumping(Constants.Key.JUMP.isPressed());
        player.setJumpHeld(Constants.Key.JUMP.isDown());

        // Update player
        if (!player.getTileMap().equals(tileMaps[currentMap]))
        {
            player.setTileMap(tileMaps[currentMap]);
            player.setPosition(tileMaps[currentMap].getPlayerX(), tileMaps[currentMap].getPlayerY());
        }
        player.update();

        // Update tile map position
        double x = WIDTH / 2.0 - player.getX();
        double y = HEIGHT / 2.0 - player.getY();

        double dx = player.getLookX(), dy = player.getLookY();

        tileMaps[currentMap].setPosition(x - dx, y - dy);

        // Reset keyPressed and keyReleased
        updateKeys();
    }

    @Override
    public void draw(Graphics2D g2)
    {
        // Draw Background
        tileMaps[currentMap].drawBackground(g2);

        // Draw TileMap
        tileMaps[currentMap].drawTiles(g2);

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

        if (code == KeyEvent.VK_P)
        {
            if (currentMap == tileMaps.length-1) setCurrentMap(0);
            else setCurrentMap(currentMap + 1);
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

    public void setCurrentMap(int currentMap)
    {
        this.currentMap = currentMap;
    }
}
