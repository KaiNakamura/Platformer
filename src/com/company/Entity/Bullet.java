package com.company.Entity;

import com.company.Constants;
import com.company.GamePanel;
import com.company.TileMap.TileMap;

import java.awt.*;

public class Bullet extends GameObject
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    // Bullet variables
    private Constants.Direction direction;
    private boolean hit, remove;

    // Animation
    private Animation NULL;
    private Animation MOVING;
    private Animation FIRED;
    private Animation HIT;
    private Animation DAMAGE;
    private Animation EXPIRED;

    public Bullet(TileMap tileMap, GamePanel gamePanel, Constants.Direction direction, double dx, double dy)
    {
        super(tileMap, gamePanel);
        this.direction = direction;
        this.dx = dx;
        this.dy = dy;
    }

    public Bullet(TileMap tileMap, GamePanel gamePanel, Constants.Direction direction)
    {
        this(tileMap, gamePanel, direction, 0, 0);
    }

    @Override
    public void init()
    {
        width = height = TILE_SIZE;

        moveSpeed = Constants.Bullet.MOVE_SPEED;
        maxSpeed = moveSpeed;
        stopSpeed = moveSpeed;

        if (direction == Constants.Direction.RIGHT || direction == Constants.Direction.LEFT)
        {
            cwidth = Constants.Bullet.COL_WIDTH;
            cheight = Constants.Bullet.COL_HEIGHT;
        }
        else
        {
            cwidth = Constants.Bullet.COL_HEIGHT;
            cheight = Constants.Bullet.COL_WIDTH;
        }

        NULL = new Animation(Constants.AnimationAction.NULL);
        MOVING = new Animation(Constants.AnimationAction.BULLET_MOVING);
        FIRED = new Animation(Constants.AnimationAction.BULLET_FIRED);
        HIT = new Animation(Constants.AnimationAction.BULLET_HIT);
        DAMAGE = new Animation(Constants.AnimationAction.BULLET_DAMAGE);
        EXPIRED = new Animation(Constants.AnimationAction.BULLET_EXPIRED);

        setAnimation(NULL);
    }

    @Override
    public void update(double dt)
    {
        // Update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // Set hit
//        if (dx == 0 && dy == 0) hit = true;
//        System.out.println(topLeft + ", " + topRight + ", " + bottomLeft + ", " + bottomRight);

        // Set animation
        if (hit) setAnimation(HIT);
        else setAnimation(MOVING);
        animation.update();

        // Removing
        if (hit && animation.isPlayedOnce()) remove = true;
        if (remove) setAnimation(NULL);
    }

    @Override
    public void draw(Graphics2D g2)
    {
        setMapPosition();

        if (animation != NULL)
        {
            int drawX = (int) (x + xMap - width / 2);
            int drawY = (int) (y + yMap - height / 2);
            switch (direction)
            {
                case RIGHT:
                    g2.drawImage(animation.getImage(), drawX, drawY, null);
                    break;
                case LEFT:
                    g2.drawImage(animation.getImage(), drawX + width, drawY, -width, height, null);
                    break;
                case UP:
                    g2.translate(drawX, drawY);
                    g2.rotate(-Math.PI / 2);
                    g2.drawImage(animation.getImage(), -width, 0, null);
                    g2.rotate(Math.PI / 2);
                    g2.translate(-drawX, -drawY);
                    break;
                default:
                    g2.translate(drawX, drawY);
                    g2.rotate(Math.PI / 2);
                    g2.drawImage(animation.getImage(), 0, -height, null);
                    g2.rotate(-Math.PI / 2);
                    g2.translate(-drawX, -drawY);
                    break;
            }
            g2.setPaint(Color.RED);
            g2.drawRect(drawX + (width - cwidth) / 2, drawY + (height - cheight) / 2, cwidth - 1, cheight - 1);
        }
    }

    private void getNextPosition()
    {
        switch (direction)
        {
            case RIGHT:
                dx = moveSpeed;
                dy = 0;
                break;
            case LEFT:
                dx = -moveSpeed;
                dy = 0;
                break;
            case UP:
                dx = 0;
                dy = -moveSpeed;
                break;
            default:
                dx = 0;
                dy = moveSpeed;
                break;
        }

        if (topLeft || topRight || bottomLeft || bottomRight) hit = true;
    }

    public Constants.Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Constants.Direction direction)
    {
        this.direction = direction;
    }

    public boolean isHit()
    {
        return hit;
    }

    public boolean shouldRemove()
    {
        return remove;
    }
}
