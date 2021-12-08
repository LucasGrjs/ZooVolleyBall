package project.messages;

public class ActionMessage {
    String action;
    long gameId;

    public ActionMessage()
    {
    }

    public ActionMessage(long gameId, String action)
    {
        this.gameId=gameId;
        this.action = action;
    }

    public String getAction()
    {
        return action;
    }
    public long getGameId(){return gameId;}
}
