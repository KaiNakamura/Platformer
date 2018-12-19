class Tool
{
  constructor()
  {
    if (this.constructor === Tool) throw new TypeError('Abstract class "Tool" cannot be instantiated directly.');
    if (this.click === undefined) throw new TypeError('Classes extending the tool abstract class must have click.');
  }
}
