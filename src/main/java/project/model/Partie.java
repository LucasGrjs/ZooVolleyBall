package project.model;

import javax.persistence.*;

@Entity
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_partie;

    @ManyToOne
    User user_1;

    @ManyToOne
    User user_2;

    int score_us_1 = 0;
    int score_us_2 = 0;

    public Partie() {

    }

    public Partie(User user_1, User user_2) {
        this.user_1 = user_1;
        this.user_2 = user_2;
    }

    public long getId_partie() {
        return id_partie;
    }

    public void setId_partie(long id_partie) {
        this.id_partie = id_partie;
    }

    public User getUser_1() {
        return user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public int getScore_us_1() {
        return score_us_1;
    }

    public void setScore_us_1(int score_us_1) {
        this.score_us_1 = score_us_1;
    }

    public int getScore_us_2() {
        return score_us_2;
    }

    public void setScore_us_2(int score_us_2) {
        this.score_us_2 = score_us_2;
    }

    public String getResult(User user) {
        if (user_1.getPseudo().equals(user.getPseudo()) && score_us_1 > score_us_2) {
            return "Win";
        }
        if (user_2.getPseudo().equals(user.getPseudo()) && score_us_2 > score_us_1) {
            return "Win";
        }
        return "Lose";
    }

    public String getAdvName(User user) {
        if (user_1.getPseudo().equals(user.getPseudo())) {
            return user_2.getPseudo();
        }
        return user_1.getPseudo();
    }

    public String getScore(User user) {
        if (user_1.getPseudo().equals(user.getPseudo())) {
            return score_us_1 + " - " + score_us_2;
        }
        return score_us_2 + "-" + score_us_1;
    }
}
