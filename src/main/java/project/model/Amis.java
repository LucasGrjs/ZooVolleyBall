package project.model;

import javax.persistence.*;

@Entity
public class Amis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_lien;

    @ManyToOne
    User id_user_1;

    @ManyToOne
    User id_user_2;

    public Amis() {

    }

    public Amis(User id_user_1, User id_user_2) {
        this.id_user_1 = id_user_1;
        this.id_user_2 = id_user_2;
    }

    public long getId_lien() {
        return id_lien;
    }

    public void setId_lien(long id_lien) {
        this.id_lien = id_lien;
    }

    public User getId_user_1() {
        return id_user_1;
    }

    public User getId_user_2() {
        return id_user_2;
    }
}
