package project.messages;

public class ReplyActionMessage {
    boolean error;
    String errorMessage;


    long xJ1=250;
    long yJ1=800;
    long xJ2=550;
    long yJ2=800;
    long xBall=430;
    long yBall=100;

    public ReplyActionMessage()
    {
    }

    public ReplyActionMessage(boolean error, String errorMessage,
                              long xJ1,long yJ1,long xJ2, long yJ2, long xBall, long yBall)
    {
        this.error = error;
        this.errorMessage = errorMessage;
        this.xJ1=xJ1;
        this.yJ1=yJ1;
        this.xJ2=xJ2;
        this.yJ2=yJ2;
        this.xBall=xBall;
        this.yBall=yBall;
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

    public long getxJ1() {
        return xJ1;
    }

    public void setxJ1(long xJ1) {
        this.xJ1 = xJ1;
    }

    public long getxJ2() {
        return xJ2;
    }

    public void setxJ2(long xJ2) {
        this.xJ2 = xJ2;
    }

    public long getyJ1() {
        return yJ1;
    }

    public void setyJ1(long yJ1) {
        this.yJ1 = yJ1;
    }

    public long getyJ2() {
        return yJ2;
    }

    public void setyJ2(long yJ2) {
        this.yJ2 = yJ2;
    }

    public long getxBall() {
        return xBall;
    }

    public void setxBall(long xBall) {
        this.xBall = xBall;
    }

    public long getyBall() {
        return yBall;
    }

    public void setyBall(long yBall) {
        this.yBall = yBall;
    }
}
