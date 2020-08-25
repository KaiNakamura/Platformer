package main.java.tilemap;

import java.util.ArrayList;

import main.java.Constants;
import main.java.Constants.Direction;
import main.java.entity.Door;
import main.java.entity.Enemy;
import main.java.entity.Entity;
import main.java.entity.Player;

public class Level extends Tilemap {
	private static final int ROOMS_ACROSS = 6;
	private static final int ROOMS_DOWN = 3;
	private static final int ROOM_WIDTH = 20;
	private static final int ROOM_HEIGHT = 15;
	private static final int TILES_ACROSS = ROOMS_ACROSS * ROOM_WIDTH;
	private static final int TILES_DOWN = ROOMS_DOWN * ROOM_HEIGHT;
	private static final double MIN_START_ENEMY_DISTANCE = 64;

	public Level(Tileset tileset, Background background) {
		super(Level.generateMap(), tileset, background);

		player = new Player(this);
		setRandomPosition(player, 0, ROOM_HEIGHT, 0, TILES_ACROSS);
		
		Door door = new Door(this);
		doors.add(door);
		setRandomPosition(
			door,
			TILES_DOWN - ROOM_HEIGHT,
			TILES_DOWN,
			0,
			TILES_ACROSS
		);
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			double dx = enemy.getX() - player.getX();
			double dy = enemy.getY() - player.getY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < MIN_START_ENEMY_DISTANCE) {
				enemies.remove(i);
			}
		}
	}		

	private static TileCode[][] generateMap() {
		TileCode[][] map = new TileCode[TILES_DOWN][TILES_ACROSS];
		for (int i = 0; i < TILES_DOWN; i++) {
			for (int j = 0; j < TILES_ACROSS; j++) {
				map[i][j] = TileCode.TERRAIN_1;
			}
		}

		Room[][] rooms = generateRooms();
		
		for (int i = 0; i < ROOMS_DOWN; i++) {
			for (int j = 0; j < ROOMS_ACROSS; j++) {
				map = addRoom(
					map,
					rooms[i][j].getMap(),
					i * ROOM_HEIGHT,
					j * ROOM_WIDTH
				);
			}
		}

		return map;
	}

	private static Room[][] generateRooms() {
		Room[][] rooms = new Room[ROOMS_DOWN][ROOMS_ACROSS];
		for (int i = 0; i < ROOMS_DOWN; i++) {
			for (int j = 0; j < ROOMS_ACROSS; j++) {
				rooms[i][j] = new Room(i, j);
			}
		}

		int randomRow = (int) (Math.random() * ROOMS_DOWN);
		int randomCol = (int) (Math.random() * ROOMS_ACROSS);
		Room room = rooms[randomRow][randomCol];

		ArrayList<Room> generationPath = new ArrayList<>();

		while (true) {
			// Get available directions
			ArrayList<Direction> availableDirections = new ArrayList<>();

			int i = 0;
			while (true) {
				// If room up exists and has all walls, add up
				if (
					room.getRow() > 0 &&
					rooms[room.getRow() - 1][room.getCol()].hasAllWalls()
				) {
					availableDirections.add(Direction.UP);
				}
				// If room down exists and has all walls, add down
				if (
					room.getRow() < ROOMS_DOWN - 1 &&
					rooms[room.getRow() + 1][room.getCol()].hasAllWalls()
				) {
					availableDirections.add(Direction.DOWN);
				}
				// If room left exists and has all walls, add left
				if (
					room.getCol() > 0 &&
					rooms[room.getRow()][room.getCol() - 1].hasAllWalls()
				) {
					availableDirections.add(Direction.LEFT);
				}
				// If room right exists and has all walls, add right
				if (
					room.getCol() < ROOMS_ACROSS - 1 &&
					rooms[room.getRow()][room.getCol() + 1].hasAllWalls()
				) {
					availableDirections.add(Direction.RIGHT);
				}

				// If no available directions, check previous rooms
				if (availableDirections.isEmpty()) {
					// If no previous room, generation complete
					if (i >= generationPath.size()) {
						return rooms;
					}
					room = generationPath.get(i);
					i++;
				} else {
					break;
				}
			}

			// Pick new room in random direction
			Direction randomDirection = availableDirections.get(
				(int) (Math.random() * availableDirections.size())
			);

			Room randomRoom;
			switch (randomDirection) {
				case UP:
					randomRoom = rooms[room.getRow() - 1][room.getCol()];
					break;
				case DOWN:
					randomRoom = rooms[room.getRow() + 1][room.getCol()];
					break;
				case LEFT:
					randomRoom = rooms[room.getRow()][room.getCol() - 1];
					break;
				default:
					randomRoom = rooms[room.getRow()][room.getCol() + 1];
					break;
			}

			// Remove walls between current room and new room
			room.setWall(randomDirection, false);
			randomRoom.setWall(
				Direction.getOppositeDirection(randomDirection), false
			);

			room = randomRoom;
			generationPath.add(0, room);
		}
	}

	private static TileCode[][] addRoom(
		TileCode[][] map,
		TileCode[][] room,
		int row,
		int col
	) {
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				map[i + row][j + col] = room[i][j];
			}
		}

		return map;
	}

	private void setRandomPosition(
		Entity entity,
		int rowMin,
		int rowMax,
		int colMin,
		int colMax
	) {
		ArrayList<double[]> positions = new ArrayList<>();

		for (int row = rowMin; row < rowMax; row++) {
			for (int col = colMin; col < colMax; col++) {
				// If tile is air and tile below is terrain, add position
				if (
					map[row][col].getTileType() == TileType.AIR &&
					row < TILES_DOWN - 1 &&
					map[row + 1][col].getTileType() == TileType.TERRAIN
				) {
					double x = 	(col * Constants.TILE_SIZE) +
								(Constants.TILE_SIZE / 2.0);
					double y = 	(row * Constants.TILE_SIZE) +
								(Constants.TILE_SIZE / 2.0);
					positions.add(new double[]{x, y});
				}
			}
		}
		
		if(!positions.isEmpty()) {
			double[] randomPosition = positions.get(
				(int) (Math.random() * positions.size())
			);
			entity.setPosition(randomPosition[0], randomPosition[1]);
		}
	}
}
