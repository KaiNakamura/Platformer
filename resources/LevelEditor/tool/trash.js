class Trash extends Tool
{
  constructor()
  {
    super();
  }

  click(tileMap)
  {
    for (var i = 0; i < tileMap.numRows; i++)
    {
      for (var j = 0; j < tileMap.numCols; j++)
      {
        tileMap.tiles[i][j].color = color(0, 0);
      }
    }
  }
}
