package project.messages;

public class ReplyJoinMessage
{
  long gameId;
  
  public ReplyJoinMessage()
  {
  }

  public ReplyJoinMessage(long gameId)
  {
    this.gameId = gameId;
  }

  public long getGameId()
  {
    return gameId;
  }
}
