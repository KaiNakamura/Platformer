document.addEventListener('contextmenu', event => event.preventDefault());

var canvas;
var panel;
var footer;
var toolRadios;
var tileMaps;

var currentTool;
var brush, bucket, dropper;

var color1, color2;

function setup()
{
  canvas = createCanvas(TILES_ACROSS * TILE_SIZE * SCALE, TILES_DOWN * TILE_SIZE * SCALE);
  panel = document.getElementById('panel');
  footer = document.getElementById('footer');
  toolRadios = document.getElementsByName('tools');

  tileMaps = [];
  tileMaps.push(new TileMap());

  brush = new Brush();
  bucket = new Bucket();
  dropper = new Dropper();

  color1 = color(255, 0, 0);
  color2 = color(0, 0);

  strokeWeight(4);
}

function draw()
{
  updateCurrentTool();

  // Draw everything
  background(128);

  if (mouseIsPressed)
  {
    if (mouseOnCanvas() && mouseX < getPanelX() && mouseY < getFooterY())
    {
      if (mouseButton === LEFT)
      {
        var c = currentTool.click(tileMaps[0], color1);
        if (c != undefined) color1 = c;
      }
      if (mouseButton === RIGHT)
      {
        var c = currentTool.click(tileMaps[0], color2);
        if (c != undefined) color2 = c;
      }
    }
  }

  tileMaps[0].show();
}

function updateCurrentTool()
{
  for (var i = 0; i < toolRadios.length; i++)
  {
    if (toolRadios[i].checked)
    {
      switch (toolRadios[i].value) {
        case "brush":
          currentTool = brush;
          break;
        case "bucket":
          currentTool = bucket;
          break;
        case "dropper":
          currentTool = dropper;
          break;
      }
      break;
    }
  }
}

function erase()
{
  saveTileMap();
  tileMaps[0].erase();
}

function mousePressed()
{
  if (mouseX < getPanelX() && mouseY < getFooterY())
  {
    saveTileMap();
  }
}

function saveTileMap()
{
  console.log("Saving tilemap");
}

function mouseOnCanvas()
{
  return mouseX <= width && mouseX >= 0 && mouseY <= height && mouseY >= 0;
}

function getPanelX()
{
  return panel.getBoundingClientRect().left - document.body.getBoundingClientRect().left;
}

function getFooterY()
{
  return footer.getBoundingClientRect().top - document.body.getBoundingClientRect().top;
}
