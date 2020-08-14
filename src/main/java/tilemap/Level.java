package main.java.tilemap;

public class Level extends Tilemap {
	private static final int ROOMS_ACROSS = 4;
	private static final int ROOMS_DOWN = 4;
	private static final int ROOM_WIDTH = 20;
	private static final int ROOM_HEIGHT = 15;
	private static final int TILES_ACROSS = ROOMS_ACROSS * ROOM_WIDTH + 2;
	private static final int TILES_DOWN = ROOMS_DOWN * ROOM_HEIGHT + 2;

	private static final double DROP_CHANCE = 0.2;

	public Level(Tileset tileset, Background background) {
		super(Level.generateMap(), tileset, background);
	}		

	private static TileCode[][] generateMap() {
		TileCode[][] map = new TileCode[TILES_DOWN][TILES_ACROSS];
		for (int i = 0; i < TILES_DOWN; i++) {
			for (int j = 0; j < TILES_ACROSS; j++) {
				map[i][j] = TileCode.TERRAIN_1;
			}
		}

		RoomType[][] roomTypes = generateRoomTypes();
		
		for (int i = 0; i < ROOMS_DOWN; i++) {
			for (int j = 0; j < ROOMS_ACROSS; j++) {
				map = addRoom(
					map,
					Room.getRoom(roomTypes[i][j]).getMap(),
					i * ROOM_HEIGHT + 1,
					j * ROOM_WIDTH + 1
				);
			}
		}

		return map;
	}

	private static RoomType[][] generateRoomTypes() {
		RoomType[][] roomTypes = new RoomType[ROOMS_DOWN][ROOMS_ACROSS];
		for (int i = 0; i < ROOMS_DOWN; i++) {
			for (int j = 0; j < ROOMS_ACROSS; j++) {
				roomTypes[i][j] = RoomType.OTHER;
			}
		}

		int row = 0;
		int col = (int) (Math.random() * ROOMS_ACROSS);
		int direction = 0;
		roomTypes[row][col] = RoomType.ENTRANCE;

		while (row < ROOMS_DOWN) {
			if (direction == 0) {
				if (col == ROOMS_ACROSS - 1) {
					direction = -1;
				} else if (col == 0) {
					direction = 1;
				} else {
					direction = Math.random() > 0.5 ? 1 : -1;
				}
			}

			if (
				((Math.random() < DROP_CHANCE) ||
				(direction == 1 && col == ROOMS_ACROSS - 1) ||
				(direction == -1 && col == 0)) &&
				(roomTypes[row][col] != RoomType.ENTRANCE)
			) {
				roomTypes[row][col] = RoomType.DROP;
				direction = 0;
				if (row >= ROOMS_DOWN - 1) {
					roomTypes[row][col] = RoomType.EXIT;
					break;
				} else {
					row++;
					roomTypes[row][col] = RoomType.LANDING;
				}
			} else {
				col += direction;
				roomTypes[row][col] = RoomType.CORRIDOR;
			}
		}

		return roomTypes;
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
}
