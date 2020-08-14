package main.java.tilemap;

import main.java.Constants;
import main.java.Constants.File;
import main.java.entity.Door;
import main.java.entity.Enemy;
import main.java.entity.Player;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tilemap {
	private static final int NUM_ROWS_TO_DRAW =	Constants.HEIGHT /
												Constants.TILE_SIZE + 2;
	private static final int NUM_COLS_TO_DRAW =	Constants.WIDTH /
												Constants.TILE_SIZE + 2;

	private TileCode[][] map;
	private Tileset tileset;
	private Background background;
	private Tile[][] tiles;
	
	private double x, y;
	private int width, height;
	private int xMin, yMin, xMax, yMax;

	private int numRows, numCols;
	private int rowOffset, colOffset;

	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Door> doors;

	protected Tilemap(
		TileCode[][] map,
		Tileset tileset,
		Background background
	) {
		this.map = map;
		this.tileset = tileset;
		this.background = background;
		init();
	}

	public Tilemap(
		File mapFile,
		Tileset tileset,
		Background background
	) {
		this(Tilemap.loadMap(mapFile), tileset, background);
	}

	private void init() {
		numRows = map.length;
		numCols = map[0].length;

		width = numCols * Constants.TILE_SIZE;
		height = numRows * Constants.TILE_SIZE;

		xMin = Constants.WIDTH - width;
		xMax = 0;
		yMin = Constants.HEIGHT - height;
		yMax = 0;

		enemies = new ArrayList<>();
		doors = new ArrayList<>();

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				double x = 	(col * Constants.TILE_SIZE) +
							(Constants.TILE_SIZE / 2.0);
				double y = 	(row * Constants.TILE_SIZE) +
							(Constants.TILE_SIZE / 2.0);

				switch (map[row][col]) {
					case PLAYER:
						setPosition(
							Constants.WIDTH / 2.0 - x,
							Constants.HEIGHT / 2.0 - y,
							false
						);

						player = new Player(this);
						player.setPosition(x, y);
						break;
					case ENEMY:
						Enemy enemy = new Enemy(this);
						enemy.setPosition(x, y);
						enemies.add(enemy);
						break;
					case DOOR:
						Door door = new Door(this);
						door.setPosition(x, y);
						doors.add(door);
						break;
					default:
						break;
				}
			}
		}


		loadTiles();
	}

	public static TileCode[][] loadMap(File file) {
		TileCode[][] map = null;
		BufferedReader bufferedReader = null;
		BufferedReader lineCounter = null;
		try {
			bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.getFile()))
			);

			lineCounter = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.getFile()))
			);

			String line = lineCounter.readLine();
			int numCols = (int) line.chars().filter(c -> c == ',').count() + 1;
			int numRows = 0;
			while (line != null) {
				numRows++;
				line = lineCounter.readLine();
			}

			map = new TileCode[numRows][numCols];

			String delims = ",";
			for (int row = 0; row < numRows; row++) {
				line = bufferedReader.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = TileCode.getTileCode(tokens[col].charAt(0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return map;
	}

	private void loadTiles() {
		tiles = new Tile[numRows][numCols];

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				TileCode tileCode = map[row][col];
				switch (tileCode.getTileType()) {
					case TERRAIN:	
						TileCode top = tileCode;
						TileCode bottom = tileCode;
						TileCode left = tileCode;
						TileCode right = tileCode;

						if (row > 0) {
							top = map[row - 1][col];
						}
						if (row < numRows - 1) {
							bottom = map[row + 1][col];
						}
						if (col > 0) {
							left = map[row][col - 1];
						}
						if (col < numCols - 1) {
							right = map[row][col + 1];
						}

						tiles[row][col] = tileset.getTile(
							Character.getNumericValue(tileCode.getCode()),
							TerrainType.getTerrainType(
								top != tileCode,
								bottom != tileCode,
								left != tileCode,
								right != tileCode
							).getCol()
						);
						break;
					default:
						tiles[row][col] = tileset.getTile(0, 0);
						break;
				}
			}
		}
	}

	public TileType getTileType(int row, int col) {
		if (
			row < 0 ||
			row > map.length - 1 ||
			col < 0 ||
			col > map[0].length - 1
		) {
			return TileType.AIR;
		}
		return map[row][col].getTileType();
	}

	public void setPosition(double x, double y, boolean smooth) {
		if (smooth) {
			// Set position to x and y smoothly
			// keep decimal end to prevent jittery player motion
			double xDecimal = x - (int) x;
			double yDecimal = y - (int) y;

			this.x += (x - this.x) * Constants.Camera.FOLLOW;
			this.x = (int) this.x + xDecimal;
			this.y += (y - this.y) * Constants.Camera.FOLLOW;
			this.y = (int) this.y + yDecimal;
		}
		else {
			this.x = x;
			this.y = y;
		}

		fixBounds();

		rowOffset = (int) -this.y / Constants.TILE_SIZE;
		colOffset = (int) -this.x / Constants.TILE_SIZE;
	}

	public void setPosition(double x, double y) {
		setPosition(x, y, false);
	}

	private void fixBounds() {
		if (x < xMin) {
			x = xMin;
		}
		if (x > xMax) {
			x = xMax;
		}
		if (y < yMin) {
			y = yMin;
		}
		if (y > yMax) {
			y = yMax;
		}
	}
	
	public void drawTiles(Graphics2D graphics) {
		for (int row = rowOffset; row < rowOffset + NUM_ROWS_TO_DRAW; row++) {
			if (row >= numRows) {
				break;
			}
			for (
				int col = colOffset; col < colOffset + NUM_COLS_TO_DRAW; col++
			) {
				if (col >= numCols) {
					break;
				}

				if (map[row][col].getTileType() != TileType.TERRAIN) {
					continue;
				}

				graphics.drawImage(
					tiles[row][col].getImage(),
					(int) x + col * Constants.TILE_SIZE,
					(int) y + row * Constants.TILE_SIZE,
					null
				);
			}
		}
	}

	public void drawBackground(Graphics2D graphics) {
		background.draw(graphics);
	}

	public TileCode[][] getMap() {
		return map;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}
}
