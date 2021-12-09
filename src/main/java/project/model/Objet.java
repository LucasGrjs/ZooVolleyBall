package project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Objet {

    public enum TypeItem {
        BALL, SKIN, BACKGROUND, NET
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_objet;

    String nom_objet;
    int price;
    TypeItem type_item;

    public Objet() {

    }

    public Objet(String nom_objet, int price, TypeItem type_item) {
        this.nom_objet = nom_objet;
        this.price = price;
        this.type_item = type_item;
    }

    public Objet(String nomObjet) {
        this.nom_objet = nomObjet;
    }

    public long getId_objet() {
        return id_objet;
    }

    public void setId_objet(long id_objet) {
        this.id_objet = id_objet;
    }

    public String getNomObjet() {
        return nom_objet;
    }

    public int getPrice() {
        return price;
    }

    public void setNomObjet(String nomObjet) {
        this.nom_objet = nomObjet;
    }
}
