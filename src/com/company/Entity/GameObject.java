package com.company.Entity;

import com.company.Constants;
import com.company.GamePanel;
import com.company.GameState.GameStateManager;
import com.company.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject
{
    private int WIDTH = Constants.WIDTH;
    private int HEIGHT = Constants.HEIGHT;
    private int TILE_SIZE = Constants.TILE_SIZE;

    // Tile setup
    protected TileMap tileMap;
    protected double xMap;
    protected double yMap;

    // Game Panel
    protected GamePanel gamePanel;

    // Position and Velocity
    protected double x, y, dx, dy;

    // Dimensions
    protected int width, height;

    // Collision box
    protected int cwidth, cheight;

    // Collision
    protected int currentRow, currentCol;
    protected double xNext, yNext;
    protected double xTemp, yTemp;

    // Four corner collision detection
    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    // Animation
    protected Animation animation;
    protected int currentAction;
    protected boolean facingRight;

    private ArrayList<BufferedImage[]> sprites;

    // Movement
    protected boolean left, right, up, down, jumping, jumpHeld, falling;

    // Movement attributes
    protected double moveSpeed, maxSpeed, stopSpeed, groundStopSpeed;
    protected double fallSpeed, jumpFallSpeed, maxFallSpeed, wallFallSpeed, maxWallFallSpeed;
    protected double jumpStart, jumpStop;

    public GameObject(TileMap tileMap, GamePanel gamePanel)
    {
        this.tileMap = tileMap;
        this.gamePanel = gamePanel;
    }

    public abstract void init();
    public abstract void update(double dt);
    public void update()
    {
        update(Constants.MINIMUM_DELAY);
    }
    public abstract void draw(Graphics2D g2);

    public boolean intersects(GameObject o)
    {
        Rectangle thisRect = getRectangle();
        Rectangle otherRect = o.getRectangle();
        return thisRect.intersects(otherRect);
    }

    protected void calculateCorners(double x, double y)
    {
        int leftTile = (int)(x - cwidth / 2) / TILE_SIZE;
        int rightTile = (int)(x + cwidth / 2 - 1) / TILE_SIZE;
        int topTile = (int)(y - cheight / 2) / TILE_SIZE;
        int bottomTile = (int)(y + cheight / 2 - 1) / TILE_SIZE;

        Constants.TileType tl = tileMap.getTileType(topTile, leftTile);
        Constants.TileType tr = tileMap.getTileType(topTile, rightTile);
        Constants.TileType bl = tileMap.getTileType(bottomTile, leftTile);
        Constants.TileType br = tileMap.getTileType(bottomTile, rightTile);

        topLeft = tl == Constants.TileType.SOLID;
        topRight = tr == Constants.TileType.SOLID;
        bottomLeft = bl == Constants.TileType.SOLID;
        bottomRight = br == Constants.TileType.SOLID;
    }

    public void checkTileMapCollision()
    {
        currentCol = (int) x / TILE_SIZE;
        currentRow = (int) y / TILE_SIZE;

        xNext = x + dx;
        yNext = y + dy;

        xTemp = x;
        yTemp = y;

        // Check up and down
        calculateCorners(x, yNext);

        // Moving up
        if (dy < 0)
        {
            // Top corners solid
            if (topLeft || topRight)
            {
                dy = 0;
                yTemp = currentRow * TILE_SIZE + cheight / 2.0;
            }
            else yTemp += dy;
        }
        // Moving down
        else if (dy > 0)
        {
            // Bottom corners solid
            if (bottomLeft || bottomRight)
            {
                dy = 0;
                falling = false;
                yTemp = (currentRow + 1) * TILE_SIZE - cheight / 2.0;
            }
            else yTemp += dy;
        }

        // Check left and right
        calculateCorners(xNext, y);

        // Moving left
        if (dx < 0)
        {
            if (topLeft || bottomLeft)
            {
                dx = 0;
                xTemp = currentCol * TILE_SIZE + cwidth / 2.0;
            }
            else xTemp += dx;
        }
        // Moving right
        else if (dx > 0)
        {
            if (topRight || bottomRight)
            {
                dx = 0;
                xTemp = (currentCol + 1) * TILE_SIZE - cwidth / 2.0;
            }
            else xTemp += dx;
        }

        if (!falling)
        {
            calculateCorners(x, yNext + 1);
            if (!bottomLeft && !bottomRight) falling = true;
        }

        if (right && !left) facingRight = true;
        else if (left && !right) facingRight = false;
    }

    public boolean isOnScreen()
    {
        double xFinal = x + xMap;
        double yFinal = y + yMap;

        return !(xFinal + width < 0 || xFinal - width > WIDTH || yFinal + height < 0 || yFinal - height > HEIGHT);
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getCWidth()
    {
        return cwidth;
    }

    public int getCHeight()
    {
        return cheight;
    }

    public boolean isMovingLeft()
    {
        return left;
    }

    public boolean isMovingRight()
    {
        return right;
    }

    public boolean isMovingUp()
    {
        return up;
    }

    public boolean isMovingDown()
    {
        return down;
    }

    public boolean isFalling()
    {
        return falling;
    }

    public boolean isJumping()
    {
        return jumping;
    }

    public boolean isJumpHeld()
    {
        return jumpHeld;
    }

    public Rectangle getRectangle()
    {
        return new Rectangle((int) x, (int) y, cwidth, cheight);
    }

    public TileMap getTileMap()
    {
        return tileMap;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }

    public void setVelocity(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public void setTileMap(TileMap tileMap)
    {
        this.tileMap = tileMap;
    }

    public void setMapPosition()
    {
        xMap = tileMap.getX();
        yMap = tileMap.getY();
    }

    public void setAnimation(Animation animation)
    {
        this.animation = animation;
    }

    public void setLeft(boolean left)
    {
        this.left = left;
    }

    public void setRight(boolean right)
    {
        this.right = right;
    }

    public void setUp(boolean up)
    {
        this.up = up;
    }

    public void setDown(boolean down)
    {
        this.down = down;
    }

    public void setJumping(boolean jumping)
    {
        this.jumping = jumping;
    }

    public void setJumpHeld(boolean jumpHeld)
    {
        this.jumpHeld = jumpHeld;
    }
}
