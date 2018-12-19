package com.company;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Constants
{
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;

    public static final int TILE_SIZE = 16;

    public static final int FPS = 60;
    public static final int MINIMUM_DELAY = 5;

    public enum Direction
    {
        RIGHT, LEFT, UP, DOWN
    }

    public enum GameStateType
    {
        MENU, LEVEL
    }

    public enum MenuChoice
    {
        NEW("New"), LOAD("Load"), QUIT("Quit");

        private String s;
        MenuChoice(String s)
        {
            this.s = s;
        }

        public String toString()
        {
            return s;
        }
    }

    public enum TileType
    {
        SOLID, TRANSPARENT
    }

    public enum Key
    {
        LEFT(KeyEvent.VK_LEFT, KeyEvent.VK_A),
        RIGHT(KeyEvent.VK_RIGHT, KeyEvent.VK_D),
        UP(KeyEvent.VK_UP, KeyEvent.VK_W),
        DOWN(KeyEvent.VK_DOWN, KeyEvent.VK_S),
        JUMP(KeyEvent.VK_Z, KeyEvent.VK_SPACE),
        SHOOT(KeyEvent.VK_X),
        PAUSE(KeyEvent.VK_ESCAPE);

        private int[] codes;
        private boolean pressed, lastPressed;
        private boolean released;
        private boolean down;

        Key(int... codes)
        {
            this.codes = codes;
        }

        public int[] getCodes()
        {
            return codes;
        }

        public boolean equals(int code)
        {
            // Quicker for events with just one key
            if (codes.length == 1) return code == codes[0];

            // Otherwise go through all codes
            for (int i = 0; i < codes.length; i++)
            {
                if (code == codes[i]) return true;
            }

            return false;
        }

        public boolean isPressed()
        {
            return pressed;
        }

        public boolean isReleased()
        {
            return released;
        }

        public boolean isDown()
        {
            return down;
        }

        public boolean getLastPressed()
        {
            return lastPressed;
        }

        public void setPressed(boolean pressed)
        {
            this.pressed = pressed;
        }

        public void setReleased(boolean released)
        {
            this.released = released;
        }

        public void setDown(boolean down)
        {
            this.down = down;
        }

        public void setLastPressed(boolean lastPressed)
        {
            this.lastPressed = lastPressed;
        }
    }

    public static class File
    {
        public static final String PLAYER_SPRITES = "/Sprites/Player/playersprites.png";
        public static final String TILESET = "/Tilesets/tileset.png";
    }

    public static class Camera
    {
        public static final double FOLLOW = 0.07;
        public static final double PARALLAX = 0.1;
        public static final double LOOK_SPEED = 0.8;
        public static final double MAX_LOOK = 32;
    }

    public enum AnimationAction
    {
        PLAYER_IDLE(1, 0, File.PLAYER_SPRITES),
        PLAYER_RUNNING(3, 100, 1, File.PLAYER_SPRITES),
        PLAYER_JUMPING(1, 2, File.PLAYER_SPRITES),
        PLAYER_LOOK_UP(1, 3, File.PLAYER_SPRITES),
        PLAYER_LOOK_UP_RUNNING(3, 100, 4, File.PLAYER_SPRITES),
        PLAYER_LOOK_UP_JUMPING(1, 5, File.PLAYER_SPRITES),
        PLAYER_LOOK_DOWN(1, 6, File.PLAYER_SPRITES),
        PLAYER_LOOK_DOWN_JUMPING(1, 7, File.PLAYER_SPRITES);

        private BufferedImage[] frames;
        private long delay;
        private int width, height;
        private int row;
        private String file;

        AnimationAction(int numFrames, long delay, int width, int height, int row, String file)
        {
            this.delay = delay;
            this.width = width;
            this.height = height;
            this.row = row;
            this.file = file;

            try
            {
                BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(this.file));

                BufferedImage[] bi = new BufferedImage[numFrames];

                for(int i = 0; i < numFrames; i++)
                {
                    bi[i] = spritesheet.getSubimage((i + 1) * this.width, (row + 1) * this.height, this.width, this.height);
                }

                this.frames = bi;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        AnimationAction(int numFrames, int width, int height, int row, String file)
        {
            this(numFrames, -1, width, height, row, file);
        }

        AnimationAction(int numFrames, int delay, int row, String file)
        {
            this(numFrames, delay, TILE_SIZE, TILE_SIZE, row, file);
        }

        AnimationAction(int numFrames, int row, String file)
        {
            this(numFrames, -1, TILE_SIZE, TILE_SIZE, row, file);
        }

        public BufferedImage[] getFrames()
        {
            return frames;
        }

        public long getDelay()
        {
            return delay;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public int getRow()
        {
            return row;
        }

        public String getFile()
        {
            return file;
        }
    }

    public static class Player
    {
        // Collision
        public static final int COL_WIDTH = 12;
        public static final int COL_HEIGHT = TILE_SIZE;

        // Movement
        public static final double MOVE_SPEED = 0.1;
        public static final double MAX_SPEED = 1.4;
        public static final double STOP_SPEED = 0.12;
        public static final double FALL_SPEED = 0.15;
        public static final double JUMP_FALL_SPEED = 0.08;
        public static final double MAX_FALL_SPEED = 4.0;
        public static final double JUMP_START = 2.9;
        public static final double JUMP_STOP = 0.1;
    }
}
