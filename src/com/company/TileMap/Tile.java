package com.company.TileMap;

import com.company.Constants;

import java.awt.image.BufferedImage;

public class Tile
{
    private BufferedImage image;
    private Constants.TileType tileType;

    public Tile(BufferedImage image, Constants.TileType tileType)
    {
        this.image = image;
        this.tileType = tileType;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public Constants.TileType getTileType()
    {
        return tileType;
    }
}
