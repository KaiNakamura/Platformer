package main.java.tilemap;

import main.java.Constants.File;

import java.util.ArrayList;

public enum Room {
	CORRIDOR_1(File.CORRIDOR_1, RoomType.CORRIDOR),
	DROP_1(File.DROP_1, RoomType.DROP),
	LANDING_1(File.LANDING_1, RoomType.LANDING),
	ENTRANCE_1(File.ENTRANCE_1, RoomType.ENTRANCE),
	EXIT_1(File.EXIT_1, RoomType.EXIT),
	OTHER_1(File.OTHER_1, RoomType.OTHER);
	
	private TileCode[][] map;
	private RoomType roomType;

	Room(TileCode[][] map, RoomType roomType) {
		this.map = map;
		this.roomType = roomType;
	}

	Room(File mapFile, RoomType roomType) {
		this(Tilemap.loadMap(mapFile), roomType);
	}

	public TileCode[][] getMap() {
		return map;
	}
	
	public RoomType getRoomType() {
		return roomType;
	}

	public static Room getRoom(RoomType roomType) {
		ArrayList<Room> rooms = new ArrayList<>();

		for (Room room : values()) {
			if (room.getRoomType() == roomType) {
				rooms.add(room);
			}
		}

		return rooms.get((int) (Math.random() * rooms.size()));
	}
}
