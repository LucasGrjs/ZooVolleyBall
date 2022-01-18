package project.messages;

public class ReplyJoinMessage
{
  boolean error;
  String errorMessage;
  
  long gameId;

  String skinJ1;
  String skinJ2;

  String skinBackGroundJ1;
  String skinBackGroundJ2;

  String skinBallJ1;
  String skinBallJ2;

  String skinNetJ1;
  String skinNetJ2;
  
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

  public void setSkinBallJ1(String skin)
  {
    this.skinBallJ1 = skin;
  }

  public void setSkinBallJ2(String skin)
  {
    this.skinBallJ2 = skin;
  }

  public void setSkinBackGroundJ1(String skin)
  {
    this.skinBackGroundJ2 = skin;
  }

  public void setSkinBackGroundJ2(String skin)
  {
    this.skinBackGroundJ1 = skin;
  }
  public void setSkinNetJ1(String skin)
  {
    this.skinNetJ2 = skin;
  }

  public void setSkinNetJ2(String skin)
  {
    this.skinNetJ2 = skin;
  }

  @Override
  public String toString() {
    return "ReplyJoinMessage{" +
            "error=" + error +
            ", errorMessage='" + errorMessage + '\'' +
            ", gameId=" + gameId +
            ", skinJ1=" + skinJ1 +
            ", skinJ2=" + skinJ2 +
            ", skinBackGroundJ1=" + skinBackGroundJ1 +
            ", skinBackGroundJ2=" + skinBackGroundJ2 +
            ", skinNetJ1=" + skinNetJ1 +
            ", skinNetJ2=" + skinNetJ2 +
            ", skinBallJ1=" + skinBallJ1 +
            ", skinBallJ2=" + skinBallJ2 +
            '}';
  }
}
