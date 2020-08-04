package main.java.tilemap;

public enum TerrainType {
	NO_EDGES(0),
	ALL_EDGES(1),
	TOP(2),
	RIGHT(3),
	BOTTOM(4),
	LEFT(5),
	TOP_LEFT(6),
	TOP_RIGHT(7),
	BOTTOM_RIGHT(8),
	BOTTOM_LEFT(9),
	NO_BOTTOM(10),
	NO_LEFT(11),
	NO_TOP(12),
	NO_RIGHT(13),
	TOP_BOTTOM(14),
	LEFT_RIGHT(15);

	private int col;

	TerrainType(int col) {
		this.col = col;
	}

	public int getCol() {
		return col;
	}

	public static TerrainType getTerrainType(
		boolean topEdge,
		boolean bottomEdge,
		boolean leftEdge,
		boolean rightEdge
	) {
		if (topEdge && bottomEdge && leftEdge && rightEdge) {
			return ALL_EDGES;
		} else if (topEdge && !bottomEdge && !leftEdge && !rightEdge) {
			return TOP;
		} else if (!topEdge && !bottomEdge && !leftEdge && rightEdge) {
			return RIGHT;
		} else if (!topEdge && bottomEdge && !leftEdge && !rightEdge) {
			return BOTTOM;
		} else if (!topEdge && !bottomEdge && leftEdge && !rightEdge) {
			return LEFT;
		} else if (topEdge && !bottomEdge && leftEdge && !rightEdge) {
			return TOP_LEFT;
		} else if (topEdge && !bottomEdge && !leftEdge && rightEdge) {
			return TOP_RIGHT;
		} else if (!topEdge && bottomEdge && !leftEdge && rightEdge) {
			return BOTTOM_RIGHT;
		} else if (!topEdge && bottomEdge && leftEdge && !rightEdge) {
			return BOTTOM_LEFT;
		} else if (topEdge && !bottomEdge && leftEdge && rightEdge) {
			return NO_BOTTOM;
		} else if (topEdge && bottomEdge && !leftEdge && rightEdge) {
			return NO_LEFT;
		} else if (!topEdge && bottomEdge && leftEdge && rightEdge) {
			return NO_TOP;
		} else if (topEdge && bottomEdge && leftEdge && !rightEdge) {
			return NO_RIGHT;
		} else if (topEdge && bottomEdge && !leftEdge && !rightEdge) {
			return TOP_BOTTOM;
		} else if (!topEdge && !bottomEdge && leftEdge && rightEdge) {
			return LEFT_RIGHT;
		} else {
			return NO_EDGES;
		}
	}
}
