package main.java.entity;

import main.java.Constants.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Animation {
	private Sprites sprites;
	private int row;
	private long delay;
	private int width, height;
	private boolean playOnce;
	private File file;

	private BufferedImage[] frames;
	private int currentFrame;
	private long startTime = -1;
	private boolean playedOnce;

	public Animation(Sprites sprites) {
		this.sprites = sprites;
		this.row = sprites.getRow();
		this.delay = sprites.getDelay();
		this.width = sprites.getWidth();
		this.height = sprites.getHeight();
		this.playOnce = sprites.shouldPlayOnce();
		this.file = sprites.getFile();

		currentFrame = 0;
		playedOnce = false;

		try {
			BufferedImage spriteSheet = ImageIO.read(file.getFile());

			BufferedImage[] frames = new BufferedImage[sprites.getNumFrames()];

			for(int col = 0; col < sprites.getNumFrames(); col++) {
				frames[col] = spriteSheet.getSubimage(
					col * width,
					row * height,
					width,
					height
				);
			}

			this.frames = frames;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (delay <= 0) {
			return;
		}

		if (startTime < 0) {
			startTime = System.nanoTime();
		}

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}

		if (currentFrame == frames.length) {
			playedOnce = true;

			if (playOnce) {
				currentFrame--;
			} else {
				currentFrame = 0;
			}
		}
	}

	public BufferedImage[] getFrames() {
		return frames;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public BufferedImage getImage() {
		return frames[currentFrame];
	}

	public int getRow() {
		return row;
	}

	public long getDelay() {
		return delay;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public File getFile() {
		return file;
	}

	public Sprites getSprites() {
		return sprites;
	}

	public boolean isPlayedOnce() {
		return playedOnce;
	}
}
