class Bucket extends Tool
{
  constructor()
  {
    super();
  }

  click(tileMap, color)
  {
    if (!Tile.colorEquals(tileMap.getTile(mouseX, mouseY).color, color))
    {
      this.fillAdjacents(tileMap, mouseX, mouseY, color);
      tileMap.setTile(mouseX, mouseY, color);
    }
  }

  fillAdjacents(tileMap, v1, v2, v3)
  {
    var x, y, color;
    if (v1 instanceof Tile)
    {
      x = v1.origin.x + TILE_SIZE * SCALE / 2;
      y = v1.origin.y + TILE_SIZE * SCALE / 2;
      color = v2;
    }
    else
    {
      x = v1;
      y = v2;
      color = v3;
    }

    var tile  = tileMap.getTile(x, y);
    var left  = tileMap.getTile(x - TILE_SIZE * SCALE, y);
    var right = tileMap.getTile(x + TILE_SIZE * SCALE, y);
    var above = tileMap.getTile(x, y - TILE_SIZE * SCALE);
    var below = tileMap.getTile(x, y + TILE_SIZE * SCALE);

    // console.log("tile");
    // console.log(tile);
    // console.log("left");
    // console.log(left);
    // console.log("right");
    // console.log(right);
    // console.log("above");
    // console.log(above);
    // console.log("below");
    // console.log(below);

    if (left != null && !Tile.colorEquals(left.color, color))
    {
      left.color = color;
      this.fillAdjacents(tileMap, left, color);
    }

    if (right != null && !Tile.colorEquals(right.color, color))
    {
      right.color = color;
      this.fillAdjacents(tileMap, right, color);
    }

    if (above != null && !Tile.colorEquals(above.color, color))
    {
      above.color = color;
      this.fillAdjacents(tileMap, above, color);
    }

    if (below != null && !Tile.colorEquals(below.color, color))
    {
      below.color = color;
      this.fillAdjacents(tileMap, below, color);
    }
  }
}
