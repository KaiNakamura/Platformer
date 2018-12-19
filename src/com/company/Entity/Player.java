package com.company.Entity;

import com.company.Constants;
import com.company.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends GameObject
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int TILE_SIZE = Constants.TILE_SIZE;

    // Player variables
    private int health;
    private int maxHealth;

    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // Shooting
    private Constants.Direction direction;

    // Looking
    private double lookX, lookY;
    private double LOOK_SPEED = Constants.Camera.LOOK_SPEED;
    private double MAX_LOOK = Constants.Camera.MAX_LOOK;

    // Animation
    private Animation IDLE;
    private Animation RUNNING;
    private Animation JUMPING;
    private Animation LOOK_UP;
    private Animation LOOK_UP_RUNNING;
    private Animation LOOK_UP_JUMPING;
    private Animation LOOK_DOWN;
    private Animation LOOK_DOWN_JUMPING;

    public Player(TileMap tileMap)
    {
        super(tileMap);
        setPosition(tileMap.getPlayerX(), tileMap.getPlayerY());
    }

    @Override
    public void init()
    {
        width = height = TILE_SIZE;
        cwidth = Constants.Player.COL_WIDTH;
        cheight = Constants.Player.COL_HEIGHT;

        moveSpeed = Constants.Player.MOVE_SPEED;
        maxSpeed = Constants.Player.MAX_SPEED;
        stopSpeed = Constants.Player.STOP_SPEED;
        fallSpeed = Constants.Player.FALL_SPEED;
        jumpFallSpeed = Constants.Player.JUMP_FALL_SPEED;
        maxFallSpeed = Constants.Player.MAX_FALL_SPEED;
        jumpStart = Constants.Player.JUMP_START;
        jumpStop = Constants.Player.JUMP_STOP;

        facingRight = true;

        health = maxHealth = 3;

        IDLE = new Animation(Constants.AnimationAction.PLAYER_IDLE);
        RUNNING = new Animation(Constants.AnimationAction.PLAYER_RUNNING);
        JUMPING = new Animation(Constants.AnimationAction.PLAYER_JUMPING);
        LOOK_UP = new Animation(Constants.AnimationAction.PLAYER_LOOK_UP);
        LOOK_UP_RUNNING = new Animation(Constants.AnimationAction.PLAYER_LOOK_UP_RUNNING);
        LOOK_UP_JUMPING = new Animation(Constants.AnimationAction.PLAYER_LOOK_UP_JUMPING);
        LOOK_DOWN = new Animation(Constants.AnimationAction.PLAYER_LOOK_DOWN);
        LOOK_DOWN_JUMPING = new Animation(Constants.AnimationAction.PLAYER_LOOK_DOWN_JUMPING);

        setAnimation(IDLE);
    }

    @Override
    public void update(double dt)
    {
        // Update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // Update direction and looking
        if (facingRight)
        {
            direction = Constants.Direction.RIGHT;
            lookX += LOOK_SPEED;
        }
        else
        {
            direction = Constants.Direction.LEFT;
            lookX -= LOOK_SPEED;
        }

        if (up)
        {
            direction = Constants.Direction.UP;
            lookY -= LOOK_SPEED;
        }
        else if (down)
        {
            direction = Constants.Direction.DOWN;
            lookY += LOOK_SPEED;
        }
        else
        {
            if (lookY > 0) lookY -= LOOK_SPEED;
            else if (lookY < 0) lookY += LOOK_SPEED;
        }

        if (lookX > MAX_LOOK) lookX = MAX_LOOK;
        else if (lookX < -MAX_LOOK) lookX = -MAX_LOOK;
        if (lookY > MAX_LOOK) lookY = MAX_LOOK;
        else if (lookY < -MAX_LOOK) lookY = -MAX_LOOK;


        // Set animation
        if (falling)
        {
            if (up) setAnimation(LOOK_UP_JUMPING);
            else if (down) setAnimation(LOOK_DOWN_JUMPING);
            else setAnimation(JUMPING);
        }
        else if (left || right)
        {
            if (up) setAnimation(LOOK_UP_RUNNING);
            else setAnimation(RUNNING);
        }
        else if (up) setAnimation(LOOK_UP);
        else if (down) setAnimation(LOOK_DOWN);
        else setAnimation(IDLE);

        animation.update();
    }

    public void draw(Graphics2D g2)
    {
        setMapPosition();

        // Draw Player
        if (flinching)
        {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) return;
        }

        if (facingRight) g2.drawImage(animation.getImage(), (int)(x + xMap - width / 2), (int)(y + yMap - height / 2), null);
        else g2.drawImage(animation.getImage(), (int)(x + xMap - width / 2) + width, (int)(y + yMap - height / 2), -width, height, null);
    }

    private void getNextPosition()
    {
        // Movement
        if (left)
        {
            dx -= moveSpeed;
            if(dx < -maxSpeed) dx = -maxSpeed;
        }
        else if (right)
        {
            dx += moveSpeed;
            if(dx > maxSpeed) dx = maxSpeed;
        }
        else
        {
            if (dx > 0)
            {
                dx -= stopSpeed;
                if(dx < 0) dx = 0;
            }
            else if (dx < 0)
            {
                dx += stopSpeed;
                if(dx > 0) dx = 0;
            }
        }

        // Jumping
        if (jumping && !falling)
        {
            dy = -jumpStart;
            falling = true;
        }

        // Falling
        if (falling)
        {
            if (jumpHeld) dy += jumpFallSpeed;
            else dy += fallSpeed;

            if (dy > 0) jumpHeld = false;
            if (dy < 0 && !jumpHeld) dy += jumpStop;
            if (dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }

    public Constants.Direction getDirection()
    {
        return direction;
    }

    public double getLookX()
    {
        return lookX;
    }

    public double getLookY()
    {
        return lookY;
    }

    public void setLookX(double lookX)
    {
        this.lookX = lookX;
    }

    public void setLookY(double lookY)
    {
        this.lookY = lookY;
    }

    public void setLook(double x, double y)
    {
        setLookX(x);
        setLookY(y);
    }

    public int getHealth()
    {
        return health;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }
}
