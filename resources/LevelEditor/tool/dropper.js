class Dropper extends Tool
{
  constructor()
  {
    super();
  }

  click(tileMap, color)
  {
    color = tileMap.getTile(mouseX, mouseY).color;
    return color;
  }
}
