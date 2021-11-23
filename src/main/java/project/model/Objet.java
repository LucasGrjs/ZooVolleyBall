package project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Objet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_objet;

    String nomObjet;
    @ManyToMany
    List<User> users;

    public Objet() {

    }

    public Objet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public long getId_objet() {
        return id_objet;
    }

    public void setId_objet(long id_objet) {
        this.id_objet = id_objet;
    }

    public String getNomObjet() {
        return nomObjet;
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public List<User> getUsers() {
        return users;
    }
}
