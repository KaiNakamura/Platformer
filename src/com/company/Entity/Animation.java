package com.company.Entity;

import com.company.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation
{
    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    private boolean playedOnce;

    public Animation(Constants.AnimationAction action)
    {
        this.frames = action.getFrames();
        this.delay = action.getDelay();
        playedOnce = false;
    }

    public void update()
    {
        if (delay == -1) return;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }

        if (currentFrame == frames.length)
        {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public int getFrame()
    {
        return currentFrame;
    }

    public BufferedImage getImage()
    {
        return frames[currentFrame];
    }

    public boolean isPlayedOnce()
    {
        return playedOnce;
    }

    public void setFrames(BufferedImage[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    public void setFrame(int i)
    {
        currentFrame = i;
    }
}
