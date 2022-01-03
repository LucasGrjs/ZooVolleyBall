package project.messages;

import project.model.Game;

public class ReplyActionMessage {
    boolean error;
    String errorMessage;


    long xJ1=250;
    long yJ1=800;
    long xJ2=550;
    long yJ2=800;
    long xBall=430;
    long yBall=100;

    long velocityXJ1=0;
    long velocityYJ1=0;
    long velocityXJ2=0;
    long velocityYJ2=0;
    long velocityXBall=0;
    long velocityYBall=0;

    String skinJ1;
    String skinJ2;

    public ReplyActionMessage()
    {
    }

    public ReplyActionMessage(boolean error, String errorMessage,
                              long xJ1, long yJ1, long xJ2, long yJ2,
                              long xBall, long yBall, long velocityXJ1,
                              long velocityYJ1, long velocityXJ2, long velocityYJ2,
                              long velocityXBall, long velocityYBall, String skinJ1, String skinJ2) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.xJ1 = xJ1;
        this.yJ1 = yJ1;
        this.xJ2 = xJ2;
        this.yJ2 = yJ2;
        this.xBall = xBall;
        this.yBall = yBall;
        this.velocityXJ1 = velocityXJ1;
        this.velocityYJ1 = velocityYJ1;
        this.velocityXJ2 = velocityXJ2;
        this.velocityYJ2 = velocityYJ2;
        this.velocityXBall = velocityXBall;
        this.velocityYBall = velocityYBall;
        this.skinJ1 = skinJ1;
        this.skinJ2 = skinJ2;
    }

    public ReplyActionMessage(boolean error, String errorMessage,Game game){
        this.error = error;
        this.errorMessage = errorMessage;
        this.xJ1 = game.getxJ1();
        this.yJ1 = game.getyJ1();
        this.xJ2 = game.getxJ2();
        this.yJ2 = game.getyJ2();
        this.xBall = game.getxBall();
        this.yBall = game.getyBall();
        this.velocityXJ1 = game.getVelocityXJ1();
        this.velocityYJ1 = game.getVelocityYJ1();
        this.velocityXJ2 = game.getVelocityXJ2();
        this.velocityYJ2 = game.getVelocityYJ2();
        this.velocityXBall = game.getVelocityXBall();
        this.velocityYBall = game.getVelocityYBall();
        this.skinJ1 = game.getSkinJ1();
        this.skinJ2 = game.getSkinJ2();
    }

    public ReplyActionMessage(boolean error, String errorMessage,
                              long xJ1, long yJ1, long xJ2, long yJ2, long xBall, long yBall, String skinJ1, String skinJ2)
    {
        this.error = error;
        this.errorMessage = errorMessage;
        this.xJ1=xJ1;
        this.yJ1=yJ1;
        this.xJ2=xJ2;
        this.yJ2=yJ2;
        this.xBall=xBall;
        this.yBall=yBall;
        this.skinJ1=skinJ1;
        this.skinJ2=skinJ2;
    }

    public void setAllAttributesFromGame(Game game){
        this.xJ1 = game.getxJ1();
        this.yJ1 = game.getyJ1();
        this.xJ2 = game.getxJ2();
        this.yJ2 = game.getyJ2();
        this.xBall = game.getxBall();
        this.yBall = game.getyBall();
        this.velocityXJ1 = game.getVelocityXJ1();
        this.velocityYJ1 = game.getVelocityYJ1();
        this.velocityXJ2 = game.getVelocityXJ2();
        this.velocityYJ2 = game.getVelocityYJ2();
        this.velocityXBall = game.getVelocityXBall();
        this.velocityYBall = game.getVelocityYBall();
        this.skinJ1 = game.getSkinJ1();
        this.skinJ2 = game.getSkinJ2();
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

    public boolean isError() {
        return error;
    }

    public long getVelocityXJ1() {
        return velocityXJ1;
    }

    public void setVelocityXJ1(long velocityXJ1) {
        this.velocityXJ1 = velocityXJ1;
    }

    public long getVelocityYJ1() {
        return velocityYJ1;
    }

    public void setVelocityYJ1(long velocityYJ1) {
        this.velocityYJ1 = velocityYJ1;
    }

    public long getVelocityXJ2() {
        return velocityXJ2;
    }

    public void setVelocityXJ2(long velocityXJ2) {
        this.velocityXJ2 = velocityXJ2;
    }

    public long getVelocityYJ2() {
        return velocityYJ2;
    }

    public void setVelocityYJ2(long velocityYJ2) {
        this.velocityYJ2 = velocityYJ2;
    }

    public long getVelocityXBall() {
        return velocityXBall;
    }

    public void setVelocityXBall(long velocityXBall) {
        this.velocityXBall = velocityXBall;
    }

    public long getVelocityYBall() {
        return velocityYBall;
    }

    public void setVelocityYBall(long velocityYBall) {
        this.velocityYBall = velocityYBall;
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
}
