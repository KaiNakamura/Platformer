class Point
{
  constructor(x, y)
  {
    this.x = x;
    this.y = y;
  }

  set(v1 = this.x, v2 = this.y)
  {
    if (v1 instanceof Point)
    {
      this.x = v1.x;
      this.y = v1.y;
    }
    else
    {
      this.x = v1;
      this.y = v2;
    }
  }

  static add(a, b)
  {
    return new Point(a.x + b.x, a.y + b.y);
  }

  add(other)
  {
    this.x += other.x;
    this.y += other.y;
  }

  static sub(a, b)
  {
    return new Point(a.x - b.x, a.y - b.y);
  }

  sub(other)
  {
    this.x -= other.x;
    this.y -= other.y;
  }

  static dist(a, b)
  {
    return a.dist(b);
  }

  dist(other)
  {
    if (other == null) return Math.sqrt(this.x * this.x + this.y * this.y);
    else return Math.sqrt(Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y), 2));
  }

  static copy()
  {
    return new Point(this.x, this.y);
  }

  show(size)
  {
    ellipse(this.x, this.y, size);
  }
}
