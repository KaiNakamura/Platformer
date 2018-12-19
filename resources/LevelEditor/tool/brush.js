class Brush extends Tool
{
  constructor()
  {
    super();
  }

  click(tileMap, color)
  {
    tileMap.setTile(mouseX, mouseY, color);
  }
}
