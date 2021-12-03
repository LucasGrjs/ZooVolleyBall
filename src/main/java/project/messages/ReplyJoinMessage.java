package project.messages;

public class ReplyJoinMessage
{
  boolean error;
  String errorMessage;
  
  long gameId;
  
  public ReplyJoinMessage()
  {
  }

  public ReplyJoinMessage(boolean error, String errorMessage, long gameId)
  {
    this.error = error;
    this.errorMessage = errorMessage;
    this.gameId = gameId;
  }
  
  public void setError(boolean error)
  {
    this.error = error;
  }
  
  public boolean getError()
  {
    return error;
  }
  
  public void setErrorMessage(String errorMessage)
  {
    this.errorMessage = errorMessage;
  }
  
  public String getErrorMessage()
  {
    return errorMessage;
  }
  
  public void setGameId(long gameId)
  {
    this.gameId = gameId;
  }

  public long getGameId()
  {
    return gameId;
  }
}
