package com.company.TileMap;

import com.company.Constants;
import com.company.Entity.Door;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileMap
{
    // Follow
    private double FOLLOW = Constants.Camera.FOLLOW;

    // Constant aliases
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    // Position
    private double x, y;

    // Player
    private double playerX, playerY;

    // Doors
    private ArrayList<Door> doors;

    // Bounds
    private int xMin, yMin, xMax, yMax;

    // Map
    private int[][] map;
    private int numRows, numCols;
    private int width, height;
    private String tsLocation;
    private String mapLocation;

    // Tileset
    private BufferedImage tileset;
    private int numTilesAcross, numTilesDown;
    private Tile[][] tiles;
    private Background bg;

    // drawing
    private int rowOffset, colOffset;
    private int numRowsToDraw, numColsToDraw;

    public TileMap(String tsLocation, String mapLocation, String bgLocation, double parralaxScale)
    {
        numRowsToDraw = HEIGHT / TILE_SIZE + 2;
        numColsToDraw = WIDTH / TILE_SIZE + 2;

        this.tsLocation = tsLocation;
        this.mapLocation = mapLocation;
        bg = new Background(bgLocation, parralaxScale);

        loadTiles(tsLocation);
        loadMap(mapLocation);
    }

    public TileMap(String tsLocation, String mapLocation, String bgLocation)
    {
        this(tsLocation, mapLocation, bgLocation, 0);
    }

    public void loadTiles(String s)
    {
        try
        {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));

            // One column and row are for labels
            numTilesAcross = tileset.getWidth() / TILE_SIZE - 1;
            numTilesDown = tileset.getHeight() / TILE_SIZE - 1;
            tiles = new Tile[numTilesDown][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++)
            {
                for (int row = 0; row < numTilesDown; row++)
                {
                    subimage = tileset.getSubimage((col + 1) * TILE_SIZE, (row + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (row == 0)
                    {
                        tiles[row][col] = new Tile(subimage, Constants.TileType.TRANSPARENT);
                    }
                    else
                    {
                        tiles[row][col] = new Tile(subimage, Constants.TileType.SOLID);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String s)
    {
        try
        {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * TILE_SIZE;
            height = numRows * TILE_SIZE;

            xMin = WIDTH - width;
            xMax = 0;
            yMin = HEIGHT - height;
            yMax = 0;

            String delims = "\\s+";
            for (int row = 0; row < numRows; row++)
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col], tiles.length);

                    if (map[row][col] == 1)
                    {
                        playerX = (col * TILE_SIZE) + TILE_SIZE/2.0;
                        playerY = (row * TILE_SIZE) + TILE_SIZE/2.0;

                        double x = WIDTH / 2.0 - playerX;
                        double y = HEIGHT / 2.0 - playerY;

                        setPosition(x, y, false);
                    }
//
//                    if (map[row][col] == 2)
//                    {
//                        doors.add(new Door(this, tiles[0][2]));
//                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getX()
    {
        return (int) x;
    }

    public int getY()
    {
        return (int) y;
    }

    public Constants.TileType getTileType(int row, int col)
    {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getTileType();
    }

    public void setPosition(double x, double y, boolean smooth)
    {
        if (smooth)
        {
            // Set position to x and y smoothly, but keep decimal end to prevent jittery player motion
            double xDecimal = x - (int) x;
            double yDecimal = y - (int) y;

            this.x += (x - this.x) * FOLLOW;
            this.x = (int) this.x + xDecimal;
            this.y += (y - this.y) * FOLLOW;
            this.y = (int) this.y + yDecimal;
        }
        else
        {
            this.x = x;
            this.y = y;
        }

        fixBounds();

        colOffset = (int) -this.x / TILE_SIZE;
        rowOffset = (int) -this.y / TILE_SIZE;
    }

    public void setPosition(double x, double y)
    {
        setPosition(x, y, true);
    }

    private void fixBounds()
    {
        if (x < xMin) x = xMin;
        if (x > xMax) x = xMax;
        if (y < yMin) y = yMin;
        if (y > yMax) y = yMax;
    }

    public void drawTiles(Graphics2D g2)
    {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
        {
            if (row >= numRows) break;
            for (int col = colOffset; col < colOffset + numColsToDraw; col++)
            {
                if (col >= numCols) break;

                if (map[row][col] == 0 || map[row][col] == 1) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g2.drawImage(tiles[r][c].getImage(), (int) x + col * TILE_SIZE, (int) y + row * TILE_SIZE, null);
            }
        }
    }

    public void drawBackground(Graphics2D g2)
    {
        bg.draw(g2);
    }

    public double getPlayerX()
    {
        return playerX;
    }

    public double getPlayerY()
    {
        return playerY;
    }

    public String getMapLocation()
    {
        return mapLocation;
    }

    public boolean equals(TileMap other)
    {
        return other.getMapLocation().equals(mapLocation);
    }
}
