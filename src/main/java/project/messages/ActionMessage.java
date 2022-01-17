package project.messages;

public class ActionMessage {
    String action;
    long gameId;
    String scoreFinal;

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

    public String getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(String scoreFinal) {
        this.scoreFinal = scoreFinal;
    }
}
