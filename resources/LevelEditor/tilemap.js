class TileMap
{
  constructor(numRows = TILES_ACROSS, numCols = TILES_DOWN)
  {
    this.numRows = numRows;
    this.numCols = numCols;

    this.width = numRows * TILE_SIZE;
    this.height = numCols * TILE_SIZE;

    // Initialize tiles
    this.tiles = [];
    for (var i = 0; i < numRows; i++)
    {
      this.tiles[i] = [];
      for (var j = 0; j < numCols; j++)
      {
        let x = i * TILE_SIZE * SCALE;
        let y = j * TILE_SIZE * SCALE;
        this.tiles[i][j] = new Tile(x, y);
      }
    }
  }

  getTile(v1, v2)
  {
    var x, y;
    if (v1 instanceof Point)
    {
      x = v1.x;
      y = v1.y;
    }
    else
    {
      x = v1;
      y = v2;
    }

    for (var i = 0; i < this.numRows; i++)
    {
      for (var j = 0; j < this.numCols; j++)
      {
        if (this.tiles[i][j].inBounds(x, y)) return this.tiles[i][j];
      }
    }

    return null;
  }

  setTile(v1, v2, v3)
  {
    var x, y, color;
    if (v1 instanceof Point)
    {
      x = v1.x;
      y = v1.y;
      color = v2;
    }
    else
    {
      x = v1;
      y = v2;
      color = v3;
    }

    for (var i = 0; i < this.numRows; i++)
    {
      for (var j = 0; j < this.numCols; j++)
      {
        if (this.tiles[i][j].inBounds(x, y)) this.tiles[i][j].color = color;
      }
    }
  }
  
  erase()
  {
    for (var i = 0; i < this.numRows; i++)
    {
      for (var j = 0; j < this.numCols; j++)
      {
        this.tiles[i][j].color = color(0, 0);
      }
    }
  }

  show()
  {
    for (var i = 0; i < this.numRows; i++)
    {
      for (var j = 0; j < this.numCols; j++)
      {
        this.tiles[i][j].show();
      }
    }
  }
}
