package com.company.GameState;

import com.company.Constants;
import com.company.TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState
{
    private final int WIDTH = Constants.WIDTH;
    private final int HEIGHT = Constants.HEIGHT;
    private Background bg;
    private Constants.MenuChoice currentMenuChoice;

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm)
    {
        super(gsm, Constants.GameStateType.MENU);

        try
        {
            bg = new Background("/Backgrounds/bg.png", 1);
            bg.setVelocity(-0.1, 0);

            titleColor = new Color(128,0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);

            currentMenuChoice = Constants.MenuChoice.NEW;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update(double dt)
    {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g2)
    {
        // Draw background
        bg.draw(g2);

        // Draw title
        g2.setColor(titleColor);
        g2.setFont(titleFont);
        drawCenteredString(g2, "Game", new Rectangle(0, 0, WIDTH, HEIGHT / 2), titleFont);

        // Draw menu choices
        g2.setFont(font);
        for (Constants.MenuChoice menuChoice : Constants.MenuChoice.values())
        {
            if (currentMenuChoice == menuChoice) g2.setColor(Color.BLACK);
            else g2.setColor(Color.RED);
            drawCenteredString(g2, menuChoice.toString(), new Rectangle(0,menuChoice.ordinal() * 15, WIDTH, HEIGHT), font);
        }
    }

    public void drawCenteredString(Graphics2D g2, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g2.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g2.setFont(font);
        // Draw the String
        g2.drawString(text, x, y);
    }

    public void select()
    {
        switch (currentMenuChoice)
        {
            case NEW:
                System.out.println("NEW");
                gsm.setState(Constants.GameStateType.LEVEL);
                break;
            case LOAD:
                System.out.println("LOAD");
                break;
            case QUIT:
                System.out.println("QUIT");
                System.exit(0);
        }
    }

    public void moveChoice(int dir)
    {
        int newOrdinal = (currentMenuChoice.ordinal() - dir) % Constants.MenuChoice.values().length;

        if (newOrdinal == -1) newOrdinal = Constants.MenuChoice.values().length - 1;

        for (Constants.MenuChoice choice : Constants.MenuChoice.values())
        {
            if (choice.ordinal() == newOrdinal) currentMenuChoice = choice;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        if (Constants.Key.JUMP.equals(code)) select();
        if (Constants.Key.UP.equals(code)) moveChoice(1);
        if (Constants.Key.DOWN.equals(code)) moveChoice(-1);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
