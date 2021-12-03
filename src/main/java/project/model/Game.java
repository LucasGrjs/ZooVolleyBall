package project.model;

public class Game
{
  long id;
  
  String[] playersId = new String[2];
  
  public Game(long id, String playerId1, String playerId2)
  {
    this.id = id;
    this.playersId[0] = playerId1;
    this.playersId[1] = playerId2;
  }
}
