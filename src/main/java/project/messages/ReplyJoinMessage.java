package project.messages;

public class ReplyJoinMessage
{
  boolean error;
  String errorMessage;
  
  long gameId;

  String skinJ1;
  String skinJ2;
  
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


  public String getSkinJ1() {
    return skinJ1;
  }
  public String getSkinJ2() {
    return skinJ2;
  }

  public void setSkinJ1(String skinJ1) {
    this.skinJ1 = skinJ1;
  }
  public void setSkinJ2(String skinJ2) {
    this.skinJ2 = skinJ2;
  }

  @Override
  public String toString() {
    return "ReplyJoinMessage{" +
            "error=" + error +
            ", errorMessage='" + errorMessage + '\'' +
            ", gameId=" + gameId +
            ", skinJ1=" + skinJ1 +
            ", skinJ2=" + skinJ2 +
            '}';
  }
}
