package project.model;

import javax.persistence.*;

@Entity
public class Amis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_lien;

    @ManyToOne
    User user_1;

    @ManyToOne
    User user_2;

    public Amis() {

    }

    public Amis(User user_1, User user_2) {
        this.user_1 = user_1;
        this.user_2 = user_2;
    }

    public long getId_lien() {
        return id_lien;
    }

    public void setId_lien(long id_lien) {
        this.id_lien = id_lien;
    }

    public User getUser_1() {
        return user_1;
    }

    public User getUser_2() {
        return user_2;
    }
}
