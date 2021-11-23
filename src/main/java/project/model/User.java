package project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_user;

    String pseudo;
    String pwd;
    String email;
    int credit = 0;
    int mmr = 0;
    int nbrWin = 0;
    int nbrLoss = 0;
    int nbrTourn = 0;
    int nbrTournWin = 0;

    @ManyToMany
    List<Objet> objets;

    @OneToMany
    List<Partie> partie;

    @OneToMany
    List<Amis> amis;

    public User() {

    }

    public User(String pseudo, String pwd, String email) {
        this.pseudo = pseudo;
        this.pwd = pwd;
        this.email = email;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getMmr() {
        return mmr;
    }

    public void setMmr(int mmr) {
        this.mmr = mmr;
    }

    public int getNbrWin() {
        return nbrWin;
    }

    public void setNbrWin(int nbrWin) {
        this.nbrWin = nbrWin;
    }

    public int getNbrLoss() {
        return nbrLoss;
    }

    public void setNbrLoss(int nbrLoss) {
        this.nbrLoss = nbrLoss;
    }

    public int getNbrTourn() {
        return nbrTourn;
    }

    public void setNbrTourn(int nbrTourn) {
        this.nbrTourn = nbrTourn;
    }

    public int getNbrTournWin() {
        return nbrTournWin;
    }

    public void setNbrTournWin(int nbrTournWin) {
        this.nbrTournWin = nbrTournWin;
    }

    public List<Objet> getObjets() {
        return objets;
    }

    public List<Partie> getPartie() {
        return partie;
    }

    public List<Amis> getAmis() {
        return amis;
    }
}
