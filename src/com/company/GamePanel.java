package com.company;

import com.company.GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
    // Dimensions
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private final int SCALE = Constants.SCALE;

    // Game thread
    private Thread thread;
    private boolean running;
    private int FPS = Constants.FPS;
    private int MINIMUM_DELAY = Constants.MINIMUM_DELAY;
    private long targetTime = (long) 1000 / (long) FPS;
    private long delay = 0;

    // Image
    private BufferedImage image;
    private Graphics2D g2;

    // Game State Manager
    private GameStateManager gsm;

    public GamePanel()
    {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify()
    {
        super.addNotify();
        if (thread == null)
        {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void init()
    {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        running = true;

        gsm = new GameStateManager(this);
    }

    @Override
    public void run()
    {
        init();

        long start;
        long elapsed;
        long wait;

        // Game Loop
        while (running)
        {
            start = System.nanoTime();

            update(System.nanoTime() - start);
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000 + delay;

            if (wait < 0) wait = MINIMUM_DELAY;

            try
            {
                Thread.sleep(wait);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            delay = 0;
        }
    }

    private void update(double dt)
    {
        gsm.update(dt);
    }

    private void draw()
    {
        gsm.draw(g2);
    }

    private void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        gsm.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        gsm.keyReleased(e);
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    public void delay(long delay)
    {
        this.delay += delay;
    }
}
