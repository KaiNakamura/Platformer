package main.java.entity;

import main.java.Constants;
import main.java.tilemap.TileType;
import main.java.util.Rectangle;

@SuppressWarnings("serial")
public class Hitbox extends Rectangle {
	private Entity entity;

	private boolean enabled;

	private boolean topLeftHit, topRightHit, bottomLeftHit, bottomRightHit;

	public Hitbox(
		Entity entity,
		double x,
		double y,
		double width,
		double height
	) {
		super(x, y, width, height);
		this.entity = entity;
		enabled = true;
	}

	public void calculateTilemapHits(double x, double y) {
		int left = (int) x / Constants.TILE_SIZE;
		int right = (int)(x + width - 1) / Constants.TILE_SIZE;
		int top = (int) y / Constants.TILE_SIZE;
		int bottom = (int)(y + height - 1) / Constants.TILE_SIZE;

		topLeftHit = entity.getTilemap().getTileType(top, left) == TileType.TERRAIN;
		topRightHit = entity.getTilemap().getTileType(top, right) == TileType.TERRAIN;
		bottomLeftHit = entity.getTilemap().getTileType(bottom, left) == TileType.TERRAIN;
		bottomRightHit = entity.getTilemap().getTileType(bottom, right) == TileType.TERRAIN;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean topLeftHit() {
		return topLeftHit;
	}

	public boolean topRightHit() {
		return topRightHit;
	}

	public boolean bottomLeftHit() {
		return bottomLeftHit;
	}

	public boolean bottomRightHit() {
		return bottomRightHit;
	}

	public boolean topHit() {
		return topLeftHit || topRightHit;
	}

	public boolean bottomHit() {
		return bottomLeftHit || bottomRightHit;
	}

	public boolean leftHit() {
		return topLeftHit || bottomLeftHit;
	}

	public boolean rightHit() {
		return topRightHit || bottomRightHit;
	}

	public boolean hit() {
		return topLeftHit || topRightHit || bottomLeftHit || bottomRightHit;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
