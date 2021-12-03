package project.messages;

public class JoinMessage
{
  private String player;

  public JoinMessage()
  {
  }

  public JoinMessage(String player)
  {
    this.player = player;
  }

  public String getPlayer()
  {
    return player;
  }
}
