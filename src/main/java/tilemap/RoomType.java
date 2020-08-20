package main.java.tilemap;

import main.java.Constants.File;

public enum RoomType {
	ALL_WALLS(
		true, true, true, true,
		File.ALL_WALLS_1
	),
	NO_WALLS(
		false, false, false, false,
		File.NO_WALLS_1
	),
	TOP(
		true, false, false, false,
		File.TOP_1
	),
	RIGHT(
		false, false, false, true,
		File.RIGHT_1
	),
	BOTTOM(
		false, true, false, false,
		File.BOTTOM_1
	),
	LEFT(
		false, false, true, false,
		File.LEFT_1
	),
	TOP_LEFT(
		true, false, true, false,
		File.TOP_LEFT_1
	),
	TOP_RIGHT(
		true, false, false, true,
		File.TOP_RIGHT_1
	),
	BOTTOM_RIGHT(
		false, true, false, true,
		File.BOTTOM_RIGHT_1
	),
	BOTTOM_LEFT(
		false, true, true, false,
		File.BOTTOM_LEFT_1
	),
	NO_BOTTOM(
		true, false, true, true,
		File.NO_BOTTOM_1
	),
	NO_LEFT(
		true, true, false, true,
		File.NO_LEFT_1
	),
	NO_TOP(
		false, true, true, true,
		File.NO_TOP_1
	),
	NO_RIGHT(
		true, true, true, false,
		File.NO_RIGHT_1
	),
	TOP_BOTTOM(
		true, true, false, false,
		File.TOP_BOTTOM_1
	),
	LEFT_RIGHT(
		false, false, true, true,
		File.LEFT_RIGHT_1
	);

	private boolean topWall, bottomWall, leftWall, rightWall;
	private File[] files;

	RoomType(
		boolean topWall,
		boolean bottomWall,
		boolean leftWall,
		boolean rightWall,
		File... files
	) {
		this.topWall = topWall;
		this.bottomWall = bottomWall;
		this.leftWall = leftWall;
		this.rightWall = rightWall;
		this.files = files;
	}

	public boolean hasTopWall() {
		return topWall;
	}

	public boolean hasBottomWall() {
		return bottomWall;
	}

	public boolean hasLeftWall() {
		return leftWall;
	}

	public boolean hasRightWall() {
		return rightWall;
	}

	public File[] getFiles() {
		return files;
	}

	public File getRandomFile() {
		return files[(int) (Math.random() * files.length)];
	}

	public static RoomType getRoomType(
		boolean topWall,
		boolean bottomWall,
		boolean leftWall,
		boolean rightWall
	) {
		for (RoomType roomType : values()) {
			if (
				topWall == roomType.hasTopWall() &&
				bottomWall == roomType.hasBottomWall() &&
				leftWall == roomType.hasLeftWall() &&
				rightWall == roomType.hasRightWall()
			) {
				return roomType;
			}
		}

		throw new IllegalArgumentException(
			"RoomType not found with topWall: " + topWall +
			", bottomWall: " + bottomWall +
			", leftWall: " + leftWall +
			", rightWall: " + rightWall
		);
	}
}
