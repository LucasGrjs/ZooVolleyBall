package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import project.messages.ActionMessage;
import project.messages.JoinMessage;
import project.messages.ReplyActionMessage;
import project.messages.ReplyJoinMessage;
import project.model.Game;
import project.services.IGamesManagement;

@Controller
public class GameController
{
  @Autowired
  private IGamesManagement gamesManagement;
  
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  static long[] limitLeft={40,540};
  static long[] limitRight={460,960};

  static long plafondHeight = 0;
  static long solHeight=870;
  static long limitJumpHeight=600;
  static long jumpVelocity=20;
  static double gravity= 1.2;
  
  @RequestMapping("game")
  public String game(Model model)
  {
    return "game";
  }
  
  @MessageMapping("game/join")
  public void join(SimpMessageHeaderAccessor headerAccessor, JoinMessage message)
  {
    System.out.println("join playerId : " + headerAccessor.getSessionId());
    
    long gameId = message.getGameId();
    
    ReplyJoinMessage reply = new ReplyJoinMessage();

    //gamesManagement.createNewGame("a","b"); // en attendant
    Game game = gamesManagement.getGameById(gameId);
    
    if (game != null)
    {
      reply.setError(false);
      reply.setGameId(gameId);
    }
    else
    {
      reply.setError(true);
      reply.setErrorMessage("Can't find game with id " + gameId);
    }
    
    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/replyjoin", reply);
  }

  @MessageMapping("game/move")
  public void depl(SimpMessageHeaderAccessor headerAccessor, ActionMessage message) {
    System.out.println("deplacement playerId : " + headerAccessor.getSessionId());
    String action = message.getAction();
    System.out.println("action depl : "+action);
    ReplyActionMessage reply = new ReplyActionMessage();
    long gameId= message.getGameId();
    Game game = gamesManagement.getGameById(gameId);
    if(game==null){
      reply.setError(true);
      reply.setErrorMessage("Can't find game with id " + gameId);
      simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/move", reply);
      return;
    }
    reply.setError(false);

    int j = 0;
    if(!game.isJ1(headerAccessor.getSessionId())){
      j=1;
    }
    switch(action){
      case "gauche":
        if(!gauche(j,game)) return;
        break;
      case "droite":
        if(!droite(j,game)) return;
        break;
      default:
        return;
    }
    reply.setAllAttributesFromGame(game);
    reply.setIdJoueurInAction(headerAccessor.getSessionId());

    simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/move", reply);
    simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/move", reply);
  }

  @MessageMapping("game/jump")
  public void jump(SimpMessageHeaderAccessor headerAccessor, ActionMessage message) {
    System.out.println("deplacement playerId : " + headerAccessor.getSessionId());
    String action = message.getAction();
    System.out.println("action jump : "+action);
    ReplyActionMessage reply = new ReplyActionMessage();
    long gameId= message.getGameId();
    Game game = gamesManagement.getGameById(gameId);
    if(game==null){
      reply.setError(true);
      reply.setErrorMessage("Can't find game with id " + gameId);
      simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/jump", reply);
      return;
    }
    reply.setError(false);
    int j = 0;
    if(!game.isJ1(headerAccessor.getSessionId())){
      j=1;
    }
    switch(action){
      case "saut":
        if(!saut(j,game)) return;
        break;
      case "enSaut":
        enSaut(j,game);
        break;
      default:
        System.out.println("SALE TRICHEUR");
        return;
    }
    reply.setAllAttributesFromGame(game);
    reply.setIdJoueurInAction(headerAccessor.getSessionId());
    simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/jump", reply);
    simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/jump", reply);
    //simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/jump", reply);
  }

