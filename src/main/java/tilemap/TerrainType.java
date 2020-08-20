package main.java.tilemap;

public enum TerrainType {
	NO_EDGES(0, false, false, false, false),
	ALL_EDGES(1, true, true, true, true),
	TOP(2, true, false, false, false),
	RIGHT(3, false, false, false, true),
	BOTTOM(4, false, true, false, false),
	LEFT(5, false, false, true, false),
	TOP_LEFT(6, true, false, true, false),
	TOP_RIGHT(7, true, false, false, true),
	BOTTOM_RIGHT(8, false, true, false, true),
	BOTTOM_LEFT(9, false, true, true, false),
	NO_BOTTOM(10, true, false, true, true),
	NO_LEFT(11, true, true, false, true),
	NO_TOP(12, false, true, true, true),
	NO_RIGHT(13, true, true, true, false),
	TOP_BOTTOM(14, true, true, false, false),
	LEFT_RIGHT(15, false, false, true, true);

	private int col;
	private boolean topEdge, bottomEdge, leftEdge, rightEdge;

	TerrainType(
		int col,
		boolean topEdge,
		boolean bottomEdge,
		boolean leftEdge,
		boolean rightEdge
	) {
		this.col = col;
		this.topEdge = topEdge;
		this.bottomEdge = bottomEdge;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
	}

	public int getCol() {
		return col;
	}

	public boolean hasTopEdge() {
		return topEdge;
	}

	public boolean hasBottomEdge() {
		return bottomEdge;
	}

	public boolean hasLeftEdge() {
		return leftEdge;
	}

	public boolean hasRightEdge() {
		return rightEdge;
	}

	public static TerrainType getTerrainType(
		boolean topEdge,
		boolean bottomEdge,
		boolean leftEdge,
		boolean rightEdge
	) {
		for (TerrainType terrainType : values()) {
			if (
				topEdge == terrainType.hasTopEdge() &&
				bottomEdge == terrainType.hasBottomEdge() &&
				leftEdge == terrainType.hasLeftEdge() &&
				rightEdge == terrainType.hasRightEdge()
			) {
				return terrainType;
			}
		}

		throw new IllegalArgumentException(
			"TerrainType not found with topEdge: " + topEdge +
			", bottomEdge: " + bottomEdge +
			", leftEdge: " + leftEdge +
			", rightEdge: " + rightEdge
		);
	}
}
