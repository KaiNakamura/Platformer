class Tile
{
  constructor(v1, v2)
  {
    if (v1 instanceof Point) this.origin = v1;
    else
    {
      this.origin = new Point(v1, v2);
    }

    this.size = TILE_SIZE * SCALE;
    this.color = color(0, 0);
  }

  getTopLeft()
  {
    return Point.copy(this.origin)
  }

  getTopRight()
  {
    return Point.copy(this.origin).add(this.size, 0);
  }

  getBottomLeft()
  {
    return Point.copy(this.origin).add(0, this.size);
  }

  getBottomRight()
  {
    return Point.copy(this.origin).add(this.size, this.size);
  }

  inBounds(v1, v2)
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

    let left = this.origin.x;
    let right = left + this.size;
    let top = this.origin.y;
    let bottom = top + this.size;

    return (x >= left && x <= right && y >= top && y <= bottom);
  }

  static colorEquals(a, b)
  {
    return red(a) == red(b) && green(a) == green(b) && blue(a) == blue(b);
  }

  colorEquals(other)
  {
    Tile.colorEquals(this.color, other.color);
  }

  show()
  {
    fill(this.color);
    rect(this.origin.x, this.origin.y, this.size, this.size);
  }
}
