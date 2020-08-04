package main.java.tilemap;

public enum TileCode {
	AIR('0', TileType.AIR),
	TERRAIN_1('1', TileType.TERRAIN),
	TERRAIN_2('2', TileType.TERRAIN),
	TERRAIN_3('3', TileType.TERRAIN),
	TERRAIN_4('4', TileType.TERRAIN),
	TERRAIN_5('5', TileType.TERRAIN),
	TERRAIN_6('6', TileType.TERRAIN),
	TERRAIN_7('7', TileType.TERRAIN),
	TERRAIN_8('8', TileType.TERRAIN),
	TERRAIN_9('9', TileType.TERRAIN),
	PLAYER('p', TileType.ENTITY),
	ENEMY('e', TileType.ENTITY);

	private char code;
	private TileType tileType;

	TileCode(char code, TileType tileType) {
		this.code = code;
		this.tileType = tileType;
	}

	public char getCode() {
		return code;
	}

	public TileType getTileType() {
		return tileType;
	}

	public static TileCode getTileCode(char code) {
		for(TileCode tileCode : values()) {
			if(tileCode.getCode() == code) {
				return tileCode;
			}
		}

		throw new IllegalArgumentException(
			"TileCode not found with code '" + code	+ "'"
		);
	}
}
