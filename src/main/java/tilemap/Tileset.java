package main.java.tilemap;

import main.java.Constants;
import main.java.Constants.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Tileset {
	private Tile[][] tiles;
	private BufferedImage image;
	private int numTilesAcross, numTilesDown;
	
	public Tileset(File file) {
		try {
			image = ImageIO.read(file.getFile());

			numTilesAcross = image.getWidth() / Constants.TILE_SIZE;
			numTilesDown = image.getHeight() / Constants.TILE_SIZE;
			tiles = new Tile[numTilesDown][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				for (int row = 0; row < numTilesDown; row++) {
					subimage = image.getSubimage(
						col * Constants.TILE_SIZE,
						row * Constants.TILE_SIZE,
						Constants.TILE_SIZE,
						Constants.TILE_SIZE
					);
					tiles[row][col] = new Tile(
						subimage,
						row == 0 ?
							TileType.AIR :
							TileType.TERRAIN	
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tile getTile(int row, int col) {
		return tiles[row][col];
	}
}