  @MessageMapping("game/moveBall")
  public void moveBall(SimpMessageHeaderAccessor headerAccessor, ActionMessage message) {
      long gameId= message.getGameId();
      Game game = gamesManagement.getGameById(gameId);

      ReplyActionMessage reply = new ReplyActionMessage();
      if(game==null){
          reply.setError(true);
          reply.setErrorMessage("Can't find game with id " + gameId);
          simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/moveBall", reply);
          return;
      }
      reply.setError(false);
      int roundWonJ1 = game.getRoundWonJ1();
      int roundWonJ2 = game.getRoundWonJ2();
      computeMoveBall(game);
      if(game.getRoundWonJ1()==3){ // J1 a gagné
          System.out.println("joueur 1 gagne la partie");
          ActionMessage msg = new ActionMessage(game.getId(),"1");
          msg.setScoreFinal("Score : "+game.getRoundWonJ1()+" - "+game.getRoundWonJ2());
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/win", msg);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/win", msg);
          return;
      }else if(game.getRoundWonJ2()==3){ // J2 a gagné
          System.out.println("joueur 2 gagne la partie");
          ActionMessage msg = new ActionMessage(game.getId(),"2");
          msg.setScoreFinal("Score : "+game.getRoundWonJ1()+" - "+game.getRoundWonJ2());
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/win", msg);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/win", msg);
          return;
      }

      if(game.getRoundWonJ1()>roundWonJ1){
          game.setInitPos();
          reply.setAllAttributesFromGame(game);
          reply.setIdJoueurInAction(game.getPlayerSessionIds()[0]);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/winRound", reply);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/loseRound", reply);
          return;
      }else if(game.getRoundWonJ2()>roundWonJ2){
          game.setInitPos();
          reply.setAllAttributesFromGame(game);
          reply.setIdJoueurInAction(game.getPlayerSessionIds()[0]);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/loseRound", reply);
          simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/winRound", reply);
          return;
      }

      //envoi du premier game/move/ball de la ball à j1
      reply.setAllAttributesFromGame(game);
      reply.setIdJoueurInAction(game.getPlayerSessionIds()[0]);
      simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/moveBall", reply);
      simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/moveBall", reply);
  }

  @MessageMapping("game/connected/{gameId}")
  public void connected(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable long gameId)
  {
    System.out.println("connected playerId : " + headerAccessor.getSessionId() + " gameId : " + gameId);
    ReplyActionMessage reply = new ReplyActionMessage();

    Game game = gamesManagement.getGameById(gameId);

    System.out.println("envoi game/init"+gameId);

    reply.setAllAttributesFromGame(game);
    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/init", reply);

    if(headerAccessor.getSessionId().equals(game.getPlayerSessionIds()[0])){
        computeMoveBall(game);
        reply.setAllAttributesFromGame(game);
        reply.setIdJoueurInAction(game.getPlayerSessionIds()[0]);
        simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[0], "/game/moveBall", reply);
        simpMessagingTemplate.convertAndSendToUser(game.getPlayerSessionIds()[1], "/game/moveBall", reply);
    }

  }

    private boolean gauche(int j,Game game){
        long xJ;
        if(j==0){
            xJ=game.getxJ1();
            if(xJ-10<limitLeft[j]) return false;
            game.setxJ1(xJ-10);
            game.setVelocityXJ1(-jumpVelocity/2);
        }else{
            xJ=game.getxJ2();
            if(xJ-10<limitLeft[j]) return false;
            game.setxJ2(xJ-10);
            game.setVelocityXJ2(-jumpVelocity/2);
        }
        return true;
    }
    private boolean droite(int j,Game game){
        long xJ;
        if(j==0){
            xJ=game.getxJ1();
            if(xJ+10>limitRight[j]) return false;
            game.setxJ1(xJ+10);
            game.setVelocityXJ1(jumpVelocity/2);
        }else{
            xJ=game.getxJ2();
            if(xJ+10>limitRight[j]) return false;
            game.setxJ2(xJ+10);
            game.setVelocityXJ2(jumpVelocity/2);
        }
        return true;
    }
    private boolean saut(int j,Game game){
        long yJ;
        long xJ;
        if(j==0){
            yJ=game.getyJ1();
            xJ=game.getxJ1();
            if(yJ!=solHeight){ // deja en train de sauter/retomber
                return false;
            }
            game.setVelocityYJ1(jumpVelocity);
            game.setyJ1(yJ-jumpVelocity); // on soustrait pour sauter car le repere est à l'envers
            if(game.getVelocityXJ1()!=0){
                long x = xJ+game.getVelocityXJ1();
                if(x<limitLeft[j]){
                    x=limitLeft[j];
                }else if(x>limitRight[j]){
                    x=limitRight[j];
                }
                game.setxJ1(x);
                game.setVelocityXJ1(0);
            }
        }else{
            yJ=game.getyJ2();
            xJ=game.getxJ2();
            if(yJ!=solHeight){ // deja en train de sauter/retomber
                return false;
            }
            game.setVelocityYJ2(jumpVelocity);
            game.setyJ2(yJ-jumpVelocity); // on soustrait pour sauter car le repere est à l'envers
            if(game.getVelocityXJ2()!=0){
                long x = xJ+game.getVelocityXJ2();
                if(x<limitLeft[j]){
                    x=limitLeft[j];
                }else if(x>limitRight[j]){
                    x=limitRight[j];
                }
                game.setxJ2(x);
                game.setVelocityXJ2(0);
            }
        }
        return true;
    }
    private void enSaut(int j,Game game){
        long yJ;
        long xJ;
        if(j==0){
            yJ=game.getyJ1();
            xJ=game.getxJ1();
            //if(yJ>=solHeight) {return false;}
            if(yJ<=limitJumpHeight){
                game.setVelocityYJ1(-jumpVelocity);
            }else if(game.getVelocityYJ1()<0){
                game.setVelocityYJ1((long)Math.ceil(game.getVelocityYJ1()*gravity));
            }
            game.setyJ1(yJ-game.getVelocityYJ1());
            if(game.getyJ1()>=solHeight){ // pas <= car repere à l'envers
                game.setyJ1(solHeight);
                game.setVelocityYJ1(0);
            }
            if(game.getVelocityXJ1()!=0){
                long x = xJ+game.getVelocityXJ1();
                if(x<limitLeft[j]){
                    x=limitLeft[j];
                }else if(x>limitRight[j]){
                    x=limitRight[j];
                }
                game.setxJ1(x);
                game.setVelocityXJ1(0);
            }
        }else{
            yJ=game.getyJ2();
            xJ=game.getxJ2();
            if(yJ<=limitJumpHeight){
                game.setVelocityYJ2(-jumpVelocity);
            }else if(game.getVelocityYJ2()<0){
                game.setVelocityYJ2((long)Math.ceil(game.getVelocityYJ2()*gravity));
            }
            game.setyJ2(yJ-game.getVelocityYJ2());
            if(game.getyJ2()>=solHeight){ // pas <= car repere à l'envers
                game.setyJ2(solHeight);
                game.setVelocityYJ2(0);
            }
            if(game.getVelocityXJ2()!=0){
                long x = xJ+game.getVelocityXJ2();
                if(x<limitLeft[j]){
                    x=limitLeft[j];
                }else if(x>limitRight[j]){
                    x=limitRight[j];
                }
                game.setxJ2(x);
                game.setVelocityXJ2(0);
            }
        }
    }

    //todo verif colision
    private void computeMoveBall(Game game){
        int radiusBall = 48;

        computeCollision(game);

        game.setVelocityYBall(game.getVelocityYBall()+1); // gravity
        if(game.getVelocityYBall()>30){
            game.setVelocityYBall(30);
        } else if(game.getVelocityYBall()< -30){
            game.setVelocityYBall(-30);
        }
        if(game.getVelocityXBall()>20){
            game.setVelocityXBall(20);
        } else if(game.getVelocityXBall()<-20){
            game.setVelocityXBall(-20);
        }
        game.setxBall(game.getxBall()+game.getVelocityXBall());
        game.setyBall(game.getyBall()+game.getVelocityYBall());

        if (game.getxBall() < 25) {
            game.setxBall(radiusBall);
            game.setVelocityXBall(game.getVelocityXBall()*-1);
        } else if(game.getxBall() > 1000-25) {
            game.setxBall(1000-radiusBall);
            game.setVelocityXBall(game.getVelocityXBall()*-1);
        }
        if (game.getyBall() < plafondHeight+radiusBall) {
            game.setyBall(plafondHeight+radiusBall);
            game.setVelocityYBall(game.getVelocityYBall()*-1);
        } if(game.getyBall() >= solHeight-radiusBall) {
            game.setyBall(solHeight-radiusBall);
            game.setVelocityYBall(0);
            long[] playerIds = game.getPlayerIds();
            if(game.getxBall()<500){
                System.out.println("joueur 2 gagne le point");
                game.setRoundWonJ2(game.getRoundWonJ2()+1);
            }else{
                System.out.println("joueur 1 gagne le point");
                game.setRoundWonJ1(game.getRoundWonJ1()+1);
            }
        }
    }

    private void computeCollision(Game game){
        long yBall=game.getyBall();
        long xBall=game.getxBall();
        long yJ1=game.getyJ1();
        long xJ1=game.getxJ1();
        long yJ2=game.getyJ2();
        long xJ2=game.getxJ2();
        long velocityXJ1 = game.getVelocityXJ1();
        long velocityYJ1 = game.getVelocityYJ1();
        long velocityXJ2 = game.getVelocityXJ2();
        long velocityYJ2 = game.getVelocityYJ2();
        long velocityXBall = game.getVelocityXBall();
        long velocityYBall = game.getVelocityYBall();

        // on detecte la collision POUR J1
        int dx = (int)(2 * (xBall -xJ1));
        int dy = (int)(yBall - yJ1) ;
        int dist = (int)(Math.sqrt(dx * dx + dy * dy));

        long dVelocityX = velocityXBall - velocityXJ1;
        long dVelocityY = velocityYBall - velocityYJ1;

        int radiusBall = 48;
        int radiusJ = 70;

        if(dy < 2*radiusBall && dist < radiusBall + radiusJ && dist > 5) {
            game.setxBall(xJ1 + (((radiusJ + radiusBall) / 2) * dx / dist));
            game.setyBall(yJ1 + ((radiusJ + radiusBall) * dy / dist));
            long something = ((dx * dVelocityX + dy * dVelocityY)/dist);
            //System.out.println("something : "+ something);
            if(something<=0){
                game.setVelocityXBall(velocityXBall+velocityXJ1-2*dx*something/dist);
                game.setVelocityYBall((velocityYBall+velocityYJ1-2*dy*something/dist));
            }
        }

        // on detecte lacollision pour J2
        dx = (int)(2 * (xBall -xJ2));
        dy = (int)(yBall - yJ2) ;
        dist = (int)(Math.sqrt(dx * dx + dy * dy));

        dVelocityX = velocityXBall - velocityXJ2;
        dVelocityY = velocityYBall - velocityYJ2;

        if(dy < 2*radiusBall && dist < radiusBall + radiusJ && dist > 5) {
            game.setxBall(xJ2 + (((radiusJ + radiusBall) / 2) * dx / dist));
            game.setyBall(yJ2 + ((radiusJ + radiusBall) * dy / dist));
            long something = ((dx * dVelocityX + dy * dVelocityY)/dist);
            if(something<=0){
                game.setVelocityXBall(velocityXBall+velocityXJ2-2*dx*something/dist);
                game.setVelocityYBall((velocityYBall+velocityYJ2-2*dy*something/dist));
            }
        }
    }
}
