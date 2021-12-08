package project.model;

import javax.persistence.*;

@Entity
public class DemandeAmi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id_demande;

    @ManyToOne
    User demandeur;
    @ManyToOne
    User receveur;

    public long getId_demande() {
        return id_demande;
    }

    public void setId_demande(long id_demande) {
        this.id_demande = id_demande;
    }

    public User getDemandeur() {
        return demandeur;
    }

    public User getReceveur() {
        return receveur;
    }
}
