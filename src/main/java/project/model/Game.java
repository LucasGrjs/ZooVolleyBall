package project.model;

import java.util.Arrays;

public class Game
{
  long id;
  
  String[] playerSessionIds = new String[2];
  long[] playerIds = new long[2];

  long xJ1=250;
  long yJ1=870;
  long xJ2=550;
  long yJ2=870;
  long xBall=250;
  long yBall=300;

  long velocityXJ1=0;
  long velocityYJ1=0;
  long velocityXJ2=0;
  long velocityYJ2=0;
  long velocityXBall=0;
  long velocityYBall=-20;

  int roundWonJ1=0;
  int roundWonJ2=0;

  String[] playersSkin = new String[2];

  public Game(long id, String playerSessionId1, Long playerId1, String playerSessionId2, Long playerId2)
  {
    this.id = id;
    this.playerSessionIds[0] = playerSessionId1;
    this.playerSessionIds[1] = playerSessionId2;
    this.playerIds[0] = playerId1;
    this.playerIds[1] = playerId2;
    this.playersSkin[0] = "../images/Lion.png";
    this.playersSkin[1] = "../images/Lion.png";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String[] getPlayerSessionIds() {
    return playerSessionIds;
  }

  public void setPlayerSessionIds(String[] playerSessionIds) {
    this.playerSessionIds = playerSessionIds;
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


  public String getSkinJ1() {
    return playersSkin[0];
  }

  public String getSkinJ2() {
    return playersSkin[1];
  }

  public void setSkinJ1(String skinJ1) {
    this.playersSkin[0] = skinJ1;
  }

  public void setSkinJ2(String skinJ2) {
    this.playersSkin[1] = skinJ2;
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

  public int getRoundWonJ1() {
    return roundWonJ1;
  }

  public void setRoundWonJ1(int roundWonJ1) {
    this.roundWonJ1 = roundWonJ1;
  }

  public int getRoundWonJ2() {
    return roundWonJ2;
  }

  public void setRoundWonJ2(int roundWonJ2) {
    this.roundWonJ2 = roundWonJ2;
  }

  public long[] getPlayerIds() {
    return playerIds;
  }

  public void setPlayerIds(long[] playerIds) {
    this.playerIds = playerIds;
  }

  public boolean isJ1(String idJoueur){
    return this.playerSessionIds[0]==idJoueur;
  }

  public void setInitPos(){
    xJ1=250;
    yJ1=870;
    xJ2=550;
    yJ2=870;
    velocityXBall=0;
    velocityYBall=-20;
    if((roundWonJ1+roundWonJ2)%2 == 0){
      xBall=getxJ1();
      yBall=300;
    }else{
      xBall=getxJ2();
      yBall=300;
    }
  }

  @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", playersId=" + Arrays.toString(playerSessionIds) +
                ", playersSkin=" + Arrays.toString(playersSkin) +
                '}';
    }
}
