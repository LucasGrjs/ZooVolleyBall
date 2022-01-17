package project.messages;

public class JoinMessage
{
  long gameId;
  long idUser;
  
  public JoinMessage()
  {
  }

  public JoinMessage(long gameId,long idUser)
  {
    this.gameId = gameId;this.idUser = idUser;
  }
  
  public long getGameId()
  {
    return gameId;
  }
  public long getIdUser()
  {
    return idUser;
  }
}
