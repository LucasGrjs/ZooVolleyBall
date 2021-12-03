package project.messages;

public class JoinMessage
{
  long gameId;
  
  public JoinMessage()
  {
  }

  public JoinMessage(long gameId)
  {
    this.gameId = gameId;
  }
  
  public long getGameId()
  {
    return gameId;
  }
}
