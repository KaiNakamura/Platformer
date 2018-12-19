package com.company.Entity;

import com.company.Constants;
import com.company.TileMap.Tile;
import com.company.TileMap.TileMap;

import java.awt.*;

public class Door extends GameObject
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    private Tile tile;

    public Door(TileMap tileMap, Tile tile)
    {
        super(tileMap);
        this.tile = tile;
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

    public Tile getTile()
    {
        return tile;
    }
}

