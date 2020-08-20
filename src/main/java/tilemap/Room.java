package main.java.tilemap;

import main.java.Constants.Direction;

public class Room {
	private int row, col;
	private boolean topWall, bottomWall, leftWall, rightWall;

	public Room(
		int row,
		int col,
		boolean topWall,
		boolean bottomWall,
		boolean leftWall,
		boolean rightWall
	) {
		this.row = row;
		this.col = col;
		this.topWall = topWall;
		this.bottomWall = bottomWall;
		this.leftWall = leftWall;
		this.rightWall = rightWall;
	}

	public Room(int row, int col) {
		this(row, col, true, true, true, true);
	}

	public TileCode[][] getMap() {
		return Tilemap.loadMap(getRoomType().getRandomFile());
	}
	
	public RoomType getRoomType() {
		return RoomType.getRoomType(
			topWall,
			bottomWall,
			leftWall,
			rightWall
		);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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

	public void setTopWall(boolean topWall) {
		this.topWall = topWall;
	}

	public void setBottomWall(boolean bottomWall) {
		this.bottomWall = bottomWall;
	}

	public void setLeftWall(boolean leftWall) {
		this.leftWall = leftWall;
	}

	public void setRightWall(boolean rightWall) {
		this.rightWall = rightWall;
	}

	public boolean hasWall(Direction direction) {
		switch (direction) {
			case UP:
				return topWall;
			case DOWN:
				return bottomWall;
			case LEFT:
				return leftWall;
			default:
				return rightWall;
		}
	}

	public void setWall(Direction direction, boolean state) {
		switch (direction) {
			case UP:
				setTopWall(state);
				break;
			case DOWN:
				setBottomWall(state);
				break;
			case LEFT:
				setLeftWall(state);
				break;
			case RIGHT:
				setRightWall(state);
				break;
		}
	}

	public boolean hasAllWalls() {
		return topWall && bottomWall && leftWall && rightWall;
	}
}
