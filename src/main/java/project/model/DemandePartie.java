package project.model;

import javax.persistence.*;

@Entity
public class DemandePartie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_demande;

    @ManyToOne
    User demandeur;
    @ManyToOne
    User receveur;

    public DemandePartie() {

    }

    public DemandePartie(User demandeur, User receveur) {
        this.demandeur = demandeur;
        this.receveur = receveur;
    }

    public long getId_demande() {
        return id_demande;
    }

    public void setId_demande(long id_demande) {
        this.id_demande = id_demande;
    }

    public User getReceveur() {
        return receveur;
    }

    public User getDemandeur() {
        return demandeur;
    }
}
