document.addEventListener('contextmenu', event => event.preventDefault());

var canvas;
var panel;
var footer;
var tileMap;
var minX, minY;

var currentTool;
var brush, bucket, dropper, trash;

var color1, color2;

function setup()
{
  minX = TILES_ACROSS * TILE_SIZE * SCALE;
  minY = TILES_DOWN * TILE_SIZE * SCALE;
  canvas = createCanvas(minX, minY);
  panel = document.getElementById('panel');
  footer = document.getElementById('footer');
  tileMap = new TileMap();

  brush = new Brush();
  bucket = new Bucket();
  dropper = new Dropper();
  trash = new Trash();

  currentTool = bucket;

  color1 = color(255, 0, 0);
  color2 = color(0, 0);

  strokeWeight(4);
}

function draw()
{
  // Draw everything
  background(128);

  if (mouseIsPressed && mouseX < getPanelX() && mouseY < getFooterY())
  {
    if (mouseButton === LEFT) brush.click(tileMap, color1);
    if (mouseButton === RIGHT) bucket.click(tileMap, color1);
  }

  tileMap.show();
}

function erase()
{
  tileMap.erase();
}

function getPanelX()
{
  return panel.getBoundingClientRect().left - document.body.getBoundingClientRect().left;
}

function getFooterY()
{
  return footer.getBoundingClientRect().top - document.body.getBoundingClientRect().top;
}
