package main.java.tilemap;

import main.java.Constants;
import main.java.Constants.File;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background
{
    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double parallaxScale;

    public Background(File file, double parallaxScale) {
        try {
			image = ImageIO.read(file.getFile());
            this.parallaxScale = parallaxScale;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Background(File file) {
		this(file, 0);
	}

    public void update(double dt) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(image, (int) x, (int) y, null);

        // Tile wrapping
        if (x < 0) {
            graphics.drawImage(image, (int) x + Constants.WIDTH, (int) y, null);
        }
        if (x > 0) {
            graphics.drawImage(image, (int) x - Constants.WIDTH, (int) y, null);
        }
        if (y < 0) {
            graphics.drawImage(image, (int) x, (int) y + Constants.HEIGHT, null);
        }
        if (y > 0) {
            graphics.drawImage(image, (int) x, (int) y - Constants.HEIGHT, null);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setX(double x) {
        this.x = (x * parallaxScale) % Constants.WIDTH;
    }

    public void setY(double y) {
        this.y = (y * parallaxScale) % Constants.HEIGHT;
    }

    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setVelocity(double dx, double dy) {
        setDx(dx);
        setDy(dy);
    }
}

