package com.company.Entity;

import com.company.Constants;
import com.company.GamePanel;
import com.company.TileMap.Tile;
import com.company.TileMap.TileMap;

import java.awt.*;

public class Door extends GameObject
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    private TileMap destination;

    public Door(TileMap tileMap, GamePanel gamePanel, double x, double y)
    {
        super(tileMap, gamePanel);
        this.x = x;
        this.y = y;

        width = height = TILE_SIZE;
        cwidth = cheight = TILE_SIZE;
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update(double dt)
    {

    }

    @Override
    public void draw(Graphics2D g2)
    {

    }

    public boolean entered(Player player)
    {
        return player.getRectangle().intersects(getRectangle()) && player.isInspecting();
    }

    public TileMap getDestination()
    {
        return destination;
    }

    public void setDestination(TileMap destination)
    {
        this.destination = destination;
    }
}

