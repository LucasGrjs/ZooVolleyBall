package project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idUser;
    String pseudo;
    String pwd;
    @Column(name="email", unique=true)
    String email;

    int credit = 0;
    int mmr = 0;
    int nbrWin = 0;
    int nbrLoss = 0;
    int nbrTourn = 0;
    int nbrTournWin = 0;

    long idBallSkin;
    long idNetSkin;
    long idBackgroundSkin;
    long idSkin;

    @ManyToMany
    List<Objet> objets;

    @OneToMany
    List<Partie> parties;

    @ManyToMany
    List<User> amis;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Roles> roles;

    public User() {
        setIdSkin(13L);
        setIdBallSkin(5L);
        setIdBackgroundSkin(3L);
        setIdNetSkin(2L);
    }

    public User(String pseudo, String pwd, String email, List<Roles> roles) {
        this.pseudo = pseudo;
        this.pwd = pwd;
        this.email = email;
        this.roles= roles;

        setIdSkin(13L);
        setIdBallSkin(5L);
        setIdBackgroundSkin(3L);
        setIdNetSkin(2L);
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long id_user) {
        this.idUser = id_user;
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
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

    public List<Partie> getParties() {
        return parties;
    }

    public List<User> getAmis() {
        return amis;
    }

    public void addItem(Objet item) { this.objets.add(item); }

    public long getIdBallSkin() {
        return idBallSkin;
    }

    public void setIdBallSkin(Long idBallSkin) {
        this.idBallSkin = idBallSkin;
    }

    public long getIdNetSkin() {
        return idNetSkin;
    }

    public void setIdNetSkin(Long idNetSkin) {
        this.idNetSkin = idNetSkin;
    }

    public long getIdBackgroundSkin() {
        return idBackgroundSkin;
    }

    public void setIdBackgroundSkin(Long idBackgroundSkin) {
        this.idBackgroundSkin = idBackgroundSkin;
    }

    public long getIdSkin() {
        return idSkin;
    }

    public void setIdSkin(Long idSkin) {
        this.idSkin = idSkin;
    }

    public void setObjets(List<Objet> objets) {
        this.objets = objets;
    }

    public void setParties(List<Partie> parties) {
        this.parties = parties;
    }

    public void setAmis(List<User> amis) {
        this.amis = amis;
    }
}
