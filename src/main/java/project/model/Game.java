package project.model;

import java.util.Arrays;

public class Game
{
  long id;
  
  String[] playersId = new String[2];
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

  public Game(long id, String playerId1, String playerId2)
  {
    this.id = id;
    this.playersId[0] = playerId1;
    this.playersId[1] = playerId2;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String[] getPlayersId() {
    return playersId;
  }

  public void setPlayersId(String[] playersId) {
    this.playersId = playersId;
  }

  public long getxJ1() {
    return xJ1;
  }

  public void setxJ1(long xJ1) {
    this.xJ1 = xJ1;
  }

  public long getyJ1() {
    return yJ1;
  }

  public void setyJ1(long yJ1) {
    this.yJ1 = yJ1;
  }

  public long getxJ2() {
    return xJ2;
  }

  public void setxJ2(long xJ2) {
    this.xJ2 = xJ2;
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

  @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", playersId=" + Arrays.toString(playersId) +
                '}';
    }
}
